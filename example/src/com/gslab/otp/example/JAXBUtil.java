/**
 * Copyright 2014 GSLAB. All Rights Reserved.
 */
package com.gslab.otp.example;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * This is utility class used to do all JAXB serialization to/from disk. Please
 * use this whenever you need to read/write all configuration classes from/to
 * disk. It also requires that all JAXB serializeable classes are included into
 * jaxb.index.
 * 
 * @author abdul.waheed@gslab.com (Abdul Waheed)
 * 
 */
public class JAXBUtil {

	private static JAXBContext context;

	private static Marshaller marshaller;
	private static Unmarshaller unmarshaller;

	private static JAXBContext getContext() throws JAXBException {
		if (context == null) {
			context = JAXBContext.newInstance(JAXBUtil.class.getPackage()
					.getName());
		}
		return context;

	}

	public static Marshaller getMarshaller() throws JAXBException {
		if (marshaller == null) {
			marshaller = getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}
		return marshaller;
	}

	public static Unmarshaller getUnmarshaller() throws JAXBException {
		if (unmarshaller == null) {
			unmarshaller = getContext().createUnmarshaller();
		}
		return unmarshaller;

	}

}
