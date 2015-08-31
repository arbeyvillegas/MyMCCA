package vandy.mooc.view;

import vandy.mooc.R;
import vandy.mooc.common.GenericActivity;
import vandy.mooc.presenter.LoginOps;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends GenericActivity<LoginOps.View, LoginOps>
		implements LoginOps.View {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.login_activity);

		// Invoke the special onCreate() method in GenericActivity,
		// passing in the VideoOps class to instantiate/manage and
		// "this" to provide VideoOps with the LoginOps.View instance.
		super.onCreate(savedInstanceState, LoginOps.class, this);
	}

	public void authenticateLogin(View view){
		
	}
	
	/**
	 * Finishes this Activity.
	 */
	@Override
	public void finish() {
		super.finish();
	}
}
