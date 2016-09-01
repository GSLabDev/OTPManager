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
