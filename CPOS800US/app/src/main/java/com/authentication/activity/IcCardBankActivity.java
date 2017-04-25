package com.authentication.activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.authentication.asynctask.AsynIcCardBank;
import com.authentication.asynctask.AsynIcCardBank.OnGetIcCardInfoListener;
import com.authentication.utils.DataUtils;
import com.authentication.utils.ToastUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android_serialport_api.SerialPortManager;


public class IcCardBankActivity extends Activity implements OnClickListener {
	private boolean isup;
	private ExecutorService singleThreadExecutor;


	private Button btnGetCardInfo;

	private Button btnClear;

	private TextView txtCardInfo;

	private ProgressDialog progressDialog;
	private AsynIcCardBank asynIcCardBank;
	private MyApplication application;

	private Handler handler = new Handler();

	private Runnable up = new Runnable() {

		@Override
		public void run() {
			while (isup) {
				if (SerialPortManager.getInstance().isUpGpio()) {
					isup = false;
					handler.post(new Runnable() {

						@Override
						public void run() {
							cancleProgressDialog();
							Toast.makeText(getApplicationContext(),
									R.string.upGpioSuccess, Toast.LENGTH_SHORT)
									.show();
						}
					});
				}
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iccardbank);
		initView();
		initData();
	}

	private void initView() {
		singleThreadExecutor = Executors.newSingleThreadExecutor();

		btnGetCardInfo = (Button) findViewById(R.id.GetCardInfo_ICCard);
		btnGetCardInfo.setOnClickListener(this);

		btnClear = (Button) findViewById(R.id.ClearBank_ICCard);
		btnClear.setOnClickListener(this);

		txtCardInfo = (TextView) findViewById(R.id.CardInfo_ICCard);
	}

	private void initData() {
		application = (MyApplication) getApplicationContext();
		asynIcCardBank = new AsynIcCardBank(application.getHandlerThread()
				.getLooper());

		asynIcCardBank
				.setOnGetIcCardInfoListener(new OnGetIcCardInfoListener() {

					@Override
					public void onGetIcCardInfoSuccess(String result) {
						cancleProgressDialog();
						ToastUtil.showToast(IcCardBankActivity.this,
								R.string.getCardInfoSuccess);
						IcCardBankActivity.this.txtCardInfo.setText(result);
					}

					@Override
					public void onGetIcCardInfoFail(int resultCode) {
						cancleProgressDialog();
						ToastUtil.showToast(IcCardBankActivity.this,
								R.string.getCardInfoFail);
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.GetCardInfo_ICCard:
			showProgressDialog("Now,it's getting the information of ic card.");
			if (!DataUtils.isContactless) {
				this.asynIcCardBank.getIcCardInfo(0);
			} else {
				this.asynIcCardBank.getIcCardInfo(1);
			}
			break;
		case R.id.ClearBank_ICCard:
			txtCardInfo.setText("");
			break;
		default:
			break;
		}
	}

	private void showProgressDialog(String message) {
		Log.i("cy",
				"Enter function IcCardBankActivity-showProgressDialog(String)");

		this.progressDialog = new ProgressDialog(this);
		this.progressDialog.setMessage(message);
		if (!this.progressDialog.isShowing()) {
			this.progressDialog.show();
		}
	}

	private void showProgressDialog(int resId) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getResources().getString(resId));
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	private void cancleProgressDialog() {
		Log.i("cy", "Enter function IcCardBankActivity-cancleProgressDialog()");

		if (null != this.progressDialog && this.progressDialog.isShowing()) {
			this.progressDialog.cancel();
			this.progressDialog = null;
		}
	}

	@Override
	protected void onResume() {
		SerialPortManager.getInstance().closeSerialPort(1);
		isup = true;
		showProgressDialog(R.string.isopenGpio);
		SerialPortManager.getInstance().openSerialPort();
		singleThreadExecutor.execute(up);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		SerialPortManager.getInstance().closeSerialPort(1);
		super.onDestroy();
	}
}