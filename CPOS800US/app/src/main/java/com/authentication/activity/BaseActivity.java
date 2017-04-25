package com.authentication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android_serialport_api.SerialPortManager;

public class BaseActivity extends Activity {
	protected MyApplication application;
	protected HandlerThread handlerThread;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MyApplication) getApplicationContext();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		handlerThread = application.getHandlerThread();
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		handlerThread.getLooper().quit();
//		handlerThread.quit();
		handlerThread = null;
	}
}