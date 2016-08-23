/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.test.otp;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gslab.otp.OTPManager;
import com.gslab.otp.OTPManagerException;
import com.gslab.test.otp.impl.AESEncryptorImpl;
import com.gslab.test.otp.impl.UserInfoDAOImpl;

/**
 * Junit test cases for Verifying OTP .
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 */
public class VerifyOtpTest {

	private OTPManager manager;
	private File otpFile;
	private UserInfoDAOImpl userInfoDAOImpl;
	private AESEncryptorImpl aesEncryptorImpl;

	@Before
	public void setUp() throws OTPManagerException {
		manager = new OTPManager();
		otpFile = new File("src\\main\\resources",
				"otp.properties");
		userInfoDAOImpl = new UserInfoDAOImpl();
		aesEncryptorImpl = new AESEncryptorImpl();
		manager.init(otpFile, userInfoDAOImpl, aesEncryptorImpl);
	}

	@After
	public void tearDown() {
		userInfoDAOImpl = null;
		aesEncryptorImpl = null;
	}

	// Please don't delete the first five entry from userdata.xml file
	@Test
	public void testVerifyOTP_success() throws OTPManagerException {
		boolean isValid = manager.verifyOTP("878357", "3");
		assertEquals(true, isValid);
	}

	// blocked OTP User testing
	@Test(expected = OTPManagerException.class)
	public void testverifyOTP_failure_blockedUser() throws OTPManagerException {
		manager.verifyOTP("765456", "2");
	}

	@Test(expected = OTPManagerException.class)
	public void testverifyOTP_failure_invalidUserId() throws OTPManagerException {
		manager.verifyOTP("765456", "-7");
	}

	@Test(expected = OTPManagerException.class)
	public void testverifyOTP_failure_invalidOTPCode() throws OTPManagerException {
		manager.verifyOTP("78978gh3", "2");
	}

	@Test(expected = OTPManagerException.class)
	public void testverifyOTP_failure_invalidOTPCodeSize() throws OTPManagerException {
		manager.verifyOTP("78", "2");
	}

	@Test(expected = OTPManagerException.class)
	public void testverifyOTP_failure_emptyOTPCOde() throws OTPManagerException {
		manager.verifyOTP("", "2");
	}

	@Test(expected = OTPManagerException.class)
	public void testverifyOTP_failure_nullOTPCOde() throws OTPManagerException {
		manager.verifyOTP(null, "2");
	}

}
