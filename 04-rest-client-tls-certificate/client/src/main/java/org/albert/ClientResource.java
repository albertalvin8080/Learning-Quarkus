package org.albert;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Path("/client")
public class ClientResource
{
    // DO NOT use @Inject here, it simply doesn't work that way.
    @ConfigProperties
    MyProperties myProperties;

    @RestClient
    ServerProxy serverProxy;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello()
    {
//        return "Hello from client";
        return serverProxy.callServer();
    }

    // javax.crypto.BadPaddingException: Given final block not properly padded. Such issues can arise if a bad key is used during decryption.
    @GET
    @Path("/clientBuilder")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloClientBuilder() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException
    {
        final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        final InputStream keyStoreStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(myProperties.getKeyStore());
        keyStore.load(keyStoreStream, myProperties.getKeyStorePassword().toCharArray());

        final KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        final InputStream trustStoreStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(myProperties.getTrustStore());
        trustStore.load(trustStoreStream, myProperties.getTrustStorePassword().toCharArray());

        final ServerProxy proxy = RestClientBuilder.newBuilder()
                .baseUri(myProperties.getUri())
                .keyStore(keyStore, myProperties.getKeyStorePassword())
                .trustStore(trustStore)
                .build(ServerProxy.class);

        return proxy.callServer();
    }
}
