/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.test.otp;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.gslab.otp.OTPManagerException;
import com.gslab.otp.Utils;

/**
 * Junit test cases for getQRCodeAsURL().
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class GetQRCodeAsURLTest {

	private String otpAuthURL;
	private String otpAuthURLInvalid;

	@Before
	public void setUp() {
		otpAuthURL = "otpauth://hotp/GSLAB:abdul.waheed@gslab.com?secret=DZLMXUJVNRHQTKYN&issuer=GSLAB&digits=6&counter=10";
		otpAuthURLInvalid = "auth://hotp/GSLAB:abdul.waheed@gslab.com?secret=DZLMXUJVNRHQTKYN&issuer=GSLAB";
	}

	@Test
	public void testQrCodeAsImage_Success() throws Exception {
		String qrCodeBaseURL = Utils.getQRCodeAsURL(otpAuthURL, 200, 200);
		assertNotNull(qrCodeBaseURL);
	}

	@Test(expected = OTPManagerException.class)
	public void testQrCodeAsImage_failure_invalidOTPAuthURL() throws Exception {
		Utils.getQRCodeAsURL(otpAuthURLInvalid, 200, 200);
	}

	@Test(expected = OTPManagerException.class)
	public void testQrCodeAsImage_failure_invalidSize() throws Exception {
		Utils.getQRCodeAsURL(otpAuthURL, 200, -1);
	}

	@Test(expected = OTPManagerException.class)
	public void testQrCodeAsImage_failure_nullCheck() throws Exception {
		Utils.getQRCodeAsURL(null, 200, 200);
	}
}
