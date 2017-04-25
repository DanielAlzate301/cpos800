package com.authentication.logic;

import android_serialport_api.SerialPortManager;

public class MagStripeCardAPI {
	public static class Result {
		public static final int SUCCESS = 1;
		public static final int FAILURE = 0;
		public Object resultInfo;
	}

	private byte[] buffer = new byte[1024];

	public void openMagStripeCard() {
		SerialPortManager.getInstance().openSerialPort();
	}

	public void closeMagStripeCard() {
		SerialPortManager.getInstance().closeSerialPort(1);
	}


	public void send() {
		byte[] toCalcCrc = { 0x04, 0x00, 0x00, 0x04 };
		SerialPortManager.getInstance().write(toCalcCrc);
	}


	public byte[] readCard() {
		int length = SerialPortManager.getInstance().read(buffer, 3000, 100);
		if (0 == length) {
			return null;
		}

		byte[] recvData = new byte[length];

		System.arraycopy(buffer, 0, recvData, 0, length);
		return recvData;
	}
}