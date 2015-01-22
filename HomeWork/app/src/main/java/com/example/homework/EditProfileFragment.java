package com.example.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProfileFragment extends Fragment implements View.OnClickListener,View.OnFocusChangeListener{

	EditText name,email,phone;
    private static EditText bdate;
	ArrayList<String> l;
	Spinner gender;
	int id;
	HashMap<String, Integer> hm = new HashMap<String,Integer>();

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) showDatePicker();
    }

    public interface OnDetailUpdate{
		public void onUpdate();
		public void onCancelUpdate();
	}
    public static void setDate(String date){
        bdate.setText("");
        bdate.append(date);
    }
	OnDetailUpdate userDetail;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = this.getArguments();
		l = args.getStringArrayList("detail");
		id = args.getInt("id");
		hm.put("Male", 1);
		hm.put("Female", 2);
		View v = inflater.inflate(R.layout.editprofilelayout, container, false);
		name = (EditText) v.findViewById(R.id.name);
		email = (EditText) v.findViewById(R.id.email);
		bdate = (EditText) v.findViewById(R.id.bdate);
		phone = (EditText) v.findViewById(R.id.phone);
		gender = (Spinner) v.findViewById(R.id.gender);
		Button save = (Button) v.findViewById(R.id.saveButton);
		Button cancel = (Button) v.findViewById(R.id.cancel);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
        bdate.setOnClickListener(this);
        bdate.setOnFocusChangeListener(this);
		name.append(l.get(0));
		email.append(l.get(1));
		bdate.append(l.get(2));
		phone.append(l.get(3));
    	gender.setSelection(hm.get(l.get(4)));
		return v;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.saveButton:
			updateDetail();
			break;
		case R.id.cancel:
			userDetail.onCancelUpdate();
            break;
        case R.id.bdate:
            showDatePicker();
            break;

		}
		
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		userDetail = (OnDetailUpdate) activity;
	}
	public void updateDetail(){
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		if(!name.getText().toString().equalsIgnoreCase(l.get(0))){
			data.add(new BasicNameValuePair("name", name.getText().toString()));
		}
		if(!email.getText().toString().equalsIgnoreCase(l.get(1))){
			data.add(new BasicNameValuePair("email", email.getText().toString()));
		}
		if(!bdate.getText().toString().equalsIgnoreCase(l.get(2))){
			data.add(new BasicNameValuePair("b_date", bdate.getText().toString()));
		}
		if(!phone.getText().toString().equalsIgnoreCase(l.get(3))){
			data.add(new BasicNameValuePair("phone", phone.getText().toString()));
		}
		if(!gender.getSelectedItem().toString().equalsIgnoreCase(l.get(4))){
			data.add(new BasicNameValuePair("gender", gender.getSelectedItem().toString()));
		}
		if(data.size()>0) {
			data.add(new BasicNameValuePair("tag", "4"));
			data.add(new BasicNameValuePair("id", id+""));
			UpdateDetail ud = new UpdateDetail(data);
			ud.execute();
		}
	}
	private class UpdateDetail extends AsyncTask<Void, Void, Integer>{

		List<NameValuePair> data;
		ProgressDialog pd ;
		public UpdateDetail(List<NameValuePair> data){
			this.data = data;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd = new ProgressDialog(getActivity());
			pd.setMessage("Saving....");
			pd.setIndeterminate(true);
			pd.show();
			pd.setCancelable(false);
		}
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			WebService web = new WebService();
			return web.updateDetail(data);
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
            userDetail.onUpdate();
			pd.dismiss();
		}
	}
    public void showDatePicker(){
        Bundle b = new Bundle();
        String[] date = bdate.getText().toString().split("/");
        b.putInt("day",Integer.valueOf(date[0]));
        b.putInt("month",Integer.valueOf(date[1]));
        b.putInt("year",Integer.valueOf(date[2]));
        BirthDatePicker bdp = new BirthDatePicker();
        bdp.setArguments(b);
        bdp.show(getActivity().getFragmentManager(), "datepicker");
    }
}
