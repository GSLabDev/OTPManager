/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.test.otp.impl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.gslab.otp.UserInfo;

/**
 * This class will be required for Marshalling/ Unmarshalling the UserInfo
 * object.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
@XmlRootElement(name = "Users")
public class UserInfos {
	private ArrayList<UserInfo> users;

	/**
	 * @return the users
	 */
	public ArrayList<UserInfo> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(ArrayList<UserInfo> users) {
		this.users = users;
	}

}
