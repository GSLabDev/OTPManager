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
