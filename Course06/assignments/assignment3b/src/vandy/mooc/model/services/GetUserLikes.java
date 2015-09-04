package vandy.mooc.model.services;

import java.util.Collection;

import vandy.mooc.presenter.VideoOps;
import android.os.AsyncTask;

public class GetUserLikes extends AsyncTask<String, Void, Collection<String> > {

	protected Collection<String> doInBackground(String... params) {		
		return VideoOps.mVideoMediator.getUsersWhoLikedVideo(Long.parseLong(params[0]));
	}
	
	protected void onPostExecute(Float result) {         	
	}
	
	protected void onPreExecute() {
		
	}
	

}
