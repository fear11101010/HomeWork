package com.example.homework;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileActivity extends ActionBarActivity implements EditProfileFragment.OnDetailUpdate,BirthDatePicker.OnDateSet{

	FragmentManager fm;
	private int EDIT=0,ID;
	ArrayList<String> l;
    Toolbar toolbar;
	private CreatePasswordChangeDialog cpd;
    @Override
    public void setDate(String date) {
        EditProfileFragment.setDate(date);


    }

    public class CreatePasswordChangeDialog implements DialogInterface.OnClickListener{

		AlertDialog.Builder ab;
		AlertDialog at;
		Context context;
		EditText np,op,rnp;
		public CreatePasswordChangeDialog(String title,Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
			ab = new AlertDialog.Builder(context);
			ab.setTitle(title);
			ab.setCancelable(false);
		}
		public void Cancel(){
			at.cancel();
		}
		public void setDialogView(){
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = li.inflate(R.layout.changepasswordlayout, null);
			op = (EditText) v.findViewById(R.id.oldPassword);
			np = (EditText) v.findViewById(R.id.newPassword);
			rnp = (EditText) v.findViewById(R.id.repeatNewPassword);
			ab.setView(v);
		}
		public void setPositiveButton(String title){
			ab.setPositiveButton(title, this);
		}
		public void setNegetiveButton(String title){
			ab.setNegativeButton(title, this);
		}
		public void createDialog(){
			at = ab.create();
		}
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			switch(which){
			case DialogInterface.BUTTON_POSITIVE:
				updatePassword();
			case DialogInterface.BUTTON_NEGATIVE:
				at.cancel();
			}
		}
		public void show(){
			at.show();
		}
		public void updatePassword(){
			if(op.getText()!=null&&op.getText().toString().equals(l.get(5))){
				String newPassword = np.getText().toString();
				String newrPassword = rnp.getText().toString();
				if(newPassword.equals(newrPassword)){
					UpdatePassword up = new UpdatePassword(newPassword);
					up.execute();
				}
                else{
                    Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_SHORT).show();
                }
			}
            else{
                Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_SHORT).show();
            }
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofilelayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        CustomActionBar cab = new CustomActionBar(toolbar);
        setSupportActionBar(cab.getActionBar(getResources().getString(R.string.app_name),R.drawable.ic_launcher,Color.WHITE));
		ID = getIntent().getExtras().getInt("id");
		get_detail.execute();
		fm = getSupportFragmentManager();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.profilemenu, menu);
		if(EDIT==1) {
			menu.getItem(0).setVisible(false);
			menu.getItem(1).setVisible(true);
		}
		else {
			menu.getItem(0).setVisible(true);
			menu.getItem(1).setVisible(false);
		}
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch(id){
		case R.id.editProfile:
			addEditFragment();
			break;
		case R.id.viewProfile:
			addViewFragment();
			break;
		case R.id.changePassword:
			cpd = new CreatePasswordChangeDialog("Change password", this);
			cpd.setDialogView();
			cpd.setPositiveButton("Change password");
			cpd.setNegetiveButton("Cancel");
			cpd.createDialog();
			cpd.show();
		
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(fm.getBackStackEntryCount()>0) {
			EDIT = 0;
			invalidateOptionsMenu();
			
		}
		super.onBackPressed();
	}
	public void addViewFragment(){
		Bundle args = new Bundle();
		args.putStringArrayList("detail", l);
		ViewProfileFragment vpf = new ViewProfileFragment();
		vpf.setArguments(args);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fragmentContainer, vpf);
		fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
		ft.commit();
		EDIT = 0;
		invalidateOptionsMenu();
	}
	public void addEditFragment(){
		Bundle args = new Bundle();
		args.putStringArrayList("detail", l);
		args.putInt("id", ID);
		EditProfileFragment epf = new EditProfileFragment();
		epf.setArguments(args);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.fragmentContainer, epf,"EPF");
        ft.addToBackStack(null);
		ft.commit();
		EDIT = 1;
		invalidateOptionsMenu();
	}
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		getDetail();
		addViewFragment();
	}
	private void getDetail(){
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("id", ID+""));
		data.add(new BasicNameValuePair("tag", "3"));
		WebService web = new WebService();
		l =  web.getUserDetail(data);
	}
	@Override
	public void onCancelUpdate() {
		// TODO Auto-generated method stub
		addViewFragment();
	}
	AsyncTask<Void, Void, Void> get_detail = new AsyncTask<Void, Void, Void>() {
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd = new ProgressDialog(UserProfileActivity.this);
			pd.setMessage("Loading....");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getDetail();
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			addViewFragment();
		};
	};
	public class UpdatePassword extends AsyncTask<Void, Void, Integer>{

		private String newPassword;
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd = new ProgressDialog(UserProfileActivity.this);
			pd.setMessage("Upating....");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}
		public UpdatePassword(String newPassword) {
			// TODO Auto-generated constructor stub
			this.newPassword = newPassword;
		}
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("tag", "5"));
			data.add(new BasicNameValuePair("password", newPassword));
			data.add(new BasicNameValuePair("id", ID+""));
			WebService web = new WebService();
			return web.updateDetail(data);
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			cpd.Cancel();
			if(result==1) Toast.makeText(UserProfileActivity.this, "Password change successfully", Toast.LENGTH_SHORT).show();
		}
	}
}
