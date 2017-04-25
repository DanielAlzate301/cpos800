
package com.authentication.asynctask;

import com.authentication.logic.IcCardBankAPI;
import com.authentication.logic.IcCardBankAPI.Result;
import com.authentication.utils.DataUtils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class AsynIcCardBank extends Handler{
private static final int GETICCARDINFO = 0x00;
	
	private IcCardBankAPI icCardBankAPI;
	private Handler workerThreadHandler;
	
	private String result;

	public AsynIcCardBank(Looper looper) 
	{
		Log.i("cy", "Enter function AsynIcCardBank-AsynIcCardProg()");
		this.workerThreadHandler = createHandler(looper);	
		this.icCardBankAPI = new IcCardBankAPI();
	}
	
	protected Handler createHandler(Looper looper) 
	{
		Log.i("cy", "Enter function AsynIcCardBank-createHandler()");
		return new WorkerHandler(looper);
	}
	
	public void getIcCardInfo(int flag) 
	{
		Log.i("cy", "Enter function AsynIcCardBank-getIcCardInfo()");
		this.workerThreadHandler.obtainMessage(GETICCARDINFO, flag, -1).sendToTarget();
	}
	
	protected int getCardInfo(int flag)
	{
		if(0 == flag)
		{
			int ret = icCardBankAPI.resetCard(0);
			if(Result.FAILURE == ret)
			{
				return Result.FAILURE;
			}
			byte[] sfData = icCardBankAPI.selectFile();
			if(null == sfData)
			{
				return Result.FAILURE;
			}
			result = "SelectFiles:" + "\r\n" + DataUtils.toHexString(sfData) + "\r\n";
		}
		else
		{
			int ret = icCardBankAPI.resetCard(1);
			if(Result.FAILURE == ret)
			{
				return Result.FAILURE;
			}
			
			byte[] sfData=icCardBankAPI.selectFileContless();
			if(null == sfData)
			{
				return Result.FAILURE;
			}
			result = "FileContless" + "\r\n" + DataUtils.toHexString(sfData) + "\r\n";
		}

		byte[] sfNextData = icCardBankAPI.selectFileNext();
		if(null == sfNextData)
		{
			return Result.FAILURE;
		}
		result = result + "FileNext:" + "\r\n" + DataUtils.toHexString(sfNextData) + "\r\n";
		
		byte[] rdData = icCardBankAPI.readRecord();
		if(null == rdData)
		{
			return Result.FAILURE;
		}
		result = result + "�ReadRecACB:" + "\r\n" + DataUtils.toHexString(rdData) + "\r\n";
		
		byte[] gtData = icCardBankAPI.getData();
		if(null == gtData)
		{
			return Result.FAILURE;
		}
		result = result + "ResultAsyncCB" + "\r\n" + DataUtils.toHexString(gtData);
		
		return Result.SUCCESS;
	}
	
	protected class WorkerHandler extends Handler 
	{
		public WorkerHandler(Looper looper) 
		{
			super(looper);
			Log.i("cy", "Enter function AsynIcCardBank-WorkerHandler-WorkerHandler()");
		}
		
		public void handleMessage(Message msg) 
		{	
			Log.i("cy", "Enter function AsynIcCardBank-WorkerHandler-handleMessage()");

			switch (msg.what) 
			{
			case GETICCARDINFO:
				int ret = AsynIcCardBank.this.getCardInfo(msg.arg1);
				AsynIcCardBank.this.obtainMessage(GETICCARDINFO, ret, -1, result).sendToTarget();
				break;
				
			default:
				break;
				
			}
			
		}
		
	}
	
	public interface OnGetIcCardInfoListener 
	{	
		void onGetIcCardInfoSuccess(String result);
		void onGetIcCardInfoFail(int resultCode);
	}
	
	private OnGetIcCardInfoListener onGetIcCardInfoListener;
	
	public void setOnGetIcCardInfoListener(OnGetIcCardInfoListener onGetIcCardInfoListener) 
	{
		Log.i("cy", "Enter function AsynIcCardBank-setOnGetIcCardInfoListener()");
		this.onGetIcCardInfoListener = onGetIcCardInfoListener;
	}
	
	public void handleMessage(Message msg) 
	{
		Log.i("cy", "Enter function AsynIcCardBank-handleMessage()");
		super.handleMessage(msg);
		
		switch (msg.what) 
		{		
		case GETICCARDINFO:
			if(null != this.onGetIcCardInfoListener)
			{
				if(0 != msg.arg1)
				{
					if(null != msg.obj)
					{
						this.onGetIcCardInfoListener.onGetIcCardInfoSuccess((String)msg.obj);
					}
				}
				else
				{
					this.onGetIcCardInfoListener.onGetIcCardInfoFail(msg.arg1);
				}
			}
			break;
			
		default:
			break;	
			
		}
		
	}
}