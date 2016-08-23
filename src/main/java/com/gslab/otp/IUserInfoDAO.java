/**
 * Copyright 2014 GSLAB. All Rights Reserved.
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
