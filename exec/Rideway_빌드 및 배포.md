### ๐  ํ๋ก์ ํธ ๊ธฐ์  ์คํ

- ์ฑ, ์น ๊ฐ๋ฐ ๋ชจ๋ ์งํ
  - ์ฃผ์ ๊ธฐ์  ์คํ: `Kotlin`, `Spring`, `React`

![image-20230216213142080](assets/image-20230216213142080.png)

---

### ๐ก ๋ฐฐํฌ ์ฌํญ

- ์  ํจ์ค๋ฅผ ํ์ฉํ ์๋ ๋ฐฐํฌ ์งํ
- ๋ฒ์  ์ ๋ณด

```
// ์๋๋ก์ด๋ //
- Mobile Dynamic Map(v3)

// DB //
Mysql
- 8.0.31.0

// ๋ฐฑ์๋ //
Intellij
- 2022.3.1
-java 11(openjdk 11)
-spring boot gradle 2.7.7
-spring data jpa
-lombok
-apache maven 3.8.7 โ openvidu์ฉ
๋ก์ปฌ ์๋ฒ โ apache tomcat 9.0.71

// ํ๋ฐํธ์๋ //
react: 18.2.0
redux: 4.2.0
node: v16.18.0
```

- ๋ก์ปฌ ํ๊ฒฝ์์ Spring boot ์คํ์ ์ค์  ๋ฐฉ๋ฒ

![image-20230216225535281](assets/image-20230216225535281.png)

![image-20230216225540327](assets/image-20230216225540327.png)

- Application.properties ์ค์ 

```spring
_schema \uBD80\uBD84 -> db \uC2A4\uD0A4\uB9C8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://3.36.76.227:3306/rideway?useUniCode=yes&characterEncoding=UTF-8&serverTimezone=Asia/Seoul

#spring.datasource.url=jdbc:mysql://172.26.6.80:3306/rideway?useUniCode=yes&characterEncoding=UTF-8&serverTimezone=Asia/Seoul


# mysql \uACC4\uC815 id & \uBE44\uBC00\uBC88\uD638 \uC785\uB825
spring.datasource.username=B6
spring.datasource.password=!wlgk6cmd!



#spring.datasource.username=B6
#spring.datasource.password=!wlgk6cmd!

# JPA Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=false
spring.jpa.show-sql=true
spring.jpa.database=mysql
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.show_sql=false
logging.level.org.hibernate.SQL=debug
logging.level.org.hibernate.type=trace

# hibernate loggin
#loggin.level.org.hibernate=info

# email \uC804\uC1A1 \uAD00\uB828
spring.mail.host=smtp.naver.com
spring.mail.port=465
spring.mail.username=ridingmaster01@naver.com
spring.mail.password=1q2w3e4r!
spring.mail.properties.debug=true
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable= true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.ssl.trust=smtp.naver.com


spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# รฌยยนรฌยยรฌยผย รชยฐยรซยฐยรฌยยฉ
spring.devtools.livereload.enabled=true
spring.devtools.restart.enabled=false

# log4j
logging.config=classpath:log4j2.xml


#Multipart file
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=200MB
spring.servlet.multipart.max-request-size=215MB


# https \uC124\uC815

```



- `Nginx`  ์ค์ 
  - `Letsencrypt`๋ก `https`์ ์ฉ
  - `443`ํฌํธ๋ก ๋ฆฌ๋ค์ด๋ ํธ ์ค์ 
  - `location`์์ `/api`๋ฅผ ํตํด ๋ฐฑ์๋์ ํ๋ฐํธ ์ฌ์ฉ ์์ญ์ ๊ตฌ๋ถ
  - `stomp`์ฐ๊ฒฐ์ ์ํด `nginx` ์์ `upgrade` ์ค์  ์งํ

```nginx
server {
        if ($host = i8e102.p.ssafy.io) {
                return 301 https://$host$request_uri;
        }
                listen 80;
                listen [::]:80;
        server_name i8e102.p.ssafy.io;
        return 404;
}

server {
        listen 443 ssl;
        listen [::]:443 ssl;
        server_name  i8e102.p.ssafy.io;

    ssl_certificate /etc/letsencrypt/live/i8e102.p.ssafy.io/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/i8e102.p.ssafy.io/privkey.pem; # managed by Certbot

        access_log      /home/ubuntu/nginx/avilaccess.log;
        error_log       /home/ubuntu/nginx/availerror.log;

        client_max_body_size 100M;

   root /home/ubuntu/jenkins/build/;
        index index.html index.htm;
        server_name i8e102.p.ssafy.io;

        location / {
                try_files $uri $uri/ /index.html;
        }

        location /api/ws-stomp {
                proxy_pass http://localhost:8080;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "Upgrade";
                proxy_set_header Host $http_host;
        }


        location /api {
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
                proxy_pass http://localhost:8080;

        }

```

- `ufw`๋ฅผ ํตํ ๋ฐฉํ๋ฒฝ ์ค์ 
  - `openvidu`์ค์ ์ผ๋ก ์ธํด ๋ง์ ํฌํธ ๊ฐ๋ฐฉ
  - `jenkins` : 9090, `Mysql`: 3306, `Https`: 443, `Http`: 80, `Front`: 8080, `ubuntu`: 22

```linux
Status: active

To                         Action      From
--                         ------      ----
22                         ALLOW       Anywhere
80                         ALLOW       Anywhere
443                        ALLOW       Anywhere
Nginx HTTP                 ALLOW       Anywhere
8080                       ALLOW       Anywhere
9090                       ALLOW       Anywhere
OpenSSH                    ALLOW       Anywhere
3306                       ALLOW       Anywhere
4443                       ALLOW       Anywhere
5000                       ALLOW       Anywhere
8443                       ALLOW       Anywhere
3478                       ALLOW       Anywhere
5442                       ALLOW       Anywhere
5443                       ALLOW       Anywhere
6379                       ALLOW       Anywhere
8888                       ALLOW       Anywhere
3478/udp                   ALLOW       Anywhere
40000:57000/tcp            ALLOW       Anywhere
40000:57000/udp            ALLOW       Anywhere
57001:65535/tcp            ALLOW       Anywhere
57001:65535/udp            ALLOW       Anywhere
3478/tcp                   ALLOW       Anywhere
8443/tcp                   ALLOW       Anywhere
8443/udp                   ALLOW       Anywhere
5443/tcp                   ALLOW       Anywhere
5443/udp                   ALLOW       Anywhere
8442/tcp                   ALLOW       Anywhere
8442/udp                   ALLOW       Anywhere
22 (v6)                    ALLOW       Anywhere (v6)
80 (v6)                    ALLOW       Anywhere (v6)
443 (v6)                   ALLOW       Anywhere (v6)
Nginx HTTP (v6)            ALLOW       Anywhere (v6)
8080 (v6)                  ALLOW       Anywhere (v6)
9090 (v6)                  ALLOW       Anywhere (v6)
OpenSSH (v6)               ALLOW       Anywhere (v6)
3306 (v6)                  ALLOW       Anywhere (v6)
4443 (v6)                  ALLOW       Anywhere (v6)
5000 (v6)                  ALLOW       Anywhere (v6)
8443 (v6)                  ALLOW       Anywhere (v6)
3478 (v6)                  ALLOW       Anywhere (v6)
5442 (v6)                  ALLOW       Anywhere (v6)
5443 (v6)                  ALLOW       Anywhere (v6)
6379 (v6)                  ALLOW       Anywhere (v6)
8888 (v6)                  ALLOW       Anywhere (v6)
3478/udp (v6)              ALLOW       Anywhere (v6)
40000:57000/tcp (v6)       ALLOW       Anywhere (v6)
40000:57000/udp (v6)       ALLOW       Anywhere (v6)
57001:65535/tcp (v6)       ALLOW       Anywhere (v6)
57001:65535/udp (v6)       ALLOW       Anywhere (v6)
3478/tcp (v6)              ALLOW       Anywhere (v6)
8443/tcp (v6)              ALLOW       Anywhere (v6)
8443/udp (v6)              ALLOW       Anywhere (v6)
5443/tcp (v6)              ALLOW       Anywhere (v6)
5443/udp (v6)              ALLOW       Anywhere (v6)
8442/tcp (v6)              ALLOW       Anywhere (v6)
8442/udp (v6)              ALLOW       Anywhere (v6)
```

- ํ์ฌ ๊ฐ๋์ค์ธ ํ๋ก์ธ์ค
  - `openvidu`, `jenkins`, `http`, `spring boot`, `mysql`์ด ๊ณ์ํด์ ๊ฐ์ ๋๊ธฐํ๊ฑฐ๋ ์คํ ์ค์ด๋ค.

```
Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
tcp        0      0 localhost:33060         0.0.0.0:*               LISTEN      899/mysqld
tcp        0      0 0.0.0.0:mysql           0.0.0.0:*               LISTEN      899/mysqld
tcp        0      0 0.0.0.0:http            0.0.0.0:*               LISTEN      1663595/nginx: mast
tcp        0      0 localhost:domain        0.0.0.0:*               LISTEN      561/systemd-resolve
tcp        0      0 0.0.0.0:3478            0.0.0.0:*               LISTEN      1589387/docker-prox
tcp        0      0 0.0.0.0:ssh             0.0.0.0:*               LISTEN      830/sshd: /usr/sbin
tcp        0      0 localhost:6010          0.0.0.0:*               LISTEN      1931454/sshd: ubunt
tcp        0      0 0.0.0.0:8442            0.0.0.0:*               LISTEN      1589801/nginx: mast
tcp        0      0 0.0.0.0:https           0.0.0.0:*               LISTEN      1663595/nginx: mast
tcp        0      0 0.0.0.0:8443            0.0.0.0:*               LISTEN      1589801/nginx: mast
tcp6       0      0 [::]:5442               [::]:*                  LISTEN      1589475/node
tcp6       0      0 [::]:9090               [::]:*                  LISTEN      722385/java
tcp6       0      0 [::]:5443               [::]:*                  LISTEN      1589583/java
tcp6       0      0 [::]:http-alt           [::]:*                  LISTEN      1930945/java
tcp6       0      0 [::]:http               [::]:*                  LISTEN      1663595/nginx: mast
tcp6       0      0 [::]:3478               [::]:*                  LISTEN      1589394/docker-prox
tcp6       0      0 [::]:ssh                [::]:*                  LISTEN      830/sshd: /usr/sbin
tcp6       0      0 [::]:8888               [::]:*                  LISTEN      1589462/kurento-med
tcp6       0      0 ip6-localhost:6010      [::]:*                  LISTEN      1931454/sshd: ubunt
tcp6       0      0 [::]:8442               [::]:*                  LISTEN      1589801/nginx: mast
tcp6       0      0 [::]:https              [::]:*                  LISTEN      1663595/nginx: mast
tcp6       0      0 [::]:8443               [::]:*                  LISTEN      1589801/nginx: mast
udp        0      0 localhost:domain        0.0.0.0:*                           561/systemd-resolve
udp        0      0 ip-172-26-6-80.a:bootpc 0.0.0.0:*                           559/systemd-network
udp        0      0 0.0.0.0:3478            0.0.0.0:*                           1589448/docker-prox
udp6       0      0 [::]:3478               [::]:*                              1589460/docker-prox

```

- Jenkins ์ค์ 
  - `GitLab`๊ณผ ์ฐ๋์์ผ, `push`์ `merge` ๋ฐ์ ์ ์๋ ๋น๋๋ฅผ ํ๊ฒ ๋๋ค.

![image-20230216223807992](assets/image-20230216223807992.png)

![image-20230216223844521](assets/image-20230216223844521.png)

- ์  ํจ์ค ํ๋ฌ๊ทธ์ธ ์ค์น
  - Publish Over SSH
    - ์๋ฒ์ ๋น๋ ํ์ผ์ ์ฎ๊ฒจ ์ฃผ๋ ํ๋ฌ๊ทธ์ธ
  - Post build task
    - ๋น๋ ํ ์กฐ์น๋ฅผ ์ธํํ๊ธฐ ์ํ ํ๋ฌ๊ทธ์ธ
  - Generic Webhook Trigger Plugin
    - Gitlab Webhook์ ํ์ฉํ๊ธฐ ์ํ plugin
  - GitLab Plugin

- ๋ฐฐํฌ ํ๊ธฐ
- ๋น๋ ์ค์ 
  - `env`ํ์ผ๋ก `kakao map api key`๋ฅผ ๋ณต์ฌํด์ `front`์ ๋ณต์ฌํด์ค๋ค.

- ๋น๋๋ ํ์ผ์ ์ง์ฐ๊ณ  frontend ๋น๋๋ฅผ ์งํํ๋ค.
- backend๋ ๋ง์ฐฌ๊ฐ์ง๋ก ๊ธฐ์กด์ ๋น๋๋ ํ์ผ๋ค์ ์ง์ฐ๊ณ  ๋น๋๋ฅผ ์งํํ๋ค.
  - ์ฌ๊ธฐ์๋ gradle์ ์ฌ์ฉํ์๋ค.

![image-20230216224352922](assets/image-20230216224352922.png)

```jenkins
sudo rm -rf /home/ubuntu/jenkins/build/*
cp -R /var/lib/jenkins/workspace/.env /var/lib/jenkins/workspace/rideway/frontend
cd /var/lib/jenkins/workspace/rideway/frontend

npm install --legacy-peer-deps
CI=false
npm run build

sudo rm -rf /home/ubuntu/jenkins/demo-1-0.0.1-SNAPSHOT.jar
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH="$PATH:$JAVA_HOME/bin"
cd /var/lib/jenkins/workspace/rideway/backend/demo-1
chmod +x ./gradlew
./gradlew clean bootJar
```

- ๋น๋ ํ ์กฐ์น
  - ๋ฐฑ์๋์ ๊ฒฝ์ฐ ๊ธฐ์กด์ nohup์ผ๋ก ์งํ๋๋ ํ๋ก์ธ์ค๋ค์ ๋ชจ๋ ์ ๋ฆฌํ๊ณ  ์๋กญ๊ฒ ๋น๋๋ ํ์ผ์ ์คํ์์ผ์ผ ํ๋ค.

![image-20230216224641389](assets/image-20230216224641389.png)

```
kill -9 `cat save_pid.txt`
rm save_pid.txt
nohup java -jar /home/ubuntu/jenkins/demo-1-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
echo $! > save_pid.txt
```

- ๋จ์ํ ๋น๋๋ ํ์ผ ์ด๋

![image-20230216224739602](assets/image-20230216224739602.png)



- ์ฑ์ ๋ฐฐํฌํ  ์๊ฐ์ด ๋ถ์กฑํด์ ๋ก์ปฌ์์ ์คํ

![1](assets/1.PNG)

![1-1](assets/1-1.png)

![1-2](assets/1-2.png)



![2](assets/2.png)

![3](assets/3.png)

![4](assets/4.png)

![5](assets/5.png)

![6](assets/6.png)

![7](assets/7.png)

![8](assets/8.png)

![9](assets/9.png)

![10](C:\Users\SSAFY_SangChan\Desktop\ํฌํ\assets\10.png)

