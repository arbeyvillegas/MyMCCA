package vandy.mooc.model.services;

import vandy.mooc.presenter.VideoOps;
import android.os.AsyncTask;

public class UnLikeVideo extends AsyncTask<String, Void, Long > {

	protected Long doInBackground(String... params) {
		VideoOps.mVideoMediator.unlikeVideo(Long.parseLong(params[2]));
		
		return (long) 0;
	}
	
	protected void onPostExecute(Float result) {   
		
	}
	
	protected void onPreExecute() {
		
	}
	
	protected void onProgressUpdate(Void... values) {}

}
