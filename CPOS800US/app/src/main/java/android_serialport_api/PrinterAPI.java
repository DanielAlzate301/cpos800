package android_serialport_api;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import com.authentication.utils.DataUtils;

import android_serialport_api.SerialPortManager;

public class PrinterAPI {
	private int maxLen = 0;
	private int maxLength = 250;
	private byte[] buffer = new byte[50 * 1024];
	private static final int MAXPDLENGTH = 0x255;
	private byte[] maxPrintData = new byte[MAXPDLENGTH];

	private byte[] print_command = { 0x0A };
	private byte[] initPrinter_command = { 0x1B, 0x40 };
	private byte[] set_command = { 0x1B, 0x21, 0x00 };
	private byte[] setUnderLine_command = { 0x1B, 0x2D, 0x00 };
	private byte[] setAlignType_command = { 0x1B, 0x61, 0x00 };
	private byte[] printFlashPic_command = { 0x1C, 0x2D, 0x00 };
	private byte[] printQrcode_command = { 0x1D, 0x5A, 0x00 };
	private byte[] printFont_command = { 0x1B, 0x4D, 0x00 };
	private byte[] printDouble_command = { 0x1B, 0x47, 0x00 };
	private byte[] line_spacing = { 0x1B, 0x33, 0x00 };
	private byte[] word_spacing = { 0x1B, 0x20, 0x00 };


	public class PrinterStatus {
		public boolean isPaper = false;
		public boolean isHot = false;
		public boolean isPrint = false;
	}

	public synchronized void openPrint() {
		SerialPortManager.getInstance().openSerialPortPrinter();
	}

	public synchronized void closePrint() {
		SerialPortManager.getInstance().closeSerialPort(2);
	}

	public synchronized void initPrint() {
		SerialPortManager.getInstance().write(topackage(initPrinter_command));
	}

	public synchronized void doPrintPaper() {
		SerialPortManager.getInstance().write(topackage(print_command));
	}

	public void printBarcode(String str, int mBarcodeType) {
		try {
			byte[] bytes = str.getBytes("GBK");
			byte[] realBytes = new byte[bytes.length + 4];
			realBytes[0] = 0x1D;
			realBytes[1] = 0x6B;
			realBytes[2] = (byte) mBarcodeType;
			realBytes[3] = (byte) bytes.length;
			byte[] tmpBytes = DataUtils.hexStringTobyte(DataUtils
					.str2Hexstr(str));
			for (int i = 0; i < bytes.length; i++)
				realBytes[4 + i] = tmpBytes[i];
			SerialPortManager.getInstance().write(topackage(realBytes));
			doPrintPaper();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public synchronized int printQrcode(String str, int codeType) {
		printQrcode_command[2] = (byte) codeType;
		SerialPortManager.getInstance().write(topackage(printQrcode_command));

		try {
			byte[] bytes = str.getBytes("GBK");
			byte[] realBytes = new byte[bytes.length + 4];
			realBytes[0] = 0x1B;
			realBytes[1] = 0x5A;
			realBytes[2] = 0x00;
			realBytes[3] = (byte) bytes.length;
			byte[] tmpBytes = DataUtils.hexStringTobyte(DataUtils
					.str2Hexstr(str));
			for (int i = 0; i < bytes.length; i++)
				realBytes[4 + i] = tmpBytes[i];
			SerialPortManager.getInstance().write(topackage(realBytes));
			doPrintPaper();
			doPrintPaper();
			doPrintPaper();
			doPrintPaper();
			doPrintPaper();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public synchronized void printFlashImage(int imageType) {
		printFlashPic_command[2] = (byte) imageType;
		SerialPortManager.getInstance().write(topackage(printFlashPic_command));
		doPrintPaper();
		doPrintPaper();
		doPrintPaper();
		doPrintPaper();
		doPrintPaper();
	}

	public synchronized void printPaper(String str) {
		String mstr = DataUtils.str2Hexstr(str);
		int len = mstr.length() / 2;
		if (len > maxLength) {
			int n = len / 250 + 1;
			String newstr;
			for (int i = 0; i < n; i++) {
				if (i != n - 1) {
					newstr = mstr.substring(500 * i, 500 * (i + 1));
					byte[] data = DataUtils.hexStringTobyte(newstr);
					// byte[] newdata = new byte[data.length + 1];
					// System.arraycopy(data, 0, newdata, 0, data.length);
					// newdata[newdata.length - 1] = (byte) 0x0a;
					SerialPortManager.getInstance().write(topackage(data));
				} else {
					newstr = mstr.substring(500 * i, mstr.length());
					byte[] data = DataUtils.hexStringTobyte(newstr);
					byte[] newdata = new byte[data.length + 1];
					System.arraycopy(data, 0, newdata, 0, data.length);
					newdata[newdata.length - 1] = (byte) 0x0a;

					SerialPortManager.getInstance().write(topackage(data));
				}
			}
		} else {
			String newstr = DataUtils.str2Hexstr(str);
			byte[] data = DataUtils.hexStringTobyte(newstr);
			byte[] newdata = new byte[data.length + 1];
			System.arraycopy(data, 0, newdata, 0, data.length);
			newdata[newdata.length - 1] = (byte) 0x0a;

			SerialPortManager.getInstance().write(topackage(newdata));
		}
		doPrintPaper();
		doPrintPaper();
		doPrintPaper();
		doPrintPaper();
		doPrintPaper();
	}


	public synchronized void setUnderLine(int underline) {
		setUnderLine_command[2] = (byte) underline;
		SerialPortManager.getInstance().write(topackage(setUnderLine_command));
	}


	public synchronized void setAlighType(int alignType) {
		setAlignType_command[2] = (byte) alignType;
		SerialPortManager.getInstance().write(topackage(setAlignType_command));
	}


	public synchronized void setKGCU(boolean wide, boolean high, boolean crude,
			boolean underLine) {
		int intWide, intHigh, intCrude, inUnderLine;
		int total;
		if (wide) {
			intWide = 100000;
		} else {
			intWide = 0;
		}
		if (high) {
			intHigh = 10000;
		} else {
			intHigh = 0;
		}
		if (crude) {
			intCrude = 1000;
		} else {
			intCrude = 0;
		}
		if (underLine) {
			inUnderLine = 10000000;
		} else {
			inUnderLine = 0;
		}
		total = intWide + intHigh + intCrude + inUnderLine;
		BigInteger src = new BigInteger(total + "", 2);// ת��ΪBigInteger����
		String ten = src.toString();
		int m = Integer.parseInt(ten);
		set_command[2] = (byte) m;
		SerialPortManager.getInstance().write(topackage(set_command));
		DataUtils.toHexString(topackage(set_command));
	}


	public synchronized void setDouble(boolean tag) {
		if (tag) {
			printDouble_command[2] = (byte) 1;
			SerialPortManager.getInstance().write(
					topackage(printDouble_command));
		} else {
			SerialPortManager.getInstance().write(
					topackage(printDouble_command));
		}
	}


	public synchronized void setLineSpace(int space) {
		line_spacing[2] = (byte) space;
		SerialPortManager.getInstance().write(topackage(line_spacing));
	}


	public synchronized void setWordSpace(int space) {
		word_spacing[2] = (byte) space;
		SerialPortManager.getInstance().write(topackage(word_spacing));
	}


	public synchronized void setFont(int fontType) {
		printFont_command[2] = (byte) fontType;
		SerialPortManager.getInstance().write(topackage(printFont_command));
	}


	public synchronized PrinterStatus getPrinterStatus() {
		maxLen = SerialPortManager.getInstance().readFixedLength(buffer, 1000,
				-1);
		PrinterStatus status = new PrinterStatus();
		maxLen = maxLen - 1;
		if (maxLen < 0)
			maxLen = 0;
		if ((buffer[maxLen] & 0x10) == 16)
			status.isPrint = true;
		else
			status.isPrint = false;
		if ((buffer[maxLen] & 0x04) == 4)
			status.isHot = true;
		else
			status.isHot = false;// ����
		if ((buffer[maxLen] & 0x01) == 1)
			status.isPaper = true;
		else
			status.isPaper = false;
		return status;
	}


	private byte[] topackage(byte[] old) {
		int len = old.length;
		byte[] heade = { (byte) 0xCA, (byte) 0xDF, (byte) 0x00, (byte) 0x35 };
		byte[] cmd = new byte[heade.length + 2 + len + 1];
		System.arraycopy(heade, 0, cmd, 0, heade.length);
		cmd[heade.length] = (byte) 0;
		cmd[heade.length + 1] = (byte) len;
		System.arraycopy(old, 0, cmd, heade.length + 2, len);
		cmd[cmd.length - 1] = (byte) 0xE3;
		return cmd;
	}
}