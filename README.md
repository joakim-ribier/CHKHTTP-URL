# CheckLink - 1.1

Check HTTP url accessibility.

RELEASE 1.1
## Deployment

### 1. Build

Generate the `*.jar` to be installed (on the server for example)
```
./mvn clean install
```
### 2. Run 
Execute directly the `*.jar` just once or with the cron task.
```
java -jar target/checklink-{release}.jar application.conf
```
```
*/10 * * * * java -jar checklink-{release}.jar application.conf
```
## Configuration

The `application.conf` can be defined by argument `args[0]` or just with default resource file `file.path`.

Example:
 * URL_1=http://www.my-domain.com
 * URL_2=http://xxx.my-domain.com
 * URL_X=http://yyy.my-domain.com
 * smtp=host.smtp.com
 * username=from@my-domain.com
 * password=password
 * from=from@my-domain.com
 * to=to@my-domain.com
 * subject=MAIL FROM MY-SERVER CHECK ON URL {0}
 * text=AUTO MESSAGE, FROM DATE {0}, ON HTTP URL {1} WAS NOT ACCESSIBLE.\n\rError HTTP code: {2}
 * smtp_debug=true
