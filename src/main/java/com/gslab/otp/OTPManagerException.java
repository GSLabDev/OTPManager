/**
 *  Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp;

/**
 * Thrown to indicate that the {@link OTPManager} has failed to perform the
 * requested operation.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * @version 1.0
 * 
 */
public class OTPManagerException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an <B>OTPManagerException</B> with a specified detail message.
	 * 
	 * @param message
	 *            the detail message
	 */
	public OTPManagerException(String message) {
		super(message);

	}

	/**
	 * Constructs an <B>OTPManagerException</B> with a detail message and the specified
	 * Throwable.
	 * 
	 * @param message
	 *            the detail message
	 * @param cause
	 *            the undeclared checked exception that was thrown
	 */
	public OTPManagerException(String message, Throwable cause) {
		super(message, cause);
	}

}
