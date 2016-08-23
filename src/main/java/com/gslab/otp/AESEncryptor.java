/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The AESEncryptor class implements {@link IEncryptor} interface. This class
 * provides the mechanism of Encrypting/Decrypting the Base32 encoded shared
 * Secret. {@link OTPManager} uses this class as default for encryption which
 * can be override during initialization.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * @version 1.0
 * 
 */
public class AESEncryptor implements IEncryptor {

	private StandardPBEStringEncryptor encrytor;

	/**
	 * The logger for this class
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(AESEncryptor.class);

	public AESEncryptor() {
		encrytor = new StandardPBEStringEncryptor();
		encrytor.setAlgorithm("PBEWithMD5AndDES");

		// Ideally the password should not be maintained directly in the
		// source code, but rather kept someplace secure
		encrytor.setPassword("ZgPiPSCdq88K8Mfay7T7IA");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gslab.otp.IEncryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String sharedSecret) throws OTPManagerException {
		LOG.info("Encrypting shared Secret...");
		if (sharedSecret == null || sharedSecret.isEmpty()) {
			LOG.error("SharedSecret cannot be null or empty");
			throw new OTPManagerException(
					"SharedSecret cannot be null or empty");
		}
		String encryptedKey = null;
		try {
			encryptedKey = encrytor.encrypt(sharedSecret);
		} catch (Exception e) {
			LOG.error("Error while encrypting sharedSecret {}", e.getMessage(),
					e);
			throw new OTPManagerException(
					"Error while encrypting sharedSecret " + e.getMessage(), e);
		}
		LOG.debug("Encrypted Shared Secret successfully");
		return encryptedKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gslab.otp.IEncryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String sharedSecret) throws OTPManagerException {
		LOG.info("Decrypting shared Secret...");
		String decryptedKey = null;
		if (sharedSecret == null || sharedSecret.isEmpty()) {
			LOG.error("SharedSecret cannot be null or empty");
			throw new OTPManagerException(
					"SharedSecret cannot be null or empty");
		}
		try {
			decryptedKey = encrytor.decrypt(sharedSecret);
		} catch (Exception e) {
			// for Invalid sharedSecret, e.getMessage will be null
			LOG.error("Error while decrypting sharedSecret {}", e.getMessage(),
					e);
			throw new OTPManagerException(
					"Error while decrypting sharedSecret " + e.getMessage(), e);
		}
		LOG.debug("Decrypted Shared Secret successfully");
		return decryptedKey;
	}
}
