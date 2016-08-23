OTPManager - One time password library
==============================================

Copyright (c) 2016, [Great Software Laboratory Private Limited](http://gslab.com/).

Contributor: Abdul Waheed [abdul.waheed@gslab.com]

[https://github.com/GSLabDev/OTPManager](https://github.com/GSLabDev/OTPManager)

**OTPManager** is a pluggable component that implements Time-based One-time Password [TOTP] (https://en.wikipedia.org/wiki/Time-based_One-time_Password_Algorithm) algorithm specified in RFC 6238 and HMAC based One-time Password [HOTP] (https://en.wikipedia.org/wiki/HMAC-based_One-time_Password_Algorithm) algorithm specified in RFC 4226. Using this component, a web application can support OTP Authentication without worrying much about the OTP implementation. It easily provide additional layer of security i.e. two factor authentication in any web application.

**OTPManager** is licensed under the Open Source License.

**Client Application**

**OTPManager** will act as Authenticator server whereas Google Authenticator application (available for [iOS] (https://itunes.apple.com/in/app/google-authenticator/id388497605), [Android]  (https://play.google.com/store/apps/details?id=com.google.android.apps.authenticator2), [BlackBerry] (https://appworld.blackberry.com/webstore/content/29401059) and [Windows] (https://www.microsoft.com/en-us/store/p/authenticatorg/9nblggh082hc) ) as a client to generates OTP code.

**Library Documentation**

This library includes full JavaDoc documentation and an example code to understand implementation of **OTPManager** library in a better way.

# Usage

## Requirements ( build dependencies )
	* Java Version 6 or above
	* Apache Maven 3 or higher
	
## Instructions

### Build
	
	mvn clean install
	
	It will create the 'OTPManager-1.0.0-archive.zip' file under OTPManager/target directory which will be having 'OTPManager-1.0.0.jar' 
	as well as all others dependencies required for this project. It also contains one properties file named otp.properties.
	
### Usage in code

See [examples](https://github.com/GSLabDev/OTPManager/tree/master/example) for working example.

## Documentation

The API documentation will get generated after mvn install under OTPManager/target/apidocs directory.

For OTPManager library implementation details, see
[IMPLEMENTATION.md](https://github.com/GSLabDev/libasynckafkaclient/blob/master/IMPLEMENTATION.md)


## Examples

See the [examples](https://github.com/GSLabDev/OTPManager/tree/master/example) sub-directory.


## Support

File bug reports, feature requests and questions using
[GitHub Issues](https://github.com/GSLabDev/OTPManager/issues)
	
	