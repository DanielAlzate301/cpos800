
package com.authentication.utils;

/**
 * @author Administrator
 *
 */
public class Constant {


	public static final int CONTACT_IC = 0x0;//�Ӵ�ʽIC��
	public static final int CONTACTLESS_IC = 0x01;//RFID
	public static final int PRINTER = 0x02;//��ӡ��
	public static final int UHF_TAG = 0x03;//UHF
	public static final int IDCARD = 0x04;//INSIDE
	
	public static final int FINGER_ID  = 0x03;//INSIDE
	

	public static byte[] print_command = {0x0A};//��ӡ����
	public static byte[] initPrinter_command ={0x1B,0x40};//��ʼ����ӡ��
	public static byte[] setBold_command = {0x1B,0x45,0x01};//���üӴ�
	public static byte[] quitBold_command = {0x1B,0x45,0x00};//ȡ���Ӵ�
	public static byte[] setTimesHeight_command ={0x1B,0x21,0x01};//���ñ���
	public static byte[] setTimesWeight_command ={0x1B,0x21,0x10};//���ñ���
	public static byte[] setTimesHWeight_command ={0x1B,0x21,0x11};//���ñ��߱���
	public static byte[] quitTimesHWeight_command ={0x1B,0x21,0x00};//ȡ�����߱���
	public static byte[] setUnderLine_command = {0x1B,0x2D,0x00};//���»���
	public static byte[] setAlignType_command = {0x1B,0x61,0x00};//���������
	public static byte[] printFlashPic_command = {0x1C,0x2D,0x00};// ��ӡflashͼƬ
	public static byte[] printQrcode_command = {0x1D,0x5A,0x00};// Ĭ��Qr��
}
