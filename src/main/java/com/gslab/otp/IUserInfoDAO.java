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

/**
 * This interface provides the functionality of Reading and Writing a
 * {@link UserInfo} object from/to the database. It should be implemented by the
 * third-party Web Applications.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public interface IUserInfoDAO {

	/**
	 * Write the {@link UserInfo} into the database, All the logic related to
	 * writing the UserInfo into the database should be implemented by
	 * third-party Web Application. It should also handle primary key and 
	 * 
	 * @param userInfo
	 *            OTP details of the user
	 * @throws OTPManagerException
	 *             if a failure occur while writing {@link UserInfo} into the
	 *             database
	 */
	public void write(UserInfo userInfo) throws OTPManagerException;

	/**
	 * Read the {@link UserInfo} from the database. All the logic related to
	 * retrieving the {@link UserInfo} from the database should be implemented
	 * by third-party Web Application.
	 * 
	 * @param userId
	 *            Id of the user
	 * @return UserInfo OTPDetails of the user
	 * @throws OTPManagerException
	 *             If a failure occur while reading {@link UserInfo} from the
	 *             database.
	 */
	public UserInfo read(String userId) throws OTPManagerException;

}
