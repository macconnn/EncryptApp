package com.example.encrypt;

import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import javax.crypto.Cipher;

public class EncryptOrDecryptConditions {


    private byte [] ivByte;
    private byte [] plainTextByte;
    private byte [] keyByte;
    private String Algorithm;
    private String Mode;
    private String Padding;
    private String plaintextStr;
    private String ivStr;
    private String keyStr;
    final Integer ENCRYPT_MODE = new Integer(Cipher.ENCRYPT_MODE);
    final Integer DECRYPT_MODE = new Integer(Cipher.DECRYPT_MODE);
    private String TdesWay;

    ConvertData convertData;
    MainActivity mainActivity;

    EncryptOrDecryptConditions(byte [] ivByte , byte [] plainTextByte , byte [] keyByte , String Algorithm , String Mode ,
            String Padding , String plaintextStr , String ivStr , String keyStr , String TdesWay){
        this.ivByte = ivByte;
        this.plainTextByte = plainTextByte;
        this.keyByte = keyByte;
        this.Algorithm = Algorithm;
        this.Mode = Mode;
        this.Padding = Padding;
        this.plaintextStr = plaintextStr;
        this.ivStr = ivStr;
        this.keyStr = keyStr;
        this.TdesWay = TdesWay;
    }

    public void setMessage(TextView tvMsg , byte [] message){
        tvMsg.setText(convertData.byteArrayToHex(message));
        tvMsg.setMovementMethod(new ScrollingMovementMethod());
    }

    public void encrypCondition(TextView tvMsg){
        EncryptAndDecrypt encryptAndDecrypt = new EncryptAndDecrypt(this.plainTextByte, this.keyByte, this.Algorithm, this.Mode, this.Padding);
        TripleDes tripleDes = new TripleDes(plainTextByte ,ivByte , Mode ,Padding , keyStr , TdesWay);
        DialogAlert dialogAlert = new DialogAlert(mainActivity.getMainContext());

        if (this.plaintextStr.length() % 2 != 0) {
            dialogAlert.alert("明文不得為奇數");
            return;
        }
        if(this.plainTextByte == null || this.keyByte == null){
            dialogAlert.alert("不得為空，請重新輸入");
            return;
        }
        if(this.Padding.equals("NoPadding") && this.plaintextStr.length() % 16  != 0
         ||this.Padding.equals("NoPadding") && this.plaintextStr.length() % 32  != 0){
            dialogAlert.alert("使用NoPadding明文需填滿8或16的倍數");
            return;
        }
        if (this.Mode.equals("CBC")) {             // mode = CBC
            if (this.ivByte == null) {
                dialogAlert.alert("不得為空，請重新輸入");
            }else {
                if (this.Algorithm.equals("DES")) {
                    if (this.keyByte.length != 8 || this.ivByte.length != 8) {      //判斷鑰匙長度是否正確
                        dialogAlert.alert("鑰匙或IV長度有誤");
                    } else {
                        byte [] result = encryptAndDecrypt.EncryptOrDecrypt(this.ivByte , ENCRYPT_MODE);
                        setMessage(tvMsg , result);
                    }
                } else if (this.Algorithm.equals("AES")) {
                    if (this.keyByte.length != 16 || this.ivByte.length != 16) {         //判斷鑰匙長度是否正確
                        dialogAlert.alert("鑰匙或IV長度有誤");
                    } else {
                        byte [] result = encryptAndDecrypt.EncryptOrDecrypt(this.ivByte , ENCRYPT_MODE);
                        setMessage(tvMsg , result);
                    }
                } else if (this.Algorithm.equals("DESede") && this.TdesWay.equals("Triple")) {
                    if (this.keyByte.length != 24 || this.ivByte.length != 8) {
                        dialogAlert.alert("鑰匙或IV值長度有誤");                        //判斷鑰匙長度是否正確
                    } else {
                        byte [] result = tripleDes.encryptTdes(this.ivByte , this.keyStr);
                        setMessage(tvMsg , result);
                    }
                }else if(this.Algorithm.equals("DESede") && this.TdesWay.equals("Twice")){
                    if (this.keyByte.length != 16 || this.ivByte.length != 8) {
                        dialogAlert.alert("鑰匙或IV值長度有誤");                        //判斷鑰匙長度是否正確
                    } else {
                        byte [] result = tripleDes.encryptTdes(this.ivByte , this.keyStr);
                        setMessage(tvMsg , result);

                    }
                }
            }
        } else {
            if (this.Algorithm.equals("DES")) {
                if (this.keyByte.length != 8) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte [] result = encryptAndDecrypt.EncryptOrDecrypt(ENCRYPT_MODE);
                    setMessage(tvMsg , result);
                }
            } else if (this.Algorithm.equals("AES")) {
                if (this.keyByte.length != 16) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte [] result = encryptAndDecrypt.EncryptOrDecrypt(ENCRYPT_MODE);
                    setMessage(tvMsg , result);

                }
            } else if (this.Algorithm.equals("DESede") && this.TdesWay.equals("Triple")) {
                if (this.keyByte.length != 24) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte [] result = tripleDes.encryptTdes(this.keyStr);
                    setMessage(tvMsg , result);
                }
            }else if(this.Algorithm.equals("DESede") && this.TdesWay.equals("Twice")){
                if (this.keyByte.length != 16) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte [] result = tripleDes.encryptTdes(this.keyStr);
                    setMessage(tvMsg , result);
                }
            }
        }
    }


    public void decrypCondition(TextView tvMsg) {
        EncryptAndDecrypt encryptAndDecrypt = new EncryptAndDecrypt(this.plainTextByte, this.keyByte, this.Algorithm, this.Mode, this.Padding);
        TripleDes tripleDes = new TripleDes(plainTextByte, ivByte, Mode, Padding, keyStr, TdesWay);
        DialogAlert dialogAlert = new DialogAlert(mainActivity.getMainContext());

        if (this.plaintextStr.length() % 2 != 0) {
            dialogAlert.alert("明文不得為奇數");
            return;
        }
        if(this.plainTextByte == null || this.keyByte == null){
            dialogAlert.alert("不得為空，請重新輸入");
            return;
        }
        if(this.plaintextStr.length() % 8 != 0 || this.plaintextStr.length() % 16 != 0){
            dialogAlert.alert("解密需填滿8或16的倍數");
            return;
        }
        if (this.Mode.equals("CBC")) {
            if (this.ivByte == null) {
                dialogAlert.alert("不得為空，請重新輸入");
            } else {
                if (this.Algorithm.equals("DES")) {
                    if (this.keyByte.length != 8 || this.ivByte.length != 8) {      //判斷鑰匙長度是否正確
                        dialogAlert.alert("鑰匙或IV長度有誤");
                    } else {
                        byte[] result = encryptAndDecrypt.EncryptOrDecrypt(this.ivByte, DECRYPT_MODE);
                        setMessage(tvMsg , result);
                    }
                } else if (this.Algorithm.equals("AES")) {
                    if (this.keyByte.length != 16 || this.ivByte.length != 16) {         //判斷鑰匙長度是否正確
                        dialogAlert.alert("鑰匙或IV長度有誤");
                    } else {
                        byte[] result = encryptAndDecrypt.EncryptOrDecrypt(this.ivByte, DECRYPT_MODE);
                        setMessage(tvMsg , result);
                    }
                } else if (this.Algorithm.equals("DESede") && this.TdesWay.equals("Triple")) {
                    if (this.keyByte.length != 24 || this.ivByte.length != 8) {
                        dialogAlert.alert("鑰匙或IV值長度有誤");                        //判斷鑰匙長度是否正確
                    } else {
                        byte[] result = tripleDes.decryptTdes(this.ivByte, this.keyStr);
                        setMessage(tvMsg , result);
                    }
                } else if (this.Algorithm.equals("DESede") && this.TdesWay.equals("Twice")) {
                    if (this.keyByte.length != 16 || this.ivByte.length != 8) {
                        dialogAlert.alert("鑰匙或IV值長度有誤");                        //判斷鑰匙長度是否正確
                    } else {
                        byte[] result = tripleDes.decryptTdes(this.ivByte, this.keyStr);
                        setMessage(tvMsg , result);
                    }
                }
            }
        } else {     //mode = ECB
            if (this.Algorithm.equals("DES")) {
                if (this.keyByte.length != 8) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte[] result = encryptAndDecrypt.EncryptOrDecrypt(DECRYPT_MODE);
                    setMessage(tvMsg , result);
                }
            } else if (this.Algorithm.equals("AES")) {
                if (this.keyByte.length != 16) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte[] result = encryptAndDecrypt.EncryptOrDecrypt(DECRYPT_MODE);
                    setMessage(tvMsg , result);
                }
            } else if (this.Algorithm.equals("DESede") && this.TdesWay.equals("Triple")) {
                if (this.keyByte.length != 24) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte[] result = tripleDes.decryptTdes(this.keyStr);
                    setMessage(tvMsg , result);
                }
            } else if (this.Algorithm.equals("DESede") && this.TdesWay.equals("Twice")) {
                if (this.keyByte.length != 16) {      //判斷鑰匙長度是否正確
                    dialogAlert.alert("鑰匙長度有誤");
                } else {
                    byte[] result = tripleDes.decryptTdes(this.keyStr);
                    setMessage(tvMsg , result);
                }
            }
        }
    }



}
