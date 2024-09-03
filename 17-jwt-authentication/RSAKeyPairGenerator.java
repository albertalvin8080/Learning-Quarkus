import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKeyPairGenerator
{
    public static void main(String[] args) throws Exception
    {
        File directory = new File("jwt");
        if (!directory.exists()) directory.mkdir();

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        savePrivateKey(privateKey, "jwt/privateKey.pem");

        PublicKey publicKey = keyPair.getPublic();
        savePublicKey(publicKey, "jwt/publicKey.pem");
    }

    // Saves the private key in PKCS#8 format
    private static void savePrivateKey(PrivateKey privateKey, String filePath) throws IOException
    {
        PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        writeToFile(filePath, pkcs8Spec.getEncoded(), "PRIVATE KEY");
    }

    // Saves the public key in X.509 format
    private static void savePublicKey(PublicKey publicKey, String filePath) throws IOException
    {
        X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(publicKey.getEncoded());
        writeToFile(filePath, x509Spec.getEncoded(), "PUBLIC KEY");
    }

    private static void writeToFile(String path, byte[] keyBytes, String keyType) throws IOException
    {
        String keyBase64 = Base64.getEncoder().encodeToString(keyBytes);
        String keyPem = String.format("-----BEGIN %s-----\n%s\n-----END %s-----\n", keyType, keyBase64, keyType);
        Files.write(Paths.get(path), keyPem.getBytes());
    }
}
