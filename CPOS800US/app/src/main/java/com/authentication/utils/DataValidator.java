package com.authentication.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataValidator {

	public static boolean isIntege(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^-?[1-9]\\d*$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}


	public static boolean isIntege1(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[1-9]\\d*$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}


	public static boolean isIntege2(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^-[1-9]\\d*$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}


	public static boolean isNum(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^([+-]?)\\d*\\.?\\d+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��������������� + 0��
	 * 
	 * @param value
	 */
	public static boolean isNum1(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[1-9]\\d*|0$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ǹ����������� + 0��
	 * 
	 * @param value
	 */
	public static boolean isNum2(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^-[1-9]\\d*|0$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ǹ�����
	 * 
	 * @param value
	 */
	public static boolean isDecmal(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^([+-]?)\\d*\\.\\d+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ�����������
	 * 
	 * @param value
	 */
	public static boolean isDecmal1(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ǹ�������
	 * 
	 * @param value
	 */
	public static boolean isDecmal2(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ǹ�����
	 * 
	 * @param value
	 */
	public static boolean isDecmal3(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��ǷǸ����������������� + 0��
	 * 
	 * @param value
	 */
	public static boolean isDecmal4(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ƿ������������������� + 0��
	 * 
	 * @param value
	 */
	public static boolean isDecmal5(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ����ʼ�
	 * 
	 * @param value
	 */
	public static boolean isEmail(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern
				.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ�����ɫ
	 * 
	 * @param value
	 */
	public static boolean isColor(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[a-fA-F0-9]{6}$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ���url
	 * 
	 * @param value
	 */
	public static boolean isUrl(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern
				.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ�������
	 * 
	 * @param value
	 */
	public static boolean isChinese(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ���ACSII�ַ�
	 * 
	 * @param value
	 */
	public static boolean isAscii(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[\\x00-\\xFF]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ����ʱ�
	 * 
	 * @param value
	 */
	public static boolean isZipcode(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^\\d{6}$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ����ֻ�
	 * 
	 * @param value
	 */
	public static boolean isMobile(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^(13|15)[0-9]{9}$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ���ip��ַ
	 * 
	 * @param value
	 */
	public static boolean isIp(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern
				.compile("^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ƿǿ�
	 * 
	 * @param value
	 */
	public static boolean isNotempty(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^\\S+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ���ͼƬ
	 * 
	 * @param value
	 */
	public static boolean isPicture(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern
				.compile("(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ���ѹ���ļ�
	 * 
	 * @param value
	 */
	public static boolean isRar(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("(.*)\\.(rar|zip|7zip|tgz)$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ�������
	 * 
	 * @param value
	 */
	public static boolean isDate(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ���QQ����
	 * 
	 * @param value
	 */
	public static boolean isQq(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[1-9]*[1-9][0-9]*$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��ǵ绰����ĺ���(������֤��������,��������,�ֻ���)
	 * 
	 * @param value
	 */
	public static boolean isTel(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern
				.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �����û�ע�ᡣƥ�������֡�26��Ӣ����ĸ�����»�����ɵ��ַ���
	 * 
	 * @param value
	 */
	public static boolean isUsername(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^\\w+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ�����ĸ
	 * 
	 * @param value
	 */
	public static boolean isLetter(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[A-Za-z]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ǵ�д��ĸ
	 * 
	 * @param value
	 */
	public static boolean isLetter_u(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[A-Z]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ǵ�д��ĸ
	 * 
	 * @param value
	 */
	public static boolean isLetter_l(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern.compile("^[a-z]+$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	/**
	 * �Ƿ��Ǽ۸�
	 * 
	 * @param value
	 */
	public static boolean isPrice(String value) {
		Pattern p = null;// ������ʽ
		Matcher m = null;// ���������ʽ
		boolean b = false;
		p = Pattern
				.compile("^([1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|\\.[0-9]{1,2})$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	public static void main(String[] args) {
		boolean b = isPrice("25.67");
		System.out.println(b);
	}
}

