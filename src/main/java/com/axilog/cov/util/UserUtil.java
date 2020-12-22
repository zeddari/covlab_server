package com.axilog.cov.util;

public class UserUtil {

	private static final String AUTHORITY_PREFIX = "ROLE_";
	
	/**
	 * @param auth
	 * @return
	 */
	public static String getRegionFromAuth(String auth) {
		if (auth == null) return "";
		return auth.replace(AUTHORITY_PREFIX, "");
	}
}
