package com.authentication.activity;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;
import android.widget.NumberPicker;

public class NumberPickerDialog extends Dialog implements OnClickListener {
	
	private NumberPicker mNumberPicker;  
	private Button btn_ok;
	private Button btn_cancel; 
	private short initNumber;  
	private int mode;  
	
	public interface OnMyNumberSetListener {  
        /** 
         * ���ֱ��趨֮��ִ�д˷��� 
         *  
         * @param number 
         *            ��ǰ���ֿ����ַ��� 
         * @param mode 
         *            �����Ա�ʶ������ 
         */  
        void onNumberSet(short number, int mode);  
    }  
  
    private OnMyNumberSetListener mListener;  
  
    public NumberPickerDialog(Context context, OnMyNumberSetListener listener,  
            short number, int mode) {  
        super(context);   
        this.mListener = listener;  
        this.initNumber = number;  
        this.mode = mode;  
    }  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.inputnumber_dialog_hk); 
        setTitle("NumberPickerD");
  
        mNumberPicker = (NumberPicker) findViewById(R.id.nump_input);  
        mNumberPicker.setMaxValue(128);  
        mNumberPicker.setMinValue(0);  
        mNumberPicker.setValue(initNumber);
        
        btn_ok = (Button) findViewById(R.id.ok);  
        btn_cancel = (Button) findViewById(R.id.cancel);
        btn_ok.setOnClickListener(this);  
        btn_cancel.setOnClickListener(this);  
        setCancelable(false);  
    }  
    
    @Override  
    public void onClick(View v) {  
        switch (v.getId()) {  
        case R.id.ok:  
            mListener.onNumberSet((short)mNumberPicker.getValue(), mode);  
            dismiss();  
            break;  
        case R.id.cancel:  
            dismiss();  
            break;
        }  
    }  
}
