/**
 *  Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp;

/**
 * Describes a OTP user registered with OTP Manager. Third-party web Application
 * MUST create this entity in their application to maintain OTP related details.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * @version 1.0
 * 
 */
public class UserInfo {

	private String userId;
	private String accountName;
	private Type type;
	private String sharedSecret;
	private boolean status;
	private int failureCounter;
	private long hotpCounter;

	/**
	 * Returns the userId of the User.
	 * 
	 * @return the userId of the User
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set the userId of the User.
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Returns the account name of the user
	 * 
	 * @return the accountName account name of the user
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Set the account name of the user
	 * 
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Returns the OTP {@link Type} of a user
	 * 
	 * @return OTP type of user
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Set the OTP {@link Type} of a user
	 * 
	 * @param type
	 *            the {@link Type} to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Returns the base32 encoded binary value
	 * 
	 * @return the sharedSecret of a user
	 */
	public String getSharedSecret() {
		return sharedSecret;
	}

	/**
	 * Set the base32 encoded binary value
	 * 
	 * @param sharedSecret
	 *            the sharedSecret to set
	 */
	public void setSharedSecret(String sharedSecret) {
		this.sharedSecret = sharedSecret;
	}

	/**
	 * Returns the status ACTIVE|INACTIVE status of a user
	 * 
	 * @return <code>true</code>If the user is active, <code>false</code>
	 *         otherwise
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * Status to set i.e. user is ACTIVE|INACTIVE
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Returns the total number of failure attempt of a user.
	 * 
	 * @return the failureCounter of a user
	 */
	public int getFailureCounter() {
		return failureCounter;
	}

	/**
	 * Set the failureCounter of a user
	 * 
	 * @param failureCounter
	 *            the failureCounter to set
	 */
	public void setFailureCounter(int failureCounter) {
		this.failureCounter = failureCounter;
	}

	/**
	 * Returns the current counter value for HOTP user
	 * 
	 * @return the value of counter
	 */
	public long getHotpCounter() {
		return hotpCounter;
	}

	/**
	 * Set the counter for HOTP user
	 * 
	 * @param hotpCounter
	 *            the hotpCounter to set
	 */
	public void setHotpCounter(long hotpCounter) {
		this.hotpCounter = hotpCounter;
	}

	/**
	 * Display a userInfo detail like type, name, status etc.
	 * 
	 */
	@Override
	public String toString() {
		return "UserInfo[UserId=" + userId + ", Account Name=" + accountName
				+ ", Type=" + type + ", HotpCounter=" + hotpCounter
				+ ", Status=" + status + ", Failure Counter=" + failureCounter
				+ "]";
	}

}
