package com.yyh.amailsite.common.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class HashPasswordGenerator {
    public static String encryptionPassword(String username, String password) {
        String salt = "我是盐打我啊";
        return DigestUtils.md5DigestAsHex((username + salt + password).getBytes(StandardCharsets.UTF_8));
    }
}
