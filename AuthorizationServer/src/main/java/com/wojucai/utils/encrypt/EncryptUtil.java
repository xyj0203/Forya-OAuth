package com.wojucai.utils.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

/**
 * @description:加密的接口
 * @author: xuyujie
 * @date: 2023/05/27
 **/
public interface EncryptUtil {

    /**
     * 加密
     * @param message
     * @return
     */
    String encode(String message) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    /**
     * 是否符合预期
     * @param message
     * @param target
     * @return
     */
    boolean decode(String message, String target) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
}
