package com.example.test;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener{
	private TextView myTV;
	private Button myBtn;
	
	 public void showNoticeDialog() {
	        // Create an instance of the dialog fragment and show it
	        DialogFragment dialog = new NoticeDialogFragment();
	        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
	    }

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        myTV = (TextView)findViewById(R.id.TextView01);
        myBtn = (Button)findViewById(R.id.Button01);
        
        myBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showNoticeDialog();
			} 
		});
    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog,String uname) {
		// TODO Auto-generated method stub
		myTV.setText("user " +uname + " ,welcome！");
		myBtn.setVisibility(View.GONE);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		myTV.setText("fire been cancelled");
	}
	
}