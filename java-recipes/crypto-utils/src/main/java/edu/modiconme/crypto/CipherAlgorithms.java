package edu.modiconme.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CipherAlgorithms {

    RSA("RSA"),
    AES_CBC_PKCS5PADDING("AES/CBC/PKCS5PADDING"),
    AES_GCM_NO_PADDING("AES/GCM/NoPadding"),
    RSA_ECB_OAEP_WITH_SHA_256_AND_MGF_1_PADDING("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
    ;

    private final String algorithm;
}
