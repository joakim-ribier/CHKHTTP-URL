#CHKHTTP-URL - 1.1

CHKHTTP-URL check the accessibility of http url.

1. Generating jar with maven `mvn clean install`

2. Running jar : 

* `java -jar target/check-http-app-1.1.jar myconf.file`

or to use crontab

* `0 * * * * java -jar check-http-app-1.1.jar myconf.file`

####CONFIGURATION

The configuration file path default is defined in file.path OR It can be defined by argument ( args[0] )

Example :

 * URL_1=http://www.my-domain.com
 * URL_2=http://xxx.my-domain.com
 * URL_X=http://yyy.my-domain.com
 * smtp=host.smtp.com
 * username=from@my-domain.com
 * password=password
 * from=from@my-domain.com
 * to=to@my-domain.com
 * subject=Subject of : {0}
 * text=At {0}, the http URL {1} was not accessible. Error code: {2}
 * smtp_debug=true
