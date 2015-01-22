package com.example.homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInActivity extends Activity implements View.OnClickListener{

	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signinlayout);
		id = getIntent().getExtras().getInt("id");
		Button shareDialog = (Button) findViewById(R.id.shareDialog);
		Button viewProfile = (Button) findViewById(R.id.viewProfile);
		Button logOut = (Button) findViewById(R.id.logOut);
		shareDialog.setOnClickListener(this);
		viewProfile.setOnClickListener(this);
		logOut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int buttonId = v.getId();
		switch(buttonId){
		case R.id.shareDialog:
			new ShareTextDialog().show(getFragmentManager(), "Share Dialog");
			break;
		case R.id.logOut:
			finish();
			break;
		case R.id.viewProfile:
			Intent intent = new Intent(this, UserProfileActivity.class);
			intent.putExtra("id", id);
			startActivity(intent);
		}
	}
}
