package com.example.homework;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener{

	private final int REQUEST_CODE = 1;
	Button signUp,signIn;
	EditText email,password;
	Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        CustomActionBar cab = new CustomActionBar(toolbar);
        setSupportActionBar(cab.getActionBar(getResources().getString(R.string.app_name),R.drawable.ic_launcher,Color.WHITE));
        signUp = (Button) findViewById(R.id.signup);
        signIn = (Button) findViewById(R.id.signin);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.signup:
			Intent signupIntent = new Intent(this, SignUpActivity.class);
			startActivityForResult(signupIntent, REQUEST_CODE);
			break;
		case R.id.signin:
			DoLogIn dli = new DoLogIn();
			dli.execute();
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(REQUEST_CODE==requestCode&&RESULT_OK==resultCode){
			String result = data.getExtras().getString("result");
			if(result!=null)Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public int checkLogin(){
		String email = this.email.getText().toString();
		String password= this.password.getText().toString();
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("tag", "2"));
		data.add(new BasicNameValuePair("email", email));
		data.add(new BasicNameValuePair("password", password));
		WebService login = new WebService();
		int status = login.logIn(data);
		if(status==-1||status==-2) return status;
		else if(status==-3) return status;
		else {
			Intent signinIntent = new Intent(this, SignInActivity.class);
			signinIntent.putExtra("id", status);
			startActivity(signinIntent);
			return 1;
		}
		
	}
	private class DoLogIn extends AsyncTask<Void, Void, Integer>{

		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd = new ProgressDialog(MainActivity.this);
			pd.setMessage("Logging in....");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}
		@Override
		protected Integer doInBackground(Void... params) {
			int value = checkLogin();
			
			return value;
			// TODO Auto-generated method stub
			
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if(result==-1||result==-2) {
				Toast.makeText(MainActivity.this, "Can`t connect to server", Toast.LENGTH_SHORT).show();
			}
			else if(result==-3) {
				Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
}
