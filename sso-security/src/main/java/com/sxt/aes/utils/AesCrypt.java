package com.sxt.aes.utils;

import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;

/**
 * AES的加密和解密
 * @author libo
 */
public class AesCrypt {
    // 密钥 ，长度是16
    private static final String KEY = "com.sxt.aes.keys";  
    // 算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    // private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";
  
    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }  
  
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return Base64.encodeBase64String(bytes);  
    }  
  
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] base64Decode(String base64Code) throws Exception{  
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);  
    }  
  
      
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
    	// 密钥生成器
        // KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        // 密钥生成器初始化， 密钥生成器初始化，会影响到Cipher的处理。
        // kgen.init(128);  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        
        /*// AES/CBC/PKCS5Padding 算法模式为CBC可以增加偏移量，可增加加密算法强度。
        String ivParameter = "0392039203920300";
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"), iv);  
        */
        
        // 初始化， Cipher.ENCRYPT_MODE-加密模式， SecretKeySpec-具体的密钥
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));  
  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
  
  
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
  
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
       /* KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  */
  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        // Cipher.DECRYPT_MODE - 解密模式
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes);  
    }  
  
  
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    }   
    
    public static String getKEY(){
    	return KEY;
    }
    
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {  
        String content = "testtest";  
        System.out.println("加密前：" + content);  
        System.out.println("加密密钥和解密密钥：" + KEY);  
        String encrypt = aesEncrypt(content, KEY);  
        System.out.println("加密后：" + encrypt);  
        String decrypt = aesDecrypt(encrypt, KEY);  
        System.out.println("解密后：" + decrypt);  
    } 
}
