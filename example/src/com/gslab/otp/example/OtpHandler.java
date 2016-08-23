package com.gslab.otp.example;

import java.io.File;

import com.gslab.otp.OTPManager;
import com.gslab.otp.OTPManagerException;
import com.gslab.otp.UserInfo;

/**
 * 
 */

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
