package in.vishalit.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils {
	
	//by using class name that i call this method
	public static String generateRandomPwd() {//by using class name that i call this method//this class does not have any states that using static
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String pwd = RandomStringUtils.random(6,characters);
		return pwd;
	}
}
