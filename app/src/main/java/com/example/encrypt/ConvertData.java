package com.example.encrypt;


public class ConvertData {

    public static byte [] stringHexToByte(String str) {
        if (str.equals("")) {
            byte[] val = null;
            return val;
        } else {
            byte[] val = new byte[str.length() / 2];
            for (int i = 0; i < val.length; i++) {
                int index = i * 2;
                int j = Integer.parseInt(str.substring(index, index + 2), 16);
                val[i] = (byte) j;
            }
            return val;
        }
    }

    public static String byteArrayToHex(byte[] a) {
        if (a == null) {
            String result = "Error 請確認輸入內容是否正確";
            return result;
        } else {
            StringBuilder sb = new StringBuilder(a.length * 2);
            for (byte b : a)
                sb.append(String.format("%02x", b));
            return sb.toString();
        }
    }

    public static String hexToAscii(String hexStr) {
        StringBuilder ascii_str = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            ascii_str.append((char) Integer.parseInt(str, 16));
        }
        return ascii_str.toString();
    }


}
