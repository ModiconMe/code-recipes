Проверка работы сервера:
```sh
curl --cacert rootCA.crt https://localhost:8080/check --cert client.crt:1234 --key client.key
```
- --cert client.crt:1234 --key client.key - клиентский сертификат:пароль ключ, для авторизации клиентов,
должны быть подписаны rootCA.jks (нужно только для клиентской авторизации)
- --cacert rootCA.crt сертификат, который подписал серверный сертификат 


Для проверки клиентского сертификата используется jks файл, потому что p12 почему-то выдает ошибку.

Рутовый
```shell
openssl genpkey -verbose \
-algorithm RSA \
-cipher -aes-256-cbc \
-pass pass:1234 \
-pkeyopt rsa_keygen_bits:4096 \
-outform PEM \
-out rootCA.key \
&&
openssl req -verbose -x509 -new -noenc -sha256 \
-key rootCA.key \
-passin pass:1234 \
-days 1024 \
-subj "/C=RU/ST=Altaisky Krai/O=CA_Smartix/CN=ROOT CA/OU=IT/L=Barnaul" \
-outform PEM \
-out rootCA.crt
```

Сервер
```shell
openssl genpkey -verbose \
-algorithm RSA \
-cipher -aes-256-cbc \
-pass pass:1234 \
-pkeyopt rsa_keygen_bits:4096 \
-outform PEM \
-out domain.key \
&&
openssl req -verbose -new -noenc -sha256 \
-key domain.key \
-passin pass:1234 \
-subj "/CN=127.0.0.1/CN=localhost/CN=localhost.example.com" \
-outform PEM \
-out domain.csr \
&&
echo "authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
subjectAltName = @alt_names
[alt_names]
IP.1 = 127.0.0.1
DNS.1 = localhost
DNS.2 = localhost.example.com" > domain.cnf \
&&
openssl x509 -verbose -req -noenc -sha256 -CAcreateserial \
-in domain.csr \
-CA rootCA.crt \
-CAkey rootCA.key \
-passin pass:1234 \
-days 500 \
-outform PEM \
-out domain.crt \
-extfile domain.cnf
```

Клиентский
```shell
openssl genpkey -verbose \
-algorithm RSA \
-cipher -aes-256-cbc \
-pass pass:1234 \
-pkeyopt rsa_keygen_bits:4096 \
-outform PEM \
-out client.key \
&&
openssl req -verbose -new -noenc -sha256 \
-key client.key \
-passin pass:1234 \
-subj "/CN=127.0.0.1/CN=localhost/CN=localhost.example.com" \
-outform PEM \
-out client.csr \
&&
openssl x509 -verbose -req -noenc -sha256 -CAcreateserial \
-in client.csr \
-CA rootCA.crt \
-CAkey rootCA.key \
-passin pass:1234 \
-days 500 \
-outform PEM \
-out client.crt
```

Создание jks trust хранилища для авторизации клиентских сертов
```shell
 keytool -import -v -trustcacerts \                                                           
-keystore rootCA.jks \
-storepass 123456 \
-file rootCA.crt \
-alias rootCA
```