package vandy.mooc.presenter;

import java.lang.ref.WeakReference;

import vandy.mooc.common.ConfigurableOps;
import vandy.mooc.common.ContextView;
import vandy.mooc.common.GenericAsyncTaskOps;
import vandy.mooc.model.mediator.VideoDataMediator;

public class LoginOps implements GenericAsyncTaskOps<Void, Void, String>,
ConfigurableOps<LoginOps.View>{

	VideoDataMediator mVideoMediator;
	private WeakReference<LoginOps.View> mLoginView;
	/**
     * This interface defines the minimum interface needed by the
     * VideoOps class in the "Presenter" layer to interact with the
     * VideoListActivity in the "View" layer.
     */
    public interface View extends ContextView {
        /**
         * Finishes the Activity the VideoOps is
         * associated with.
         */
        void finish();
        
        String getUser();

        String getPassword();
    }
    
    public void authenticate(String user,String password){
    	mVideoMediator=new VideoDataMediator(user,password);
    	mVideoMediator.getVideo(7);
    	
    }

	@Override
	public void onConfiguration(View view, boolean firstTimeIn) {
		// TODO Auto-generated method stub
		mLoginView=new WeakReference<LoginOps.View>(view);
		if (firstTimeIn){
			mVideoMediator=new VideoDataMediator();
		}
	}

	@Override
	public String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
	}
}
