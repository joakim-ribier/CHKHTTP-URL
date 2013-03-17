#CHKHTTP-URL - 1.0

CHKHTTP-URL check the accessibility of http url.

1. Generating jar with maven `mvn clean install`

2. Running jar : 

* `java -jar target/check-http-app-1.0.jar myconf.file`

or to use crontab

* `* */1 * * * java -jar check-http-app-1.0.jar myconf.file`

####CONFIGURATION

The configuration file path default is defined in file.path OR It can be defined by argument ( args[0] )

Example :

 * check_url=http://www.my-domain.com
 * smtp=host.smtp.com
 * username=from@my-domain.com
 * password=password
 * from=from@my-domain.com
 * to=to@my-domain.com
 * subject=It's the subject
 * text=Content plain text
 * smtp_debug=true

