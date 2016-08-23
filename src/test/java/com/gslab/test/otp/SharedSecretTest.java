/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.test.otp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.gslab.otp.OTPManagerException;
import com.gslab.otp.Utils;

/**
 * Junit test cases for {@link Utils} sharedSecret test.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class SharedSecretTest {

	String otpAuthURL;
	String invalidOtpAuthURL;
	String invalidOtpAuthURL_digitMissing;
	String invalidOtpAuthURL_secretMissing;

	@Before
	public void setUp() throws Exception {

		otpAuthURL = "otpauth://hotp/GSLAB:abdul.waheed@gslab.com?secret=DZLMXUJVNRHQTKYN&issuer=GSLAB&digits=6&counter=10";
		invalidOtpAuthURL = "hdjkshjgdjkfgafkgrjks===";
		invalidOtpAuthURL_digitMissing = "otpauth://hotp/GSLAB:abdul.waheed@gslab.com?secret=DZLMXUJVNRHQTKYN&issuer=GSLAB&=6&counter=10";
		invalidOtpAuthURL_secretMissing = "otpauth://hotp/GSLAB:abdul.waheed@gslab.com?=DZLMXUJVNRHQTKYN&issuer=GSLAB&digits=6&counter=10";

	}

	@Test
	public void testSharedSecret_Success() throws OTPManagerException {
		String sharedSecret = Utils.getSharedSecret(otpAuthURL);
		assertEquals(sharedSecret, "DZLMXUJVNRHQTKYN");
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_invalidURL() throws OTPManagerException {
		Utils.getSharedSecret(invalidOtpAuthURL);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_invalidURL_digistMissing() throws OTPManagerException {
		Utils.getSharedSecret(invalidOtpAuthURL_digitMissing);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_invalidURL_secretMissing() throws OTPManagerException {
		Utils.getSharedSecret(invalidOtpAuthURL_secretMissing);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_nullCheck() throws OTPManagerException {
		Utils.getSharedSecret(null);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_emptyCheck() throws OTPManagerException {
		Utils.getSharedSecret("");
	}

}
