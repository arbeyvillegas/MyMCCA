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

/**
 *                       DO NOT MODIFY THIS CLASS
 *                       
                    ___                    ___           ___                            
     _____         /\  \                  /\  \         /\  \                           
    /::\  \       /::\  \                 \:\  \       /::\  \         ___              
   /:/\:\  \     /:/\:\  \                 \:\  \     /:/\:\  \       /\__\             
  /:/  \:\__\   /:/  \:\  \            _____\:\  \   /:/  \:\  \     /:/  /             
 /:/__/ \:|__| /:/__/ \:\__\          /::::::::\__\ /:/__/ \:\__\   /:/__/              
 \:\  \ /:/  / \:\  \ /:/  /          \:\~~\~~\/__/ \:\  \ /:/  /  /::\  \              
  \:\  /:/  /   \:\  /:/  /            \:\  \        \:\  /:/  /  /:/\:\  \             
   \:\/:/  /     \:\/:/  /              \:\  \        \:\/:/  /   \/__\:\  \            
    \::/  /       \::/  /                \:\__\        \::/  /         \:\__\           
     \/__/         \/__/                  \/__/         \/__/           \/__/           
      ___           ___                                     ___                         
     /\  \         /\  \         _____                     /\__\                        
    |::\  \       /::\  \       /::\  \       ___         /:/ _/_         ___           
    |:|:\  \     /:/\:\  \     /:/\:\  \     /\__\       /:/ /\__\       /|  |          
  __|:|\:\  \   /:/  \:\  \   /:/  \:\__\   /:/__/      /:/ /:/  /      |:|  |          
 /::::|_\:\__\ /:/__/ \:\__\ /:/__/ \:|__| /::\  \     /:/_/:/  /       |:|  |          
 \:\~~\  \/__/ \:\  \ /:/  / \:\  \ /:/  / \/\:\  \__  \:\/:/  /      __|:|__|          
  \:\  \        \:\  /:/  /   \:\  /:/  /   ~~\:\/\__\  \::/__/      /::::\  \          
   \:\  \        \:\/:/  /     \:\/:/  /       \::/  /   \:\  \      ~~~~\:\  \         
    \:\__\        \::/  /       \::/  /        /:/  /     \:\__\          \:\__\        
     \/__/         \/__/         \/__/         \/__/       \/__/           \/__/        
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.magnum.autograder.junit.Rubric;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.magnum.dataup.model.VideoStatus;
import org.magnum.dataup.model.VideoStatus.VideoState;
import org.magnum.dataup.repository.Video;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AutoGradingTest {

	private static final String SERVER = "http://localhost:8080";

	private File testVideoData = new File(
			"src/test/resources/test.mp4");
	
	private Video video = new Video();
	

	private VideoSvcApi videoSvc = new RestAdapter.Builder()
			.setEndpoint(SERVER).build()
			.create(VideoSvcApi.class);

	@Rubric(
			value="Video data is preserved",
			goal="The goal of this evaluation is to ensure that your Spring controller(s) "
					+ "properly unmarshall Video objects from the data that is sent to them "
					+ "and that the HTTP API for adding videos is implemented properly. The"
					+ " test checks that your code properly accepts a request body with"
					+ " application/json data and preserves all the properties that are set"
					+ " by the client. The test also checks that you generate an ID and data"
					+ " URL for the uploaded video.",
			points=20.0,
			reference="This test is derived from the material in these videos: "
					+ "https://class.coursera.org/mobilecloud-001/lecture/61 "
					+ "https://class.coursera.org/mobilecloud-001/lecture/67 "
					+ "https://class.coursera.org/mobilecloud-001/lecture/71"
			)
	@Test
	public void testAddVideoMetadata() throws Exception {
		video=new Video();
		video.setContentType("video/mp4");
		video.setTitle(UUID.randomUUID().toString());
		
		
		Video received = videoSvc.addVideo(video);
		assertEquals(video.getTitle(), received.getTitle());

		assertTrue(received.getId() > 0);
		assertTrue(received.getLocation() != null);
	}
	

	@Rubric(
			value="Mpeg video data can be submitted for a video",
			goal="The goal of this evaluation is to ensure that your Spring controller(s) "
					+ "allow mpeg video data to be submitted for a video. The test also"
					+ " checks that the controller(s) can serve that video data to the"
					+ " client.",
			points=20.0,
			reference="This test is derived from the material in these videos: "
					+ "https://class.coursera.org/mobilecloud-001/lecture/69 "
					+ "https://class.coursera.org/mobilecloud-001/lecture/65 "
					+ "https://class.coursera.org/mobilecloud-001/lecture/71 "
					+ "https://class.coursera.org/mobilecloud-001/lecture/207"
			)
	@Test
	public void testAddVideoData() throws Exception {
		
		Collection<Video> stored = videoSvc.getVideoList();
		Video received=null;
		for(Video v : stored){
			received=v;
			break;
		}
		
		VideoStatus status = videoSvc.setVideoData(received.getId(),
				new TypedFile(received.getContentType(), testVideoData));
		assertEquals(VideoState.READY, status.getState());
		
		Response response = videoSvc.getData(received.getId());
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void checkVideoData() throws Exception {
		
		Collection<Video> stored = videoSvc.getVideoList();
		Video received=null;
		for(Video v : stored){
			received=v;
			break;
		}
		
		Response response = videoSvc.getData(received.getId());
		assertEquals(200, response.getStatus());
		
		InputStream videoData = response.getBody().in();
		byte[] originalFile = IOUtils.toByteArray(new FileInputStream(testVideoData));
		byte[] retrievedFile = IOUtils.toByteArray(videoData);
		assertTrue(Arrays.equals(originalFile, retrievedFile));
	}
	
	@Test
	public void setRateVideo() throws Exception {
		Collection<Video> stored = videoSvc.getVideoList();
		Video received=null;
		for(Video v : stored){
			received=v;
			break;
		}
		received.setRating((byte)4);
		Video result=videoSvc.addVideo(received);
		assertEquals(result.getRating(), received.getRating());
	}
}
