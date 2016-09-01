/*
 * Copyright (c) 2016, Great Software Laboratory Private Limited.
 *  All rights reserved.
 *
 * Contributor: Abdul Waheed [abdul.waheed@gslab.com]
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the "Great Software Laboratory Private Limited" nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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