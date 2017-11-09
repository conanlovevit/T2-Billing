package com.ztv.oauth2.manager;

import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityManager {
    private static int byte2Unsigned(byte b) {
      return b & 0xFF;
    }    
    private static byte ns2Byte(String str) {
        return (byte)(Integer.parseInt(str));
    }
    private static String byte2Ns(byte n) {
        String ret = "";
        int n1 = byte2Unsigned(n);
        return (n1 < 10 ? "00" : (n1 < 100 ? "0" : "")) + String.valueOf(n1);
    }
    
    public static String byte2NumberString(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 3);
        for (int i = 0; i < buf.length; i++) {
            strbuf.append(byte2Ns(buf[i]));
        }
        return strbuf.toString();
    }
    
    public static byte[] numberString2Byte(String str) {
        byte[] ret = new byte[str.length() / 3];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ns2Byte(str.substring(i * 3, (i + 1) * 3));
        }
        
        return ret;
    }
    
    public static String byte2Hex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)
                strbuf.append("0");
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();

    }
    
    public static byte[] hex2Byte(String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(s2, 16) & 0xff);
        }
        return b;

    }
    
    public static byte[] md5_byte(String arg) throws Exception {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        md.update(arg.getBytes());
        return md.digest();
    }

    public static String md5_string(String arg) throws Exception {
        return byte2Hex(md5_byte(arg));
    }
    
    public static String xor_string(String s1, String s2) throws Exception {
        if (s1.length() != s2.length()) return null;
        byte[] b1 = hex2Byte(s1);
        byte[] b2 = hex2Byte(s2);
        byte[] a = new byte[b1.length];
        for (int i = 0; i < b1.length; i++) {
            a[i] = (byte)(b1[i] ^ b2[i]);
        }
        return byte2Hex(a);
    }
    
    public static String AES_encryptByHexKey(String key, String cleartext) throws Exception {
        byte[] rawKey = hex2Byte(key);
        byte[] result = AES_encrypt(rawKey, cleartext.getBytes());
        return byte2Hex(result);
    }

    public static String AES_decryptByHexKey(String key, String encrypted) throws Exception {
        byte[] rawKey = hex2Byte(key);
        byte[] enc = hex2Byte(encrypted);
        byte[] result = AES_decrypt(rawKey, enc);
        return new String(result);
    }

    public static String AES_encryptByNumberStringKey(String key, String cleartext) throws Exception {
        byte[] rawKey = numberString2Byte(key);
        byte[] result = AES_encrypt(rawKey, cleartext.getBytes());
        return byte2Hex(result);
    }

    public static String AES_decryptByNumberStringKey(String key, String encrypted) throws Exception {
        byte[] rawKey = numberString2Byte(key);
        byte[] enc = hex2Byte(encrypted);
        byte[] result = AES_decrypt(rawKey, enc);
        return new String(result);
    }

    public static String AES_getHexKey(String seed)throws Exception {
        return byte2Hex(AES_getRawKey(seed));
    }
    
    public static byte[] AES_getRawKey(String seed)throws Exception {
        return AES_getRawKey(seed.getBytes());
    }

    private static byte[] AES_getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }


    public static byte[] AES_encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    public static byte[] AES_decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    
    public static String RSA_encode(BigInteger public_mod, BigInteger public_exp, String data) throws Exception {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(public_mod, public_exp);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey pubKey = fact.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] cipherData = cipher.doFinal(data.getBytes());
        return SecurityManager.byte2Hex(cipherData);
    }
    
    public static String RSA_decode(BigInteger private_mod, BigInteger private_exp, String data) throws Exception {
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(private_mod, private_exp);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = fact.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherData = cipher.doFinal(SecurityManager.hex2Byte(data));
        return (new String(cipherData)).trim();
    }
    
    public static String sha1_String(String data) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(data.getBytes("UTF-8"));
        return SecurityManager.byte2Hex(crypt.digest());
    }
}
