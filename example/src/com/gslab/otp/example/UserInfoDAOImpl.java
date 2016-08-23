/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp.example;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gslab.otp.IUserInfoDAO;
import com.gslab.otp.OTPManager;
import com.gslab.otp.OTPManagerException;
import com.gslab.otp.UserInfo;

/**
 * This is the implementation of {@link IUserInfoDAO}. It will read and write
 * the data from/to XML file. Similarly This class can be implemented to read
 * and write {@link UserInfo} from database.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class UserInfoDAOImpl implements IUserInfoDAO {

	private static final String FILE_PATH = "userdata.xml";

	/**
	 * The logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OTPManager.class);

	@Override
	public UserInfo read(String userId) throws OTPManagerException {

		UserInfo user = null;

		// read all the user details
		UserInfos userInfos = readUserFromXmlFile(FILE_PATH);

		for (UserInfo u : userInfos.getUsers()) {
			if (u.getUserId().equals(userId)) {
				user = u;
				break;
			}
		}

		if (user == null) {
			LOG.error("No data found for userId : {}", userId);
			throw new OTPManagerException("No data found for userId : "
					+ userId);
		}
		return user;
	}

	@Override
	public void write(UserInfo userInfo) throws OTPManagerException {

		// Read the data from the XML file
		UserInfos userInfos = readUserFromXmlFile(FILE_PATH);

		// for first user entry
		if (userInfos == null) {
			userInfos = new UserInfos();
			// generate primary key
			userInfo.setUserId(String.valueOf("1"));
			ArrayList<UserInfo> users = new ArrayList<UserInfo>();
			users.add(userInfo);
			userInfos.setUsers(users);
		} else {
			int listSize = userInfos.getUsers().size();
			if (userInfo.getUserId() == null || userInfo.getUserId().isEmpty()) {
				// Generate primary key, We are taking Integer in sequence order
				// as
				// a primary key

				int key = Integer.valueOf((userInfos.getUsers().get(
						listSize - 1).getUserId())) + 1;
				userInfo.setUserId(String.valueOf(key));
				userInfos.getUsers().add(userInfo);
			} else {
				int counter = 0;
				int indexToSet = 0;
				for (UserInfo u : userInfos.getUsers()) {
					counter++;
					if (u.getUserId().equals(userInfo.getUserId())) {
						indexToSet = counter;
					}
				}
				userInfos.getUsers().set(indexToSet - 1, userInfo);
			}
		}
		writeUserToXmlFile(userInfos);
	}

	/**
	 * Save user details into xml file
	 * 
	 * @param userInfos
	 * @throws OTPManagerException 
	 */
	private void writeUserToXmlFile(UserInfos userInfos) throws OTPManagerException {
		try {
			JAXBUtil.getMarshaller().marshal(userInfos, new File(FILE_PATH));
		} catch (JAXBException e) {
			LOG.error("Error while writing user to xml file, {}",
					e.getMessage(), e);
			throw new OTPManagerException(
					"Error while writing user to xml file, {}" + e.getMessage(),
					e);
		}
	}

	/**
	 * 
	 * @param userInfo 
	 * @return
	 * @throws OTPManagerException 
	 */
	protected String getUserId(UserInfo userInfo) throws OTPManagerException {

		UserInfos userInfos = readUserFromXmlFile(FILE_PATH);

		for (UserInfo u : userInfos.getUsers()) {
			if (u.getAccountName().equals(userInfo.getAccountName())) {
				return u.getUserId();
			}
		}
		return null;

	}

	private UserInfos readUserFromXmlFile(String filePath) throws OTPManagerException {
		UserInfos userInfos = null;
		// Read the userdata file
		File userFile = new File(FILE_PATH);
		if (!userFile.isFile() || !userFile.canRead()) {
			LOG.error("Unable to read file : {}", FILE_PATH);
			return userInfos;
			// throw new OTPManagerException("Unable to read file : " +
			// FILE_PATH);
		}

		try {
			// Unmarshalling the UserInfos object
			userInfos = (UserInfos) JAXBUtil.getUnmarshaller().unmarshal(
					userFile);
		} catch (JAXBException e) {
			LOG.error("Error while mashalling file {}", FILE_PATH, e);
			throw new OTPManagerException("Error while mashalling file "
					+ FILE_PATH, e);
		}
		return userInfos;
	}
}
