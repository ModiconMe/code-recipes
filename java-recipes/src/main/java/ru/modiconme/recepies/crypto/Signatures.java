package ru.modiconme.recepies.crypto;

import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.file.Path;
import java.security.*;
import java.util.Base64;

import static ru.modiconme.recepies.crypto.Keys.KeyStoreUtils.loadKeyAndCert;
import static ru.modiconme.recepies.crypto.Keys.KeyStoreUtils.loadPKCS12KeyStore;

public class Signatures {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        String data = "Hello";
        String sign = sign(data);
        boolean verify = verify(data, sign);
        System.out.println(verify);
    }

    @SneakyThrows
    private static String sign(String data) {
        Signature signature = Signature.getInstance("SHA256WithRSA");
        PrivateKey privateKey = getKeyEntry().getPrivateKey();
        signature.initSign(privateKey, new SecureRandom());
        signature.update(data.getBytes());
        byte[] digest = signature.sign();
        return Base64.getEncoder().encodeToString(digest);
    }

    @SneakyThrows
    private static boolean verify(String rowData, String base64EncData) {
        Signature signature = Signature.getInstance("SHA256WithRSA");
        byte[] encData = Base64.getDecoder().decode(base64EncData);
        PublicKey publicKey = getKeyEntry().getCertificate().getPublicKey();
        signature.initVerify(publicKey);
        signature.update(rowData.getBytes());
        return signature.verify(encData);
    }

    private static KeyStore.PrivateKeyEntry getKeyEntry() {
        return loadKeyAndCert(loadPKCS12KeyStore(Path.of(Resources.getResource("certs/server.p12").getPath()), "1234"));
    }
}
