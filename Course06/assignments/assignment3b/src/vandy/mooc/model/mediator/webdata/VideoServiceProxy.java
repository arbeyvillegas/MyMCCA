package vandy.mooc.model.mediator.webdata;

import java.util.Collection;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * This interface defines an API for a Video Service web service.  The
 * interface is used to provide a contract for client/server
 * interactions.  The interface is annotated with Retrofit annotations
 * to send Requests and automatically convert the Video.
 */
public interface VideoServiceProxy {
public static final String TITLE_PARAMETER = "title";
	
	public static final String DURATION_PARAMETER = "duration";

	public static final String TOKEN_PATH = "/oauth/token";

	// The path where we expect the VideoSvc to live
	public static final String VIDEO_SVC_PATH = "/video";

	// The path to search videos by title
	public static final String VIDEO_TITLE_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findByName";
	
	// The path to search videos by title
	public static final String VIDEO_DURATION_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findByDurationLessThan";

	@GET(VIDEO_SVC_PATH)
	public Collection<Video> getVideoList();
	
	@GET(VIDEO_SVC_PATH + "/{id}")
	public Video getVideoById(@Path("id") long id);
	
	@POST(VIDEO_SVC_PATH)
	public Video addVideo(@Body Video v);
	
	@POST(VIDEO_SVC_PATH + "/{id}/like")
	public Void likeVideo(@Path("id") long id);
	
	@POST(VIDEO_SVC_PATH + "/{id}/unlike")
	public Void unlikeVideo(@Path("id") long id);
	
	@GET(VIDEO_SVC_PATH + "/{id}/likedby")
	public Collection<String> getUsersWhoLikedVideo(@Path("id") long id);
//    /**
//     * Used as Request Parameter for Video data.
//     */
//    public static final String DATA_PARAMETER = "data";
//
//    /**
//     * Used as Request Parameter for VideoId.
//     */
//    public static final String ID_PARAMETER = "id";
//
//    /**
//     * The path where we expect the VideoSvc to live.
//     */
//    public static final String VIDEO_SVC_PATH = "/video";
//	
//    /**
//     * The path where we expect the VideoSvc to live.
//     */
//    public static final String VIDEO_DATA_PATH = 
//        VIDEO_SVC_PATH 
//        + "/{"
//        + VideoServiceProxy.ID_PARAMETER
//        + "}/data";
//
//    /**
//     * Sends a GET request to get the List of Videos from Video
//     * Web service using a two-way Retrofit RPC call.
//     */
//    @GET(VIDEO_SVC_PATH)
//    public Collection<Video> getVideoList();
//    
//    /**
//     * Sends a POST request to add the Video metadata to the Video 
//     * Web service using a two-way Retrofit RPC call.
//     *
//     * @param video meta-data
//     * @return Updated video meta-data returned from the Video Service.
//     */
//    @POST(VIDEO_SVC_PATH)
//    public Video addVideo(@Body Video video);
//	
//    /**
//     * Sends a POST request to Upload the Video data to the Video Web
//     * service using a two-way Retrofit RPC call.  @Multipart is used
//     * to transfer multiple content (i.e. several files in case of a
//     * file upload to a server) within one request entity.  When doing
//     * so, a REST client can save the overhead of sending a sequence
//     * of single requests to the server, thereby reducing network
//     * latency.
//     * 
//     * @param id
//     * @param videoData
//     * @return videoStatus indicating status of the uploaded video.
//     */
//    @Multipart
//    @POST(VIDEO_DATA_PATH)
//    public VideoStatus setVideoData(@Path(ID_PARAMETER) long id,
//                                    @Part(DATA_PARAMETER) TypedFile videoData);
//	
//    /**
//     * This method uses Retrofit's @Streaming annotation to indicate
//     * that the method is going to access a large stream of data
//     * (e.g., the mpeg video data on the server).  The client can
//     * access this stream of data by obtaining an InputStream from the
//     * Response as shown below:
//     * 
//     * VideoServiceProxy client = ... // use retrofit to create the client
//     * Response response = client.getData(someVideoId); 
//     * InputStream videoDataStream = response.getBody().in();
//     * 
//     * @param id
//     * @return Response which contains the actual Video data.
//     */
//    @Streaming
//    @GET(VIDEO_DATA_PATH)
//    Response getData(@Path(ID_PARAMETER) long id);
}
