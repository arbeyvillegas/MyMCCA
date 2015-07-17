package vandy.mooc.model.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.mime.TypedFile;
import vandy.mooc.model.mediator.webdata.Video;
import vandy.mooc.model.mediator.webdata.VideoStatus;
import vandy.mooc.model.mediator.webdata.VideoServiceProxy;
import vandy.mooc.model.mediator.webdata.VideoStatus.VideoState;
import vandy.mooc.utils.Constants;
import vandy.mooc.utils.VideoMediaStoreUtils;
import android.content.Context;
import android.net.Uri;
import vandy.mooc.common.Utils;

public class VideoController {

	private Context mContext;

	private AndroidVideoCache mAndroidVideoCache;

	private VideoServiceProxy mVideoServiceProxy;

	public VideoController(Context ctx) {
		mContext = ctx;

		mAndroidVideoCache = new AndroidVideoCache(mContext);

		mVideoServiceProxy = new RestAdapter.Builder()
				.setEndpoint(Constants.SERVER_URL).build()
				.create(VideoServiceProxy.class);

	}

	public boolean uploadVideo(Long videoId, Uri videoUri) {
		Video androidVideo = mAndroidVideoCache.getVideoById(videoId);

		if (androidVideo != null) {
			Video receivedVideo = mVideoServiceProxy.addVideo(androidVideo);

			if (receivedVideo != null) {

				String filePath = VideoMediaStoreUtils.getPath(mContext,
						videoUri);

				File videoFile = new File(filePath);
				Long size = videoFile.length() / Constants.MEGA_BYTE;

				if (size < Constants.MAX_SIZE_MEGA_BYTE) {
					VideoStatus status = mVideoServiceProxy.setVideoData(
							receivedVideo.getId(),
							new TypedFile(receivedVideo.getContentType(),
									videoFile));

					if (status.getState() == VideoState.READY) {
						return true;
					}

				} else
					Utils.showToast(mContext,
							"The video was too large to upload");
			}
		}

		return false;
	}

	public List<Video> getVideoList() {
		try {
			return (ArrayList<Video>) mVideoServiceProxy.getVideoList();
		} catch (Exception e) {
			return null;
		}
	}
}
