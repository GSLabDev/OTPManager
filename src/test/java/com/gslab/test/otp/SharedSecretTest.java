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

import org.junit.Before;
import org.junit.Test;

import com.gslab.otp.OTPManagerException;
import com.gslab.otp.Utils;

/**
 * Junit test cases for {@link Utils} sharedSecret test.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class SharedSecretTest {

	String otpAuthURL;
	String invalidOtpAuthURL;
	String invalidOtpAuthURL_digitMissing;
	String invalidOtpAuthURL_secretMissing;

	@Before
	public void setUp() throws Exception {

		otpAuthURL = "otpauth://hotp/GSLAB:abdul.waheed@gslab.com?secret=DZLMXUJVNRHQTKYN&issuer=GSLAB&digits=6&counter=10";
		invalidOtpAuthURL = "hdjkshjgdjkfgafkgrjks===";
		invalidOtpAuthURL_digitMissing = "otpauth://hotp/GSLAB:abdul.waheed@gslab.com?secret=DZLMXUJVNRHQTKYN&issuer=GSLAB&=6&counter=10";
		invalidOtpAuthURL_secretMissing = "otpauth://hotp/GSLAB:abdul.waheed@gslab.com?=DZLMXUJVNRHQTKYN&issuer=GSLAB&digits=6&counter=10";

	}

	@Test
	public void testSharedSecret_Success() throws OTPManagerException {
		String sharedSecret = Utils.getSharedSecret(otpAuthURL);
		assertEquals(sharedSecret, "DZLMXUJVNRHQTKYN");
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_invalidURL() throws OTPManagerException {
		Utils.getSharedSecret(invalidOtpAuthURL);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_invalidURL_digistMissing() throws OTPManagerException {
		Utils.getSharedSecret(invalidOtpAuthURL_digitMissing);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_invalidURL_secretMissing() throws OTPManagerException {
		Utils.getSharedSecret(invalidOtpAuthURL_secretMissing);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_nullCheck() throws OTPManagerException {
		Utils.getSharedSecret(null);
	}

	@Test(expected = OTPManagerException.class)
	public void testSharedSecret_Failure_emptyCheck() throws OTPManagerException {
		Utils.getSharedSecret("");
	}

}
