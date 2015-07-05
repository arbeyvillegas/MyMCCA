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
package org.magnum.dataup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStatus.VideoState;
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

/**
 * Handles client video requests
 */
@Controller
public class VideoSvc {

	/**
	 * Id generator
	 */
	private static final AtomicLong currentId = new AtomicLong(0L);

	/**
	 * Reference to the pair id->Video object
	 */
	private Map<Long, Video> videos = new HashMap<Long, Video>();

	/**
	 * Add video to the hash map
	 * 
	 * @param video
	 *            Video meta-data
	 * @return Video meta-data modified
	 */
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video video) {
		checkAndSetId(video);
		video.setDataUrl(getDataUrl(video.getId()));
		videos.put(video.getId(), video);
		return video;
	}

	/**
	 * Get all video list saved into hashmap
	 * 
	 * @return
	 */
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
	public @ResponseBody List<Video> getVideoList() {
		List<Video> listVideos = new ArrayList<Video>();
		for (Long key : videos.keySet()) {
			Video itemVideo = videos.get(key);
			listVideos.add(itemVideo);
		}
		return listVideos;
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
			if (videos.containsKey(id)) {
				fileMgm = VideoFileManager.get();
				Video video = creteVideoFromId(id);
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
			Video video = creteVideoFromId(id);
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

	/**
	 * Create Video instance from id
	 * 
	 * @param id
	 *            Video identifier
	 * @return Video instance object
	 */
	private Video creteVideoFromId(Long id) {
		String url = getDataUrl(id);
		Video video = new Video();
		video.setId(id);
		video.setDataUrl(url);
		return video;
	}

	/**
	 * Set video identifier
	 * 
	 * @param entity
	 *            Video object involved
	 */
	private void checkAndSetId(Video entity) {
		if (entity.getId() == 0) {
			entity.setId(currentId.incrementAndGet());
		}
	}

	/**
	 * Construct URL corresponding to video ID
	 * 
	 * @param videoId
	 *            Video identifier
	 * @return Video URL
	 */
	private String getDataUrl(long videoId) {
		String url = getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
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
