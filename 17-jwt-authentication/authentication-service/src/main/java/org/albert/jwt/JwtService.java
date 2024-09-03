package org.albert.jwt;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

@Singleton
public class JwtService
{
    public String getJwt()
    {
        // WARNING: Remember to generate the public and private keys using the RSAKeyPairGenerator.java
        return Jwt.issuer("albert-productions")
                .subject("book-store")
                .groups("ADMIN")
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();
    }
}
