### Создание keystore, экспорт/импорт ключей

##### Создание Keystore и генерация сертификата:
`keytool -genkey -alias client -keyalg RSA -keystore client.jks -keysize 2048`

##### Создание Keystore и генерация private+public:
`keytool -genkeypair -keyalg RSA -keysize 2048 -keystore keystore.jks -alias server -validity 3650`

##### Создание Keystore и импорт сертификата: 
```bash
keytool -importcert -file cert.pem -keystore keystore1.jks -alias "test-cert"
```

----

### Экспорт
##### Экспорт сертификата из хранилища в binary формате:
```bash
keytool -export -alias client -file client.crt -keystore client.jks
```

##### Экспорт сертификата из хранилища в pem формате:
```bash
keytool -export -alias client -file client.crt -rfc -keystore client.jks
```

##### Экспорт Java keystore в .p12 файл:
```shell
keytool -importkeystore \
-srckeystore keystore.jks \
-srcstoretype JKS \
-srcstorepass changeit \
-destkeystore keystore.p12 \
-deststoretype PKCS12 \
-deststorepass changeit
```
----
### Импорт

##### Импорт CA сертификата в хранилище:
`keytool -import -v -trustcacerts -alias client -file client.crt -keystore clienttrust.jks`


----

### Команды для проверки сертификатов

##### Просмотр содержимого сертификата:
`keytool -printcert -v -file mydomain.crt`

##### Просмотр содержимого сертификата в PEM формате:
`keytool -v -printcert -file server.crt -rfc`

##### Просмотр всех сертификатов в хранилище:
`keytool -list -v -keystore keystore.jks`

##### Просмотр сертификата в хранилище по alias:
`keytool -list -v -keystore keystore.jks -alias mydomain`

----

### Другое

##### Удалить сертификат из хранилища:
`keytool -delete -alias mydomain -keystore keystore.jks`

##### Сменить пароль:
`keytool -storepasswd -new new_storepass -keystore keystore.jks`

##### Посмотреть список trusted CA сертификатов:
`keytool -list -v -keystore $JAVA_HOME/jre/lib/security/cacerts`

##### Импорт нового CA сертификата:
`keytool -import -trustcacerts -file /path/to/ca/ca.pem -alias CA_ALIAS -keystore $JAVA_HOME/jre/lib/security/cacerts`



----
The stores can be changed using:
```
System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
System.setProperty("javax.net.ssl.trustStore", "cacerts.jks");
System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
```

or:

```
-Djavax.net.ssl.keyStore=path/to/keystore.jks
-Djavax.net.ssl.trustStore=cacerts.jks"
-Djavax.net.ssl.keyStorePassword=changeit
```