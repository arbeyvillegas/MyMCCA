package vandy.mooc.model.services;

import vandy.mooc.model.mediator.VideoDataMediator;
import android.os.AsyncTask;

public class UnLikeVideo extends AsyncTask<String, Void, Long > {

	protected Long doInBackground(String... params) {
		VideoDataMediator mVideoMediator = new VideoDataMediator();
		mVideoMediator.unlikeVideo(Long.parseLong(params[0]));
		
		return (long) 0;
	}
	
	protected void onPostExecute(Float result) {   
		
	}
	
	protected void onPreExecute() {
		
	}
	
	protected void onProgressUpdate(Void... values) {}

}
