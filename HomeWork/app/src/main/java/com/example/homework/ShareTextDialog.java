package com.example.homework;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ShareTextDialog extends DialogFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View dialogView = inflater.inflate(R.layout.sharetextlayout, container);
		getDialog().setTitle("Message Dialog");
		Button shareButton = (Button) dialogView.findViewById(R.id.shareButton);
        Button cancel = (Button)dialogView.findViewById(R.id.button2);
		final EditText shareText = (EditText) dialogView.findViewById(R.id.shareMessage);
		shareButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = shareText.getText().toString();
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT, text);
				startActivity(Intent.createChooser(shareIntent, "Choose a method"));
			}
		});
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
		return dialogView;
	}
	
}
