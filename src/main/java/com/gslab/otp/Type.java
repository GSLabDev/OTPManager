/**
 *  Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp;

/**
 * Represents the OTP type i.e. The type of key used for generating shared
 * secret. Valid types are HOTP (counter-based) and TOTP (time based).
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * @version 1.0
 * 
 */
public enum Type {
	/**
	 * HOTP : HMAC-Based One-Time Password Algorithm described in RFC 4226
	 * 
	 * @see <a
	 *      href="https://tools.ietf.org/html/rfc4226">HMAC-Based One-Time Password Algorithm</a>
	 */
	HOTP,

	/**
	 * TOTP: Time based one-time password algorithm described in RFC 6238
	 * 
	 * @see <a
	 *      href="https://tools.ietf.org/html/rfc6238">Time based one-time password algorithm</a>
	 * 
	 */
	TOTP
}
