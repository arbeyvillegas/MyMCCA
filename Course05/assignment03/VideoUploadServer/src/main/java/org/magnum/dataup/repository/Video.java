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
package org.magnum.dataup.repository;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Video {

	
	public Video(){
		
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	// private long duration;
	private String location;
	// private String subject;
	private String contentType;
	private byte rating;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// public long getDuration() {
	// return duration;
	// }
	//
	// public void setDuration(long duration) {
	// this.duration = duration;
	// }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	// public String getSubject() {
	// return subject;
	// }
	//
	// public void setSubject(String subject) {
	// this.subject = subject;
	// }

	

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTitle(), getId());
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Video)
				&& Objects.equals(getTitle(), ((Video) obj).getTitle())
				&& getId() == ((Video) obj).getId();
	}

	public byte getRating() {
		return rating;
	}

	public void setRating(byte rating) {
		this.rating = rating;
	}

}
