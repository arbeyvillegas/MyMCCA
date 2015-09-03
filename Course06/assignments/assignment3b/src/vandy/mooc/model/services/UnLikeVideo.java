package vandy.mooc.model.services;

import vandy.mooc.model.mediator.VideoDataMediator;
import android.os.AsyncTask;

public class UnLikeVideo extends AsyncTask<String, Void, Long > {

	protected Long doInBackground(String... params) {
		long ret = 0;
		
		VideoDataMediator mVideoMediator =
                new VideoDataMediator(params[0], params[1]);
		
		mVideoMediator.unlikeVideo(Long.parseLong(params[2]));
		
		return ret;
	}
	
	protected void onPostExecute(Float result) {         	
	}
	
	protected void onPreExecute() {
		
	}
	
	protected void onProgressUpdate(Void... values) {}

}
