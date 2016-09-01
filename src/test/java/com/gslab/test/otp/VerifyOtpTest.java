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
