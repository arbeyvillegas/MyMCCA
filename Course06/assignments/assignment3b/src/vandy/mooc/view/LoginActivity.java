package vandy.mooc.view;

import vandy.mooc.R;
import vandy.mooc.common.GenericActivity;
import vandy.mooc.presenter.LoginOps;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
		this.getOps().authenticate(getUser(),getPassword());
	}
	
	/**
	 * Finishes this Activity.
	 */
	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		return getEditText(R.id.usernameET);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return getEditText(R.id.passwordET);
	}
	
	public String getEditText(int id){
		EditText login=(EditText)findViewById(id);
		return login.getText().toString();
	}
}
