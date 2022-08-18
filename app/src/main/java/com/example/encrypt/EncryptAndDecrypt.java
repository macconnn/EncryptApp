package com.example.encrypt;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptAndDecrypt {

    private byte [] plaintext;
    private byte [] key;
    private String algorithm;
    private String mode;
    private String padding;

    EncryptAndDecrypt(byte [] plaintext  , byte [] key , String algorithm , String mode , String padding){
        this.plaintext = plaintext;
        this.key = key;
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
    }

    public byte [] EncryptOrDecrypt(byte [] initVector , Integer cipherMode) {
        try {
            //encrypt int == 1 decrypt int == 2
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(this.key, this.algorithm);
            Cipher cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding);
            cipher.init(cipherMode, skeySpec, iv);
            return cipher.doFinal(this.plaintext);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public byte [] EncryptOrDecrypt(Integer cipherMode) {
        try {
            //encrypt int == 1 decrypt int == 2
            SecretKeySpec skeySpec = new SecretKeySpec(this.key, this.algorithm);
            Cipher cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding);
            cipher.init(cipherMode, skeySpec);
            return cipher.doFinal(this.plaintext);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
