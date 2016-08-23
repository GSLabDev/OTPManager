/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.test.otp;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gslab.otp.AESEncryptor;
import com.gslab.otp.OTPManagerException;

/**
 * Junit test cases for Inhouse {@link AESEncryptor}
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class AESEncryptorTest {

	private AESEncryptor aesEncryptor;

	@Before
	public void setUp() {
		aesEncryptor = new AESEncryptor();
	}

	@After
	public void tearDown() {
		aesEncryptor = null;
	}

	@Test
	public void testEncrytpion_success() throws OTPManagerException {
		String encryptedSharedSecret = aesEncryptor.encrypt("2QUM4WZM3JHMR2QI");
		assertNotNull(encryptedSharedSecret);
	}

	@Test(expected = OTPManagerException.class)
	public void testEncrytpion_failure_nullSharedSecret()
			throws OTPManagerException {
		aesEncryptor.encrypt(null);
	}

	@Test(expected = OTPManagerException.class)
	public void testEncrytpion_failure_emptySharedSecret()
			throws OTPManagerException {
		aesEncryptor.encrypt("");
	}

	@Test
	public void testDecrytpion_success() throws OTPManagerException {
		String decryptedSharedSecret = aesEncryptor
				.decrypt("VbKQe75YSUgajZOoa3W/lyo0aTDn4xo9NKjFAN+Z5/w=");
		assertNotNull(decryptedSharedSecret);
	}

	@Test(expected = OTPManagerException.class)
	public void testDecrytpion_failure_invalidSecret()
			throws OTPManagerException {
		String decryptedSharedSecret = aesEncryptor.decrypt("2QUM4WZM3JHMR2QI");
		assertNotNull(decryptedSharedSecret);
	}

	@Test(expected = OTPManagerException.class)
	public void testDecryption_failure_nullSharedSecret()
			throws OTPManagerException {
		aesEncryptor.decrypt(null);
	}

	@Test(expected = OTPManagerException.class)
	public void testDecryption_failure_emptySharedSecret()
			throws OTPManagerException {
		aesEncryptor.decrypt("");
	}

}
