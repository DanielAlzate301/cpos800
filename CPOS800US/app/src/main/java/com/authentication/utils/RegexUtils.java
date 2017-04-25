package com.authentication.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	public static boolean isCheckPwd(String s) {
		Matcher m = Pattern.compile("^[0-9A-Fa-f]{12}$").matcher(s);
		return m.matches();
	}

	public static boolean isCheckWriteData(String s) {
		Matcher m = Pattern.compile("^[0-9A-Fa-f]{32}$").matcher(s);
		return m.matches();
	}
}
