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
package com.gslab.otp;

import java.io.File;

import org.apache.commons.codec.binary.Base32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the functionality described in RFC 6238 (TOTP: Time
 * based one-time password algorithm), RFC 4226 (HOTP : HMAC-Based One-Time
 * Password Algorithm) and has been tested again Google's implementation of such
 * algorithm in its Google Authenticator application.
 * 
 * This class lets users create a new 16-bit base32-encoded secret key as
 * described in RFC 3548. It generates the QR barcode to let an user load the
 * generated information into Google Authenticator and also verify user OTP
 * code.
 * 
 * {@link OTPManager} class doesn't store in any way either the generated shared
 * secret nor the OTP code during the authorization process.
 * 
 * @author abdul.waheed@gslab.com
 * @version 1.0
 * 
 */
public class OTPManager {

	private IUserInfoDAO userInfoDAO;
	private IEncryptor encryptor;
	private Utils utils;

	/**
	 * The logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OTPManager.class);

	/**
	 * Initialize the path of the "otp.properties" file and the set reference
	 * for the {@link IUserInfoDAO}, {@link IEncryptor} interface. This init()
	 * function should be called during Application start up.
	 * 
	 * @param PropertiesFile
	 *            path of the "otp.properties file"
	 * @param userInfoDAO
	 *            reference of {@link IUserInfoDAO}
	 * @param encryptor
	 *            reference of {@link IEncryptor}
	 * @throws OTPManagerException
	 *             if a failure occur while initializing {@link OTPManager}
	 */
	public void init(File PropertiesFile, IUserInfoDAO userInfoDAO,
			IEncryptor encryptor) throws OTPManagerException {
		LOG.info("Initializing properties file and references...");
		if (PropertiesFile == null || userInfoDAO == null) {
			LOG.error("OTP file path or IUserInfoDao are not set. Please initialize it");
			throw new OTPManagerException(
					"OTP file path or IUserInfoDao are not set. Please initialize it");
		}
		this.userInfoDAO = userInfoDAO;
		utils = new Utils(PropertiesFile);
		if (encryptor == null) {
			LOG.info("No Reference found for IEncryptor, Setting  AESEncryptor by Default. ");
			this.encryptor = new AESEncryptor();
		} else {
			this.encryptor = encryptor;
		}
	}

	/**
	 * Returns the OTPAuthURL which is required to generate the QRCODE image.
	 * 
	 * @param userInfo
	 *            user details of the user like OTP type(HOTP/TOTP), Account
	 *            name etc
	 * @return OTPAuthURL for QRCode
	 * @throws OTPManagerException
	 *             if a failure occur while registering user
	 */
	public String register(UserInfo userInfo) throws OTPManagerException {
		LOG.info("Registering user...");
		String sharedSecret = null;
		String otpAuthURL = null;

		if (userInfo == null) {
			LOG.error("UserInfo cannot be null");
			throw new OTPManagerException("UserInfo cannot be null");
		}
		// Make sure account name and type is not empty or null
		if (userInfo.getAccountName() == null
				|| userInfo.getAccountName().isEmpty()) {
			LOG.error("Account name cannot be null or empty");
			throw new OTPManagerException(
					"Account name cannot be null or empty");
		}

		if (userInfo.getType() == null) {
			LOG.error("OTP type cannot be null or empty");
			throw new OTPManagerException("OTP type cannot be null or empty");
		}

		try {
			sharedSecret = utils.generateSharedSecret();
			otpAuthURL = utils.generateOTPAuthURL(sharedSecret, userInfo);

			// Encrypt the shared Secret before saving to the database
			userInfo.setSharedSecret(encryptor.encrypt(sharedSecret));
			userInfo.setStatus(true);
			userInfo.setFailureCounter(0);

			// HotpCounter is mandatory only for HOTP
			if (userInfo.getType() == Type.HOTP)
				userInfo.setHotpCounter(utils.getHotpCounter());

			// Primary key should be handled while implementing IUserInfoDao
			// Interface
			userInfoDAO.write(userInfo);
		} catch (Exception e) {
			LOG.error("Erroring while doing OTP registration {}",
					e.getMessage(), e);
			throw new OTPManagerException(
					"Erroring while doing OTP registration {}" + e.getMessage(),
					e);
		}
		return otpAuthURL;
	}

	/**
	 * Verify that whether the provided OTP code is valid or not against the
	 * userId
	 * 
	 * @param otpCode
	 *            the OTP code to validate
	 * @param userId
	 *            id of the {@link UserInfo}
	 * @return <code>true</code> if the validation code is valid,
	 *         <code>false</code> otherwise.
	 * @throws OTPManagerException
	 *             if a failure occur while verifying OTP
	 */
	public boolean verifyOTP(String otpCode, String userId)
			throws OTPManagerException {
		LOG.info("Verifying OTP for user : " + userId);
		if (otpCode == null || otpCode.isEmpty() || userId == null
				|| userId.isEmpty()) {
			LOG.error("OTP Code or UserId cannot be null or empty");
			throw new OTPManagerException(
					"OTP Code or UserId cannot be null or empty");
		}

		if (otpCode.length() != Constants.DIGITS) {
			LOG.error("The given otp " + otpCode
					+ " has wrong length. Expected " + Constants.DIGITS
					+ " digits");
			throw new OTPManagerException("The given otp " + otpCode
					+ " has wrong length. Expected " + Constants.DIGITS
					+ " digits");
		}

		long code;
		try {
			code = Long.valueOf(otpCode);
		} catch (NumberFormatException nfe) {
			LOG.error("Invalid OTP code: {}", otpCode, nfe);
			throw new OTPManagerException("Invalid OTP code: " + otpCode, nfe);
		}

		if (code <= 0) {
			LOG.error("OTP code cannot be zero or negative");
			throw new OTPManagerException("OTP code cannot be zero or negative");
		}

		// Read the user details
		UserInfo userInfo = null;
		try {
			userInfo = userInfoDAO.read(userId);
			// for Invalid userId
			if (userInfo == null) {
				LOG.error(
						"Invalid userId, {} is not registered for OTP Authentication",
						userId);
				throw new OTPManagerException("Invalid userId, " + userId
						+ " is not registered for OTP Authentication");
			}
		} catch (Exception e) {
			LOG.error("Error while reading userDetails from the database {}",
					e.getMessage(), e);
			throw new OTPManagerException(
					"Error while reading userDetails from the database "
							+ e.getMessage(), e);
		}

		// Ensure that the user is ACTIVE
		if (userInfo.getStatus() == false) {
			LOG.error(
					"UserId '{}' is blocked, Please contact System Administrator",
					userId);
			throw new OTPManagerException("UserId '" + userId
					+ "' is blocked, Please contact System Administrator");
		}

		// Decrypt the encrypted sharedSecret
		String sharedSecret = null;
		try {
			sharedSecret = encryptor.decrypt(userInfo.getSharedSecret());
		} catch (Exception e) {
			LOG.error("Error while decrypting the sharedSecret {} ",
					e.getMessage(), e);
			throw new OTPManagerException(
					"Error while decrypting the sharedSecret " + e.getMessage(),
					e);
		}

		boolean otpResult = false;
		long generateOTPCode;
		int variance;
		long hotpCounter = 0;

		try {
			// Decoding the secret key to get its raw byte representation.
			byte[] secretInBytes = new Base32().decode(sharedSecret);

			if (userInfo.getType() == Type.HOTP) {
				variance = utils.getHotpWindowSize();
				hotpCounter = userInfo.getHotpCounter();
				for (int i = 0; i <= variance; i++) {
					generateOTPCode = utils.getHotpCode(secretInBytes,
							hotpCounter + i);

					// if generared == OTP code to be verified.
					if (generateOTPCode == code) {
						otpResult = true;
						userInfo.setHotpCounter(hotpCounter + i);
						break;
					}
				}
			} else {
				variance = utils.getTotpWindowSize();

				// -ve to check both side of time interval
				for (int i = -variance; i < variance; i++) {
					// Calculating the verification code for the current time
					// interval.
					generateOTPCode = utils.getTotpCode(secretInBytes,
							getTimeIndex() + i);
					// if generared == OTP code to be verified.
					if (generateOTPCode == code) {
						otpResult = true;
						break;
					}
				}
			}
			if (otpResult == false) {
				userInfo = checkThrottlingCounter(userInfo);
			} else {
				userInfo.setFailureCounter(0);
			}
			userInfoDAO.write(userInfo);
		} catch (Exception e) {
			LOG.error("Error while verifying OTP {}", e.getMessage(), e);
			throw new OTPManagerException("Error while verifying OTP "
					+ e.getMessage(), e);
		}
		return otpResult;
	}

	/**
	 * The number of seconds a key is valid for TOTP.
	 * 
	 * @return time in Seconds.
	 */
	private long getTimeIndex() {
		return System.currentTimeMillis() / 1000 / Constants.TOTP_TIME_PERIODS;
	}

	/**
	 * Checks whether the user has reached throttling Counter or not.
	 * 
	 * @param userInfo
	 *            OTP details of the {@link UserInfo}
	 * @return updated userDetails
	 * @throws OTPManagerException
	 *             if a failure occur while checking Throttling counter
	 */
	private UserInfo checkThrottlingCounter(UserInfo userInfo)
			throws OTPManagerException {
		int failureCounter = userInfo.getFailureCounter();

		if (failureCounter < 0) {
			LOG.error("Failure counter cannot be negative");
			throw new OTPManagerException("Failure counter cannot be negative");
		}

		int totalFailureCounter = failureCounter + 1;

		// If failure Counter reached MAX_OTP_FAILURE, block the user
		if (totalFailureCounter >= utils.getMaxOTPFailure()) {
			userInfo.setStatus(false);
		}
		userInfo.setFailureCounter(totalFailureCounter);
		return userInfo;
	}
}
