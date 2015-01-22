package com.example.homework;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewProfileFragment extends Fragment{

	TextView name,email,bdate,phone,gender;
	List<String> l;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle b = this.getArguments();
		l = b.getStringArrayList("detail");
		View v = inflater.inflate(R.layout.viewprofilelayout, container,false);
	    name = (TextView) v.findViewById(R.id.nameView);
	    email = (TextView) v.findViewById(R.id.emailView);
	    bdate = (TextView) v.findViewById(R.id.bdateView);
	    phone = (TextView) v.findViewById(R.id.phoneView);
	    gender = (TextView) v.findViewById(R.id.genderView);
	    name.setText("Name : "+l.get(0));
	    email.setText("Email : "+l.get(1));
	    bdate.setText("Birth date : "+l.get(2));
	    phone.setText("Phone no : "+l.get(3));
	    gender.setText("Gender : "+l.get(4));
		return v;
	}
}
