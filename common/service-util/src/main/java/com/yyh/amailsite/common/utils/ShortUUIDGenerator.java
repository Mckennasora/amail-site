package com.yyh.amailsite.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ShortUUIDGenerator {
    public static String generateShortUUID() {
        // 生成随机UUID
        UUID uuid = UUID.randomUUID();

        // 使用SHA-256哈希算法计算UUID的哈希值
        String hashedUUID = hashUUID(uuid.toString());

        // 取哈希值的前8位作为短UUID

        return hashedUUID.substring(0, 8);
    }

    private static String hashUUID(String uuid) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(uuid.getBytes());

            // 转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String shortUUID = generateShortUUID();
        System.out.println("Short UUID: " + shortUUID);
    }
}