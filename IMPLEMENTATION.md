# Implementation of OTPManager - One time password library

### Project Integration

Add below dependency to your build environment

		<!-- Slf4J logger -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.7</version>
		</dependency>
		
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-simple</artifactId>
			<version>1.7.7</version>
		</dependency>
		
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>1.9</version>
		</dependency>

		<dependency>
			<groupId>org.jasypt</groupId>
			<artifactId>jasypt</artifactId>
			<version>1.9.0</version>
		</dependency>
		
Or copy all the libraries from OTPManager-1.0.0-archive\lib into your application classpath.
	
	* Add OTPManager-1.0.0-archive/OTPManager-1.0.0.jar' into your pom.xml file or copy it into your application classpath.
	* Copy OTPManager-1.0.0-archive/config/otp.properties' file into your configuration directory and change 'ISSUER.NAME' value.
	
### Implementation
    * UserInfo: Need to be created in third-party application and make it persistent either in database file or flat file.
	It stores OTP related details details of User. Check UserInfo in OTPManager Javadoc or [example] (https://github.com/GSLabDev/OTPManager/tree/master/example) project.
	
	* IUSerInfoDAO: Must implement and initialize in OTPManager, It required to read/write userInfo object from/to the database. Check example\src\com\gslab\otp\example\UserInfoDAOImpl class for details.
	
	* IEncryptor: OTPManager comes with default AESEncryptor which do encrypt/decrypt the shared secret but for security purpose 
	it must be implemented by third party Web Application. Check example\src\com\gslab\otp\example\AESEncryptor class for details.
	
	* OtpHandler: OTPManager has init(...) which is designed to be called only once. To handle OTPManager in multi threading environment,
	Caller should has to create one handler class which must be double checked singleton and it internally call all OTPManager API’s. Check example\src\com\gslab\otp\example\OtpHandler class for more details.

### Usage

The following code is designed to be called only once in the application lifetime.
	
		OTPManager otpManager = new OTPManager();
		otpManager.init(otpFilePath, UserInfoDAOImpl, EncryptorImpl)
		
		Where optFilePath -> Path of otp.properties file
			  UserInfoDAOImpl -> Implementation of IuserInfoDAO to perform read/write userInfo into the database.
			  EncryptorImpl -> Implementation of IEncryptor which encrypt/decrypt the shared Secret.
			  
The above code must be called inside the getInstance() of a Singleton class e.g. OtpHandler class as explained above.

		private static volatile OtpHandler instance = null;
		public static OtpHandler getInstance() throws OTPManagerException {
			if (instance == null) { // single checked
				synchronized (OtpHandler.class) {
					if (instance == null) { // double checked
						instance = new OtpHandler();
						otpManager = new OTPManager();
						
						// specify the filepath of otp.properties file
						otpFile = new File("config", OTP_FILE);
						
						// For storing userInfo detail,Temporary we are using flat file
						// rather than database. We HAVE TO implement IUserInfoDAO
						// for reading/writing userInfo Entity from database
						userInfoDAO = new UserInfoDAOImpl();
						
						// using bydefault Encryptor for encrypting/decryping shared
						// secret,Ideally we should implement our Encryptor by
						// implementing IEncryptor interface for Encryption
						encryptor = new AESEncryptor();

						// Should be initialize while starting the application and should
						// not be called more than once in whole Application
						otpManager.init(otpFile, userInfoDAO, encryptor);
					}
				}
			}
			return instance;
		}
		
To enable OTP for a user,It has to register to OTPManager to get the qrCodeURL.

		UserInfo userInfo = new UserInfo();
		userInfo.setAccountName(“ACCOUNTNAME”);//accountName of user		
		userInfo.setType(Type.TOTP);// OTP Type
		String qrCodeURL = otpHandler.getInstance().register(userInfo);
		
It returns qrcodeUrl using which caller can generate QRCODE image or you can use OTPManager Utility class to get QRCodeBaseURL which will display Image qrcode directly at runtime.
		
		String qrcodeBaseURL = Utils.getQRCodeAsURL(qrcode, 200, 200);
		
The following code verifies the OTP code against the QRCode Image.

		String boolean = otpHandler.getInstance()..verifyOTP(otpCode, userId)
		
Please refer to the [examples](https://github.com/GSLabDev/OTPManager/tree/master/example) sub-directory for more details.