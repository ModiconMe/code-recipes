package edu.modiconme.crypto;

import lombok.experimental.UtilityClass;

import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

@UtilityClass
public class EncodingUtils {

    public String base64EncodeBytes(final byte[] bytes) {
        return getEncoder().encodeToString(bytes);
    }

    public String base64EncodeString(final String input) {
        return getEncoder().encodeToString(input.getBytes());
    }

    public byte[] base64DecodeBytes(final byte[] bytes) {
        return getDecoder().decode(bytes);
    }

    public byte[] based64DecodeString(final String input) {
        return getDecoder().decode(input.getBytes());
    }

    public String urlEncode(final String input) {
        return encode(input, UTF_8);
    }

    public String urlDecodeString(final String input) {
        return decode(input, UTF_8);
    }
}
