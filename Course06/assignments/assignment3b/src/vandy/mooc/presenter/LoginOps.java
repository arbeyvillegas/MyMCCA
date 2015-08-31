package vandy.mooc.presenter;

import vandy.mooc.common.ConfigurableOps;
import vandy.mooc.common.ContextView;
import vandy.mooc.common.GenericAsyncTaskOps;

public class LoginOps implements GenericAsyncTaskOps<Void, Void, String>,
ConfigurableOps<LoginOps.View>{

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

        
    }

	@Override
	public void onConfiguration(View view, boolean firstTimeIn) {
		// TODO Auto-generated method stub
		
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
