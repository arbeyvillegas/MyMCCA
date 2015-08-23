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

package org.magnum.mobilecloud.video;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.magnum.mobilecloud.video.client.VideoSvcApi;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
public class VideoSvc {

	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it to
	 * something other than "AnEmptyController"
	 * 
	 * 
	 * ________ ________ ________ ________ ___ ___ ___ ________ ___ __ |\
	 * ____\|\ __ \|\ __ \|\ ___ \ |\ \ |\ \|\ \|\ ____\|\ \|\ \ \ \ \___|\ \
	 * \|\ \ \ \|\ \ \ \_|\ \ \ \ \ \ \ \\\ \ \ \___|\ \ \/ /|_ \ \ \ __\ \ \\\
	 * \ \ \\\ \ \ \ \\ \ \ \ \ \ \ \\\ \ \ \ \ \ ___ \ \ \ \|\ \ \ \\\ \ \ \\\
	 * \ \ \_\\ \ \ \ \____\ \ \\\ \ \ \____\ \ \\ \ \ \ \_______\ \_______\
	 * \_______\ \_______\ \ \_______\ \_______\ \_______\ \__\\ \__\
	 * \|_______|\|_______|\|_______|\|_______|
	 * \|_______|\|_______|\|_______|\|__| \|__|
	 * 
	 * 
	 */

	@Autowired
	private VideoRepository repository;

	/**
	 * Add video to the hash map
	 * 
	 * @param video
	 *            Video meta-data
	 * @return Video meta-data modified
	 */
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video video,
			Principal principal) {
		video.setOwner(principal.getName());
		Video videoResult = repository.save(video);
		return videoResult;
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}/like", method = RequestMethod.POST)
	public @ResponseBody void addVideoLike(@PathVariable Long id,
			Principal principal, HttpServletResponse response) {
		Video video = repository.findOne(id);
		if (video != null) {
			String userName = principal.getName();
			if (!video.isAlredyLikeByUser(userName)) {
				video.addLikeUser(userName);
				repository.save(video);
			} else {
				response.setStatus(400);
			}
		} else {
			response.setStatus(404);
		}
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}/unlike", method = RequestMethod.POST)
	public @ResponseBody void removeVideoLike(@PathVariable Long id,
			Principal principal, HttpServletResponse response) {
		Video video = repository.findOne(id);
		if (video != null) {
			String userName = principal.getName();
			if (video.isAlredyLikeByUser(userName)) {
				video.removeLikeUser(userName);
				repository.save(video);
			} else {
				response.setStatus(400);
			}
		}else {
			response.setStatus(404);
		}
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}/likedby", method = RequestMethod.GET)
	public @ResponseBody List<String> getUsersWhoLikedVideo(
			@PathVariable long id) {
		Video video = repository.findOne(id);
		List<String> usersWhoLiked = null;
		if (video != null) {
			usersWhoLiked = video.getLikeUsers();
		}
		return usersWhoLiked;
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH + "/{id}", method = RequestMethod.GET)
	public @ResponseBody Video getVideo(@PathVariable long id) {
		return repository.findOne(id);
	}

	/**
	 * Get all video list saved into hashmap
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = VideoSvcApi.VIDEO_SVC_PATH, method = RequestMethod.GET)
	public @ResponseBody List<Video> getVideoList() {
		return Lists.newArrayList(repository.findAll());
	}

}
