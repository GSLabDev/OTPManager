/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp;

/**
 * Constants class stores all the value which are required for OTP code
 * generation.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * @version 1.0
 * 
 */
public class Constants {

	/**
	 * Defines a period that a TOTP code will be valid for, in seconds. The
	 * default value is 30.
	 */
	public static final int TOTP_TIME_PERIODS = 30;

	/**
	 * Determines how long of a one-time passcode to display to the user. The
	 * default is 6.
	 */
	public static final int DIGITS = 6;

	/**
	 * name of the properties file
	 */
	public static final String OTP_FILE = "otp.properties";

	/**
	 * Base URL to download the QRCode using Google API's
	 */
	public static final String QRCODE_BASE_URL = "http://chart.apis.google.com/chart?cht=qr&chs=";

	/**
	 * Length of the shared Secret
	 */
	public static final int SHARED_SECRET_LENGTH = 16;

	/**
	 * The SecureRandom algorithm to use.
	 * 
	 * @see java.security.SecureRandom#getInstance(String)
	 */
	public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

	/**
	 * Minimum validation window size of TOTP or HOTP.
	 */
	public static final int MIN_WINDOW = 1;

	/**
	 * Maximum validation window size of TOTP or HOTP.
	 */
	public static final int MAX_WINDOW = 10;

	/**
	 * Length in bytes of each scratch code.
	 */
	public static final int BYTES_PER_SCRATCH_CODE = 4;

	/**
	 * The size of the seed which is fed to the SecureRandom instance, in bytes.
	 */
	public static final int SEED_SIZE = 128;

	/**
	 * The number of bits of a secret key in binary form. Since the Base32
	 * encoding with 8 bit characters introduces an 160% overhead, we just need
	 * 80 bits (10 bytes) to generate a 16 bytes Base32-encoded secret key.
	 */
	public static final int SECRET_BITS = 80;

	/**
	 * Number of scratch codes to generate during the key generation. We are
	 * using Google's default of providing 5 scratch codes.
	 */
	public static final int SCRATCH_CODES = 5;

	/**
	 * Cryptographic hash function used to calculate the HMAC (Hash-based
	 * Message Authentication Code). This implementation uses the SHA1 hash
	 * function.
	 */
	public static final String HMAC_HASH_FUNCTION = "HmacSHA1";

}
