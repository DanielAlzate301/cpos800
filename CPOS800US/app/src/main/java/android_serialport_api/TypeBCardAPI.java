package android_serialport_api;

import java.io.IOException;

import com.authentication.utils.DataUtils;

import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

public class TypeBCardAPI {

	public static final int TIME_OUT = 1;

	public static final int NO_RESPONSE = 2;

	public static final int CERTIFICATION_SUCCESS = 3;

	public static final int CERTIFICATION_FAIL = 4;

	public static final int CRC_ERROR = 5;

	public static final int READ_SUCCESS = 6;

	public static final int READ_FAIL = 7;

	public static final int WRITE_SUCCESS = 8;

	public static final int WRITE_FAIL = 9;

	public static final int DESELECT_SUCCESS = 10;

	public static final int ACTIVE_SUCCESS = 11;


	public static final int REQUEST_SUCCESS = 12;

	private byte[] buffer = new byte[100];

	private static final byte[] SWITCH_COMMAND = "D&C00040104".getBytes();

	private static final String END_IDENTIFY = "\r\n";


	private byte AFI;

	/**
	 * αΨһ��Ƭ��ʶ����Pseudo-Unique PICC Identifier��
	 */
	private String PUPI;

	/**
	 * Ӧ�����ݣ�Application Data����4 ���ֽڣ��� �����̿ɶ���Ŀ�Ƭר�ñ�ʶ��λ�ڴ洢����0 ҳ�ĸ�4 ����
	 * ��Ƭ�����������Ϣ���ڸ��߶������߿�Ƭ�ľ���Ӧ�ã�ʹ���������ڷ��ֶ��ſ�Ƭʱ����ѡ����Ҫ�Ŀ�Ƭ
	 */
	private String application_data;

	/**
	 * ��Ƭ��ʶ����Card IDentifier��
	 * ÿ�����ڼ���״̬�µĿ�Ƭӵ��Ψһ��ͨ���ţ�����ȡֵ��ΧΪ��0~14������15������,��4bitλ��ʾ��0000~1110��
	 */
	private String CID;

	private static final byte PAGE0 = 0;
	private static final byte PAGE1 = 4;
	private static final byte PAGE2 = 8;
	private static final byte PAGE3 = 12;

	private static final byte READ = 2;
	private static final byte WRITE = 3;

	private final Message msg = new Message();

	private void clearMessage() {
		msg.what = -1;
		msg.obj = null;
	}

	/**
	 * ��Ƭ���к�
	 */
	private String serialNum;

	private boolean isSwitch = false;

	private boolean switchStatus() {
		SerialPortManager.getInstance().write(SWITCH_COMMAND);
		Log.i("whw", "SWITCH_COMMAND hex=" + new String(SWITCH_COMMAND));
		SystemClock.sleep(500);
		return true;
	}

	/**
	 * Ӧ������ʶ����Application Family Identifier������ƬԤѡӦ�ñ�׼ AFI
	 * �����������ָ����ʹ�����ͣ�����Ԥѡ��Ƭ��ֻ�п�Ƭ��Ӧ��Ϊָ����AFI ʱ����Ƭ�Ż�Ӧ������ָ� ��Ƭ��AFI �ɶ��ơ���AFI
	 * Ϊ��0x00��ʱ�����п�Ƭ����Ӧ�𣨲�Ԥѡ����
	 * ������ɹ�������8�ֽڵ�����
	 * ��ʶ����PUPI����4 ���ֽڣ�����Ƭ��Ψһ���кŵĵ�4 ���ֽڡ�
	 * Ӧ�����ݣ�Application Data����4 ���ֽڣ��������̿ɶ���Ŀ�Ƭר�ñ�ʶ��λ�ڴ洢����0 ҳ�ĸ�4 ���ֽڡ�
	 */
	public Message request(byte afi) {
		if (!isSwitch) {
			switchStatus();
			isSwitch = true;
		}

		StringBuffer commandStr = new StringBuffer("f05");
		commandStr.append(DataUtils.toHexString1(afi));
		commandStr.append("00");
		commandStr.append(END_IDENTIFY);
		Log.i("whw", "request=" + commandStr.toString());
		SerialPortManager.getInstance().write(commandStr.toString().getBytes());
		int length = SerialPortManager.getInstance().read(buffer, 3000, 30);
		Log.i("whw", "length=" + length);
		String result = null;
		if (length > 0) {
			byte[] data = new byte[length];
			System.arraycopy(buffer, 0, data, 0, length);
			result = new String(data);
			Log.i("whw", "temp str=" + result);
			PUPI = result.substring(2, 10);
			application_data = result.substring(10, 18);
			Log.i("whw", "PUPI=" + PUPI);
			Log.i("whw", "application_data=" + application_data);
		}

		clearMessage();
		if (result == null) {
			msg.what = TIME_OUT;
		} else {
			if (length == 26) {
				String dataStr = result.substring(2, 18);
				msg.obj = DataUtils.hexStringTobyte(dataStr);
				msg.what = REQUEST_SUCCESS;
			} else {
				msg.what = NO_RESPONSE;
			}
		}
		return msg;
	}

	/**
	 * ��Ƭ��ʶ����Card IDentifier��
	 * ÿ�����ڼ���״̬�µĿ�Ƭӵ��Ψһ��ͨ���ţ�����ȡֵ��ΧΪ��0~14������15������,��4bitλ��ʾ��0000~1110��
	 * cid������ʮ�������ַ�'0'~'e'��ʾ ���ַ�'f'����
	 * �������ɹ�   ACTIVE_SUCCESS  ������8�ֽڵ����кţ�serialNum��
	 */
	public Message active(byte[] pupi, char cid) {
		StringBuffer commandStr = new StringBuffer("f1d");
		commandStr.append(DataUtils.toHexString(pupi));
		commandStr.append("0000000" + cid);
		commandStr.append("00");
		commandStr.append(END_IDENTIFY);

		Log.i("whw", "active=" + commandStr.toString());
		SerialPortManager.getInstance().write(commandStr.toString().getBytes());
		int activelength = SerialPortManager.getInstance().read(buffer, 3000,
				30);
		Log.i("whw", "activelength=" + activelength);
		String result = null;
		if (activelength > 0) {
			byte[] data = new byte[activelength];
			System.arraycopy(buffer, 0, data, 0, activelength);
			result = new String(data);
			Log.i("whw", "active str=" + result);

		}

		clearMessage();
		if (result == null) {
			msg.what = TIME_OUT;
		} else {
			if (activelength == 22) {
				serialNum = result.substring(4, 20);
				Log.i("whw", "SerialNum=" + serialNum);
				msg.obj = DataUtils.hexStringTobyte(serialNum);
				msg.what = ACTIVE_SUCCESS;
			} else {
				msg.what = NO_RESPONSE;
			}
		}
		return msg;
	}

	private Message read(char cid, byte pageId, String address) {
		StringBuffer commandStr = new StringBuffer("f");
		commandStr.append(cid);
		commandStr.append(DataUtils.toHexString1((byte) (pageId + READ))
				.substring(1));
		commandStr.append(address);
		commandStr.append(END_IDENTIFY);
		Log.i("whw", "read=" + commandStr);
		SerialPortManager.getInstance().write(commandStr.toString().getBytes());
		int readlength = SerialPortManager.getInstance().read(buffer, 3000, 30);
		String result = null;
		if (readlength > 0) {
			byte[] data = new byte[readlength];
			System.arraycopy(buffer, 0, data, 0, readlength);
			result = new String(data);
			Log.i("whw", "read result str=" + result + "   length="
					+ readlength);

		}

		clearMessage();
		byte[] data = null;
		if (result == null) {
			msg.what = TIME_OUT;
		} else {
			if (result.length() == 20) {
				data = DataUtils.hexStringTobyte(result.substring(2, 18));
				msg.what = READ_SUCCESS;
				msg.obj = data;
				Log.i("whw", "obj length=" + data.length);
			} else if (result.length() == 4) {
				if (result.charAt(1) == '1') {
					msg.what = READ_FAIL;
				} else if (result.charAt(1) == '2') {
					msg.what = CRC_ERROR;
				}
			} else {
				msg.what = NO_RESPONSE;
			}
		}
		Log.i("whw", "msg.what=" + msg.what + "  obj" + msg.obj);
		return msg;
	}

	private Message write(char cid, byte pageId, String address, String dataStr) {
		StringBuffer commandStr = new StringBuffer("f");
		commandStr.append(cid);
		commandStr.append(DataUtils.toHexString1((byte) (pageId + WRITE))
				.substring(1));
		commandStr.append(address);
		commandStr.append(dataStr);
		commandStr.append(END_IDENTIFY);
		Log.i("whw", "write=" + commandStr);
		SerialPortManager.getInstance().write(commandStr.toString().getBytes());
		int writelength = SerialPortManager.getInstance()
				.read(buffer, 3000, 30);
		String result = null;
		if (writelength > 0) {
			byte[] data = new byte[writelength];
			System.arraycopy(buffer, 0, data, 0, writelength);
			result = new String(data);
			Log.i("whw", "write result str=" + result);
		}

		clearMessage();
		if (result == null) {
			msg.what = TIME_OUT;
		} else {
			if (result.charAt(1) == '0') {
				msg.what = WRITE_SUCCESS;
			} else if (result.charAt(1) == '1') {
				msg.what = WRITE_FAIL;
			} else if (result.charAt(1) == '2') {
				msg.what = CRC_ERROR;
			} else {
				msg.what = NO_RESPONSE;
			}
		}
		return msg;

	}

	public Message authentication(char cid, byte[] key) {
		return write(cid, PAGE2, "00", DataUtils.toHexString(key));

	}

	public Message deselect(char cid) {
		StringBuffer commandStr = new StringBuffer("f");
		commandStr.append(cid);
		commandStr.append("8");// 1000
		commandStr.append(END_IDENTIFY);
		Log.i("whw", "deselect=" + commandStr);
		SerialPortManager.getInstance().write(commandStr.toString().getBytes());
		int disSelectlength = SerialPortManager.getInstance().read(buffer,
				3000, 30);
		String result = null;
		if (disSelectlength > 0) {
			byte[] data = new byte[disSelectlength];
			System.arraycopy(buffer, 0, data, 0, disSelectlength);
			result = new String(data);
			Log.i("whw", "disSelect str=" + result);
		}

		clearMessage();
		if (result == null) {
			msg.what = TIME_OUT;
		} else {
			if (result.length() == 4) {
				msg.what = DESELECT_SUCCESS;
			} else {
				msg.what = NO_RESPONSE;
			}
		}
		return msg;
	}

	/**
	 * д�û���ʶ��
	 * 
	 * @param identify
	 * @return
	 */
	public Message writeIdentify(char cid, byte[] identify, String address) {
		// return write(PAGE1, "00", identifyCommand);
		Log.i("whw", "writeIdentify----------" + address);
		return write(cid, PAGE1, address, DataUtils.toHexString(identify));
	}

	/**
	 * ���û���ʶ��
	 * 
	 * @return
	 */
	public Message readIdentify(char cid, String address) {
		Log.i("whw", "readIdentify----------" + address);
		return read(cid, PAGE1, address);
	}

	/**
	 * д��Կ
	 * 
	 * @param key
	 *            Ϊ8���ֽڵ���Կ
	 * @return
	 */
	public Message writeKey(char cid, byte[] key) {
		// return write(PAGE2, "00", "0807060504030201");
		Log.i("whw", "writeKey----------");
		return write(cid, PAGE2, "00", DataUtils.toHexString(key));

	}

	/**
	 * ����Կ
	 */
	public Message readKey(char cid) {
		Log.i("whw", "readKey----------");
		return read(cid, PAGE2, "00");
	}

	public Message writePermission(char cid, byte[] applicationData, byte afi,
			byte[] permission) {
		StringBuffer commandStr = new StringBuffer();
		commandStr.append(DataUtils.toHexString(applicationData));
		commandStr.append(DataUtils.toHexString1(afi));
		commandStr.append(DataUtils.toHexString(permission));
		Log.i("whw", "writePermission=" + commandStr);
		return write(cid, PAGE0, "00", commandStr.toString());
	}

	public Message readPermission(char cid) {
		return read(cid, PAGE0, "00");

	}

	public Message readData(char cid) {
		return read(cid, PAGE3, "00");
	}

	public Message writeData(char cid, byte[] data) {
		return write(cid, PAGE3, "00", DataUtils.toHexString(data));
	}

	public void release(char cid) {
		request((byte) 0x00);
		active(DataUtils.hexStringTobyte(PUPI), cid);

		writeIdentify(cid, new byte[] { (byte) 0xaa, 0x22, 0x33, 0x44, 0x55,
				0x66, 0x77, (byte) 0x88 }, "00");

		readIdentify(cid, "00");

		writeKey(cid, new byte[] { 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02,
				0x01 });
		readKey(cid);

		writeData(cid, new byte[] { (byte) 0xbb, 0x01, 0x0f, 0x0f, 0x04, 0x05,
				0x06, 0x07 });
		readData(cid);

		// writePermission(new byte[] { 0x01, 0x02, 0x03, 0x04 }, (byte) 0x21,
		// new byte[] { 0x1B, (byte) 0xE4, 0x1B });
		// readPermission();

		deselect(cid);

	}

	public void comsume(char cid) {
		request((byte) 0x21);
		active(DataUtils.hexStringTobyte(PUPI), cid);
		read(cid, PAGE1, "00");
		int aut = authentication(cid, new byte[] { 0x08, 0x07, 0x06, 0x05,
				0x04, 0x03, 0x02, 0x01 }).what;
		Log.i("whw", "aut=" + aut);
		readData(cid);
		writeData(cid, new byte[] { (byte) 0xbb, 0x01, 0x0f, 0x0f, 0x04, 0x05,
				0x06, 0x07 });
		readData(cid);
		deselect(cid);
	}

}
