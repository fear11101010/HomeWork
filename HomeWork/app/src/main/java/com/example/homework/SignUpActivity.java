package com.example.homework;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;

public class SignUpActivity extends ActionBarActivity implements View.OnClickListener,OnFocusChangeListener,BirthDatePicker.OnDateSet{

	private String value;
	EditText name,email,bdate,phone,password,cpassword;
	Spinner gender;
	List<NameValuePair> data;
    Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(tp);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signuplayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        CustomActionBar cab = new CustomActionBar(toolbar);
        setSupportActionBar(cab.getActionBar(getResources().getString(R.string.app_name),R.drawable.ic_launcher,Color.WHITE));
		name = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.email);
		bdate = (EditText) findViewById(R.id.bdate);
		phone = (EditText) findViewById(R.id.phone);
		password = (EditText) findViewById(R.id.password);
		cpassword = (EditText) findViewById(R.id.cpassword);
		gender = (Spinner) findViewById(R.id.gender);
		Button signup = (Button) findViewById(R.id.signup);
		Button cancel = (Button) findViewById(R.id.cancel);
		signup.setOnClickListener(this);
		cancel.setOnClickListener(this);
		bdate.setOnFocusChangeListener(this);
		bdate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.signup:
			completeSignUp();
			break;
		case R.id.bdate:
			showDatePicker();
			break;
		case R.id.cancel:
			finish();
		}
	}
	public void completeSignUp(){
		String n = name.getText().toString();
		String e = email.getText().toString();
		String bd = bdate.getText().toString();
		String ph = phone.getText().toString();
		String p = password.getText().toString();
		String cp = cpassword.getText().toString();
		data = new ArrayList<NameValuePair>();
		if(n.isEmpty()) {
			Toast.makeText(this, "Name field can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
		if(e.isEmpty()){
			Toast.makeText(this, "Email field can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
		if(bd.isEmpty()) {
			Toast.makeText(this, "Birth date field can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
		if(ph.isEmpty()) {
			Toast.makeText(this, "Phone field can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
		if(p.isEmpty()||cp.isEmpty()||!p.equals(cp)){
			Toast.makeText(this, "Password mismatch or empty can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
		if(gender.getSelectedItemPosition()==0) return;
		data.add(new BasicNameValuePair("tag", "1"));
		data.add(new BasicNameValuePair("name", n));
		data.add(new BasicNameValuePair("email", e));
		data.add(new BasicNameValuePair("b_date", bd));
		data.add(new BasicNameValuePair("phone", ph));
		data.add(new BasicNameValuePair("gender", gender.getSelectedItem().toString()));
		data.add(new BasicNameValuePair("password", p));
		ast.execute();
	}
	AsyncTask<Void, Void, String> ast = new AsyncTask<Void, Void, String>() {
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
		    pd = new ProgressDialog(SignUpActivity.this);
			pd.setMessage("Regstering.....");
			pd.setIndeterminate(true);
			pd.show();
		};
		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			SignUpActivity.this.finish();
			//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			WebService web = new WebService();
			String result = web.InsertData(data);
			value = result;
			return result;
		};
	};
	@Override
	public void finish() {
		Intent i = new Intent();
		i.putExtra("result", value);
		setResult(RESULT_OK, i);
		super.finish();
	}

    @Override
    public void setDate(String date) {
        bdate.setText("");
        bdate.append(date);
    }

    @Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(hasFocus){
			showDatePicker();
		}
	};
	public void showDatePicker(){
        BirthDatePicker bdp = new BirthDatePicker();
		if(bdate.getText().toString().isEmpty()==false){
            Bundle b = new Bundle();
            String[] date = bdate.getText().toString().split("/");
            b.putInt("day",Integer.valueOf(date[0]));
            b.putInt("month",Integer.valueOf(date[1]));
            b.putInt("year",Integer.valueOf(date[2]));
            bdp.setArguments(b);
        }
		bdp.show(getFragmentManager(), "datepicker");
	}
}
