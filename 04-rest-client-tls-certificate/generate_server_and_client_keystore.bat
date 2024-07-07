@REM NOTE: keytool is provided by the OpenJDK, it's not a Linux tool.

@REM A keystore is a repository where private keys, certificates,
@REM and symmetric keys can be stored. It serves as a secure storage
@REM for cryptographic material.

@REM SAN = Subject Alternative Name
keytool -genkeypair ^
        -storepass server_password ^
        -keyalg RSA ^
        -keysize 2048 ^
        -dname "CN=server" ^
        -alias server ^
        -ext "SAN:c=DNS:localhost,IP:127.0.0.1" ^
        -keystore server.keystore

copy ".\server.keystore" ".\client\src\main\resources\META-INF\resources\server.truststore"
move ".\server.keystore" ".\server\src\main\resources\META-INF\resources\server.keystore"

keytool -genkeypair ^
        -storepass client_password ^
        -keyalg RSA ^
        -keysize 2048 ^
        -dname "CN=client" ^
        -alias server ^
        -ext "SAN:c=DNS:localhost,IP:127.0.0.1" ^
        -keystore client.keystore

copy ".\client.keystore" ".\server\src\main\resources\META-INF\resources\client.truststore"
move ".\client.keystore" ".\client\src\main\resources\META-INF\resources\client.keystore"