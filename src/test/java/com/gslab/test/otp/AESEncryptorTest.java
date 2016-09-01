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
