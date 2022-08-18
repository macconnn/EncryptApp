package com.example.encrypt;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDes {

    ConvertData convertData;
    private String keyStr;
    private String Mode;
    private String Padding;
    private String TdesWay;
    private byte [] plainTextByte;
    private byte [] ivByte;
    final Integer ENCRYPT_MODE = new Integer(Cipher.ENCRYPT_MODE);
    final Integer DECRYPT_MODE = new Integer(Cipher.DECRYPT_MODE);


    TripleDes(byte [] plainTextByte , byte [] ivByte , String Mode , String Padding , String keyStr , String TdesWay){
        this.plainTextByte = plainTextByte;
        this.ivByte = ivByte;
        this.Mode = Mode;
        this.Padding = Padding;
        this.keyStr = keyStr;
        this.TdesWay = TdesWay;
    }

    private byte [] getThirdKey(){
        if(this.TdesWay.equals("Triple")){
            String keyStrThree = this.keyStr.substring(32, 48);
            byte[] keyByteThree = convertData.stringHexToByte(keyStrThree);
            return keyByteThree;
        }else{
            String keyStrThree = this.keyStr.substring(0, 16);
            byte[] keyByteThree = convertData.stringHexToByte(keyStrThree);
            return keyByteThree;
        }
    }
    
    //TDES mode = CBC
    public byte [] decryptTdes(byte [] iv , String keyStr){
        String keyStrOne = keyStr.substring(0, 16);
        String keyStrTwo = keyStr.substring(16, 32);
        byte[] keyByteOne = convertData.stringHexToByte(keyStrOne);
        byte[] keyByteTwo = convertData.stringHexToByte(keyStrTwo);
        byte [] keyByteThree = getThirdKey();
        //TDES解密順序 解密-->加密-->解密
        byte[] encryptOne = TDESEncryptAndDecrypt(this.plainTextByte, iv, keyByteThree, this.Mode, this.Padding , DECRYPT_MODE);
        byte[] decryptTwo = TDESEncryptAndDecrypt(encryptOne, iv, keyByteTwo, this.Mode, this.Padding , ENCRYPT_MODE);
        byte[] encryptThree = TDESEncryptAndDecrypt(decryptTwo, iv, keyByteOne, this.Mode, this.Padding , DECRYPT_MODE);
        return encryptThree;
        }

    public byte [] decryptTdes(String keyStr){
        String keyStrOne = keyStr.substring(0, 16);
        String keyStrTwo = keyStr.substring(16, 32);
        byte[] keyByteOne = convertData.stringHexToByte(keyStrOne);
        byte[] keyByteTwo = convertData.stringHexToByte(keyStrTwo);
        byte [] keyByteThree = getThirdKey();
        //TDES解密順序 解密-->加密-->解密
        byte[] encryptOne = TDESEncryptAndDecrypt(this.plainTextByte, keyByteThree, this.Mode, this.Padding , DECRYPT_MODE);
        byte[] decryptTwo = TDESEncryptAndDecrypt(encryptOne, keyByteTwo, this.Mode, this.Padding , ENCRYPT_MODE);
        byte[] encryptThree = TDESEncryptAndDecrypt(decryptTwo, keyByteOne, this.Mode, this.Padding , DECRYPT_MODE);
        return encryptThree;
    }

    public byte [] encryptTdes(byte [] iv , String keyStr){
        String keyStrOne = keyStr.substring(0, 16);
        String keyStrTwo = keyStr.substring(16, 32);
        byte[] keyByteOne = convertData.stringHexToByte(keyStrOne);
        byte[] keyByteTwo = convertData.stringHexToByte(keyStrTwo);
        byte [] keyByteThree = getThirdKey();
        //TDES加密順序 加密-->解密-->加密
        byte[] encryptOne = TDESEncryptAndDecrypt(this.plainTextByte, iv, keyByteOne, this.Mode, this.Padding , ENCRYPT_MODE);
        byte[] decryptTwo = TDESEncryptAndDecrypt(encryptOne, iv, keyByteTwo, this.Mode, this.Padding , DECRYPT_MODE);
        byte[] encryptThree = TDESEncryptAndDecrypt(decryptTwo, iv, keyByteThree, this.Mode, this.Padding , ENCRYPT_MODE);
        return encryptThree;
    }

    public byte [] encryptTdes(String keyStr){
        String keyStrOne = keyStr.substring(0, 16);
        String keyStrTwo = keyStr.substring(16, 32);
        byte[] keyByteOne = convertData.stringHexToByte(keyStrOne);
        byte[] keyByteTwo = convertData.stringHexToByte(keyStrTwo);
        byte [] keyByteThree = getThirdKey();
        //TDES加密順序 加密-->解密-->加密
        byte[] encryptOne = TDESEncryptAndDecrypt(this.plainTextByte, keyByteOne, this.Mode, this.Padding , ENCRYPT_MODE);
        byte[] decryptTwo = TDESEncryptAndDecrypt(encryptOne, keyByteTwo, this.Mode, this.Padding , DECRYPT_MODE);
        byte[] encryptThree = TDESEncryptAndDecrypt(decryptTwo, keyByteThree, this.Mode, this.Padding , ENCRYPT_MODE);
        return encryptThree;
    }

    //Algorithm = TDES mode = ECB
    public byte [] TDESEncryptAndDecrypt(byte [] plaintext  , byte [] key  , String mode , String padding , Integer cipherMode) {
            try {
                SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
                Cipher cipher = Cipher.getInstance("DES/" + mode + "/" + padding);
                cipher.init(cipherMode, skeySpec);
                return cipher.doFinal(plaintext);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    //Algorithm = TDES mode = CBC
    public byte [] TDESEncryptAndDecrypt(byte [] plaintext , byte [] initVector , byte [] key , String mode , String padding ,  Integer cipherMode) {
            try {
                IvParameterSpec iv = new IvParameterSpec(initVector);
                SecretKeySpec skeySpec = new SecretKeySpec(key, "DES");
                Cipher cipher = Cipher.getInstance("DES/" + mode + "/" + padding);
                cipher.init(cipherMode, skeySpec , iv);
                return cipher.doFinal(plaintext);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

}
