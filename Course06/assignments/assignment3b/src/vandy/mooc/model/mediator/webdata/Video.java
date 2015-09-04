package vandy.mooc.model.mediator.webdata;

import java.util.Objects;

/**
 * This "Plain Ol' Java Object" (POJO) class represents meta-data of
 * interest downloaded in Json from the Video Service via the
 * VideoServiceProxy.
 */
public class Video {
    /**
     * Various fields corresponding to data downloaded in Json from
     * the Video WebService.
     */
	private long id;

	private String name;
	private String url;
	private long duration;
	private long likes;
	private boolean likeByCurrentUser;
	
	public Video() {
	}

	public Video(String name, String url, long duration, long likes) {
		super();
		this.name = name;
		this.url = url;
		this.duration = duration;
		this.likes = likes;
	}
	
	public Video(String name,  long duration) {
		super();
		this.name = name;
		this.duration = duration;
	}

    

    /*
     * Getters and setters to access Video.
     */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}
	
	public boolean isLikeByCurrentUser() {
		return likeByCurrentUser;
	}

	public void setLikeByCurrentUser(boolean likeByCurrentUser) {
		this.likeByCurrentUser = likeByCurrentUser;
	}
	
    /**
     * @return the textual representation of Video object.
     */
    @Override
    public String toString() {
        return "{" +
            "Id: "+ id + ", "+
            "Name: "+ name+ ", "+
            "Duration: "+ duration + ", "+
            "Data URL: "+ url +
            "}";
    }

    /** 
     * @return an Integer hash code for this object. 
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                            getDuration());
    }

    /**
     * @return Compares this Video instance with specified 
     *         Video and indicates if they are equal.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Video)
            && Objects.equals(getName(),
                              ((Video) obj).getName())
            && getDuration() == ((Video) obj).getDuration();
    }
}