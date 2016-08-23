/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp;

/**
 * This interface provides the functionality of Encrypting/Decrypting the Base32
 * encoded shared Secret which can be either implemented by third-party Web
 * Application or or can simply use inhouse {@link AESEncryptor} to
 * encrypt/decrypt it.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * @version 1.0
 * 
 */
public interface IEncryptor {

	/**
	 * Returns the shared secret in the encrypted format.
	 * 
	 * @param sharedSecret
	 *            shared secret to encrypt
	 * @return encrypted shared secret
	 * @throws OTPManagerException
	 *             if a failure occur while encrypting the shared secret
	 */
	String encrypt(String sharedSecret) throws OTPManagerException;

	/**
	 * Returns the shared secret in decrypted format.
	 * 
	 * @param sharedSecret
	 *            encrypted shared sectet
	 * @return decrypted shared secret
	 * @throws OTPManagerException
	 *             if a failure occur while decrypting the shared secret
	 */
	String decrypt(String sharedSecret) throws OTPManagerException;
}
