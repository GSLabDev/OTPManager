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
package com.gslab.otp.example;

import java.io.File;

import com.gslab.otp.OTPManager;
import com.gslab.otp.OTPManagerException;
import com.gslab.otp.UserInfo;

/**
 * @author abdul
 *
 */
public class OtpHandler {

	private static OTPManager otpManager;
	private static File otpFile;
	private static UserInfoDAOImpl userInfoDAO;
	private static AESEncryptor encryptor;
	private static final String OTP_FILE = "otp.properties";

	private static volatile OtpHandler instance = null;

	// preventing OtpHandler object instantiation from outside
	private OtpHandler() {
	};

	public static OtpHandler getInstance() throws OTPManagerException {
		if (instance == null) { // single checked
			synchronized (OtpHandler.class) {
				if (instance == null) { // double checked
					instance = new OtpHandler();

					otpManager = new OTPManager();

					// specify the filepath of otp.properties file
					otpFile = new File("config", OTP_FILE);

					// For storing userInfo detail,Temporary we are using flat
					// file
					// rather than database. We HAVE TO implement IUserInfoDAO
					// for
					// reading/writing userInfo Entity from database
					userInfoDAO = new UserInfoDAOImpl();

					// using bydefault Encryptor for encrypting/decryping shared
					// secret,Ideally we should implement our Encryptor by
					// implementing
					// IEncryptor interface for Encryption
					encryptor = new AESEncryptor();

					// Should be initialize while starting the application and
					// should
					// not be called more than once in whole Application
					otpManager.init(otpFile, userInfoDAO, encryptor);
				}
			}

		}
		return instance;
	}

	/**
	 * Returns the OTPAuthURL which is required to generate the QRCODE image.
	 * 
	 * @param info
	 *            user details of the user like OTP type(HOTP/TOTP), Account
	 *            name etc.
	 * @return OTPAuthURL for QRCode
	 * @throws OTPManagerException
	 *             if a failure occur during OTP registration
	 */
	protected String register(UserInfo info) throws OTPManagerException {
		return otpManager.register(info);
	}

	/**
	 * Returns the userId of the user.
	 * 
	 * @param userInfo
	 *            useInfo of the user
	 * @return userId of the user
	 * @throws OTPManagerException 
	 */
	protected String getUserId(UserInfo userInfo) throws OTPManagerException {
		return userInfoDAO.getUserId(userInfo);
	}

	/**
	 * Returns true if the OTP code is valid otherwise false
	 * 
	 * @param otpCode
	 *            optcode to be verified
	 * @param userId
	 *            id of the user
	 * @return <code>true</code> if the code is valid <code>false</code>
	 *         otherwise
	 * @throws OTPManagerException
	 *             If a failure occur during verifyig OTP
	 */
	public boolean verifyOtp(String otpCode, String userId)
			throws OTPManagerException {
		return otpManager.verifyOTP(otpCode, userId);
	}
}
