package com.example.voicerecognition;

import java.util.ArrayList;
import java.util.List;

import com.example.test.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class SpeechRecognitionHelper extends Activity{
	private static final int VOICE_RECOGNITION_REQUEST_CODE=1;
	
	private ListView mList;
	Button mButton;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_main);
      
        mButton=(Button)findViewById(R.id.myButton);
        mButton.setOnClickListener(new myRecognizerIntentListener());
        
        mList=(ListView)findViewById(R.id.myListView);
    }
    
    public class myRecognizerIntentListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				//用Intent来传递语音识别的模式,并且开启语音模式
				Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				//语言模式和自由形式的语音识别
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				//提示语言开始
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请开始语音");
				//开始语音识别
				startActivityForResult(intent,VOICE_RECOGNITION_REQUEST_CODE);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(SpeechRecognitionHelper.this, "找不到语音设备", Toast.LENGTH_LONG).show();
			}
		}
    }
    //语音结束时的回调函数
	@Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==VOICE_RECOGNITION_REQUEST_CODE&&resultCode==RESULT_OK){
			//取得语音的字符
			ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			//设置视图的更新
			mList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));
			String resultsString="";
			for (int i = 0; i < results.size(); i++) {
				resultsString+=results.get(i);
			}
			Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
