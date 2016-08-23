/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.test.otp;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gslab.otp.OTPManager;
import com.gslab.otp.OTPManagerException;
import com.gslab.otp.Type;
import com.gslab.otp.UserInfo;
import com.gslab.test.otp.impl.UserInfoDAOImpl;

/**
 * Junit test cases for register API of {@link OTPManager}.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class OTPRegisterTest {

	private UserInfo userInfo;
	private OTPManager manager;
	private File otpFile;
	private UserInfoDAOImpl userInfoDAO;

	@Before
	public void setUp() throws Exception {
		manager = new OTPManager();
		userInfoDAO = new UserInfoDAOImpl();

		otpFile = new File("src\\main\\resources",
				"otp.properties");
		manager.init(otpFile, userInfoDAO, null);
		userInfo = new UserInfo();
		userInfo.setType(Type.TOTP);

	}

	@After
	public void tearDown() throws Exception {
		userInfo = null;
		userInfoDAO = null;
		manager = null;
	}

	@Test
	public void testOTPRegister_Success_TOTP() throws OTPManagerException {
		userInfo.setAccountName("abdul.waheed@gslab.com");
		String QRCodeURL = manager.register(userInfo);
		assertNotNull(QRCodeURL);
	}

	@Test
	public void testOTPRegister_Success_HOTP() throws OTPManagerException {
		UserInfo userInfo = new UserInfo();
		userInfo.setAccountName("waheed.abdul@gslab.com");
		userInfo.setType(Type.HOTP);
		String QRCodeURL = manager.register(userInfo);
		assertNotNull(QRCodeURL);
	}

	@Test(expected = OTPManagerException.class)
	public void testOTPRegister_Failure_EmptyAccountName() throws OTPManagerException {
		userInfo.setAccountName("");
		manager.register(userInfo);
	}

	@Test(expected = OTPManagerException.class)
	public void testOTPRegister_Failure_Null_AccountName() throws OTPManagerException {
		userInfo.setAccountName(null);
		manager.register(userInfo);
	}

	@Test(expected = OTPManagerException.class)
	public void testOTPRegister_Failure_Null_Type() throws OTPManagerException {
		UserInfo userInfo = new UserInfo();
		userInfo.setAccountName("waheed.abdul@gslab.com");
		manager.register(userInfo);
	}
	
	@Test(expected = OTPManagerException.class)
	public void testOTPRegister_Failure_Null_UserInfo() throws OTPManagerException {
		manager.register(null);
	}
	
}