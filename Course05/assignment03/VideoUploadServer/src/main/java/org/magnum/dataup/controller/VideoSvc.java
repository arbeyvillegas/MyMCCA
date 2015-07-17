/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.magnum.dataup.VideoFileManager;
import org.magnum.dataup.VideoSvcApi;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStatus.VideoState;
import org.magnum.dataup.repository.Video;
import org.magnum.dataup.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

/**
 * Handles client video requests
 */
@Controller
public class VideoSvc {

	
	@Autowired
	private VideoRepository repository;
	
	/**
	 * Add video to the hash map
	 * 
	 * @param video
	 *            Video meta-data
	 * @return Video meta-data modified
	 */
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video video) {
		video.setLocation(getDataUrl());
		Video videoResult=repository.save(video);
		return videoResult;
	}

	/**
	 * Get all video list saved into hashmap
	 * 
	 * @return
	 */
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
	public @ResponseBody List<Video> getVideoList() {
		return Lists.newArrayList(repository.findAll());
	}

	/**
	 * Save stream video to disk
	 * 
	 * @param id
	 *            Video identifier
	 * @param data
	 *            Stream that contains the video data
	 * @return Object representing video status operation
	 */
	@RequestMapping(value = VideoSvcApi.VIDEO_DATA_PATH, method = RequestMethod.POST)
	public @ResponseBody VideoStatus setVideoData(
			@PathVariable(VideoSvcApi.ID_PARAMETER) Long id,
			@RequestParam(VideoSvcApi.DATA_PARAMETER) MultipartFile data,
			HttpServletResponse response) {
		VideoStatus status = new VideoStatus(VideoState.PROCESSING);
		VideoFileManager fileMgm;
		try {
			if (repository.exists(id)) {
				fileMgm = VideoFileManager.get();
				Video video = repository.findOne(id);
				fileMgm.saveVideoData(video, data.getInputStream());
				status.setState(VideoState.READY);
			} else {
				response.setStatus(404);
			}
		} catch (IOException e) {
			response.setStatus(404);
		}
		return status;
	}

	/**
	 * Get video stream data
	 * 
	 * @param id
	 *            Video identifier
	 */
	@RequestMapping(value = VideoSvcApi.VIDEO_DATA_PATH, method = RequestMethod.GET)
	public void getData(@PathVariable Long id, HttpServletResponse response) {
		try {
			VideoFileManager fileManager = VideoFileManager.get();
			Video video = repository.findOne(id);
			if (fileManager.hasVideoData(video)) {
				response.setContentType("video/mp4");
				fileManager.copyVideoData(video, response.getOutputStream());
				response.setStatus(200);
				response.flushBuffer();
			} else {
				response.setStatus(404);
			}
		} catch (IOException e) {
			response.setStatus(404);
		}
	}

	
	/***
	 * Get physic url of video
	 * @return Video's url
	 */	
	private String getDataUrl() {
		java.util.UUID uid=java.util.UUID.randomUUID();
		String url = getUrlBaseForLocalServer() + "/video/" + uid.toString() + "/data";
		return url;
	}

	/**
	 * Get base URL for local server
	 * 
	 * @return Base URL for local server
	 */
	private String getUrlBaseForLocalServer() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String base = "http://"
				+ request.getServerName()
				+ ((request.getServerPort() != 80) ? ":"
						+ request.getServerPort() : "");
		return base;
	}

}
