/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.test.otp;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gslab.otp.IUserInfoDAO;
import com.gslab.otp.OTPManager;
import com.gslab.otp.OTPManagerException;
import com.gslab.test.otp.impl.AESEncryptorImpl;
import com.gslab.test.otp.impl.UserInfoDAOImpl;

/**
 * Junit test cases for init API of {@link OTPManager}
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class InitTest {

	private OTPManager manager;
	private File otpFile;
	private IUserInfoDAO iUserInfoDAO;
	private AESEncryptorImpl encryptor;
	private File invalidFilePath;

	@Before
	public void setUp() {
		manager = new OTPManager();
		otpFile = new File("src\\main\\resources",
				"otp.properties");
		invalidFilePath = new File("");
		iUserInfoDAO = new UserInfoDAOImpl();
		encryptor = new AESEncryptorImpl();
	}

	@After
	public void tearDown() {
		manager = null;
		iUserInfoDAO = null;
		encryptor = null;
	}

	@Test
	public void testInit_success_allSet() throws OTPManagerException {
		manager.init(otpFile, iUserInfoDAO, encryptor);
	}

	@Test
	public void testInit_success() throws OTPManagerException {
		manager.init(otpFile, iUserInfoDAO, null);
	}

	@Test(expected = OTPManagerException.class)
	public void testInit_failure_otpFileNull() throws OTPManagerException {
		manager.init(null, iUserInfoDAO, null);
	}

	@Test(expected = OTPManagerException.class)
	public void testInit_failure_daoNull() throws OTPManagerException {
		manager.init(otpFile, null, encryptor);
	}

	@Test(expected = OTPManagerException.class)
	public void testInit_failure_otpFileInvalid() throws OTPManagerException {
		manager.init(invalidFilePath, iUserInfoDAO, null);
	}
}
