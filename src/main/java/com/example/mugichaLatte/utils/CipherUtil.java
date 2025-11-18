package com.example.mugichaLatte.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class CipherUtil {

    public static String encrypt(String password) {
        //暗号化処理は例外が発生する可能性があるため
        try {
            //MessageDigest → Java標準の暗号化API
            //SHA-256 → 256bitの一方向ハッシュ（改ざん不可）
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //渡されたバイト配列に対して、SHA-256計算し、ハッシュ値を返す
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            //DBに保存するためにBase64に変更している
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}