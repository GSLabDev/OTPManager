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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gslab.otp.Type;
import com.gslab.otp.UserInfo;
import com.gslab.otp.Utils;

/**
 * This class demonstrate the use of OTPManager package. Rather than database It
 * will use XML file to retrieve and store userInfo details.
 * 
 * @author abdul.waheed@gslab.com
 *
 */
public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		OtpHandler handler = OtpHandler.getInstance();
		Scanner scan = new Scanner(System.in);
		String otpCode = null;
		String accountName = null;
		String userId = null;

		UserInfo info = new UserInfo();

		LOG.info("Enter accountName or Username, e.g(abdul.waheed@gslab.com)");
		accountName = scan.next();

		// get the account name from the user
		info.setAccountName(accountName);

		// set OTP TYPE. by default We are using TOTP, but we can set to HOTP as
		// well.It should be decided by user
		info.setType(Type.TOTP);

		// return the qrcodeAuth URL which can be used to generate QRCODE using
		// some third party jars
		String qrcode = handler.register(info);
		LOG.info("qrcode : " + qrcode);

		// return qrcode code in from of URL which can easily be downloaded form
		// net.
		String qrcodeBaseurl = Utils.getQRCodeAsURL(qrcode, 200, 200);
		try {
			saveImage(qrcodeBaseurl, "qrcode.png");
		} catch (IOException e) {
			LOG.error("Error while downloading Image : {} ", e.getMessage(), e);
			throw e;
		}
		LOG.info("Open root folder and scan 'qrcode.png' from your mobile device to get OTP code.");
		LOG.info("Enter OTP code : ");

		// During registration, you should also ask for OTP code verification
		// which make sure user has scanned the qrcode properly
		otpCode = scan.next();
		userId = handler.getUserId(info);
		if (handler.verifyOtp(otpCode, userId)) {
			LOG.info("OTP code is valid");
		} else {
			LOG.info("OTP code is Invalid");
		}
		scan.close();

	}

	/*
	 * It download the qrcode Image from the net and save it as 'qrcode.png' at
	 * root folder.
	 */
	private static void saveImage(String imageUrl, String destinationFile)
			throws IOException {
		LOG.info("Downloading qrcode image...");
		URL url = new URL(imageUrl);
		BufferedImage image = ImageIO.read(url);
		ImageIO.write(image, "png", new File(destinationFile));
	}
}
