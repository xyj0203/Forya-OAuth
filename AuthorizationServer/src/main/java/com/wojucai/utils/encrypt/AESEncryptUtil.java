package com.wojucai.utils.encrypt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @description:AES算法加密实现类
 * @author: xuyujie
 * @date: 2023/05/27
 **/
@Component
public class AESEncryptUtil implements EncryptUtil{

    @Value("${encode.secret}")
    private String secret;

    private Cipher cipher;

    public AESEncryptUtil() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES");
    }

    @Override
    public String encode(String message) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] bytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return new String(bytes);
    }

    @Override
    public boolean decode(String message, String target) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return encode(message).equals(target);
    }
}
