package com.example.encrypt;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private Button buttonEncrypt;
    private Button buttonDecrypt;
    private EditText txtPlainText;
    private EditText txtKey;
    private EditText txtIv;
    private Spinner spAlgorithm;
    private Spinner spMode;
    private Spinner spPadding;
    private TextView tvMsg;
    private TextView tvDecrypt;
    private Spinner spTdes;
    private static Context context;
    ConvertData convertData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListensers();
        MainActivity.context = MainActivity.this;
        }


    public static Context getMainContext(){
        return MainActivity.context;
    }

    private void initViews(){
        buttonEncrypt = (Button) findViewById(R.id.btnEncrypt);
        buttonDecrypt = (Button) findViewById(R.id.btnDecrypt);
        txtPlainText = (EditText) findViewById(R.id.etPlainText);
        txtKey = (EditText) findViewById(R.id.etKey);
        txtIv = (EditText) findViewById(R.id.etIv);
        spAlgorithm = (Spinner) findViewById(R.id.spAlgorithm);
        spMode = (Spinner) findViewById(R.id.spMode);
        spPadding = (Spinner) findViewById(R.id.spPadding);
        spTdes = (Spinner) findViewById(R.id.spTdesWay);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvDecrypt = (TextView) findViewById(R.id.tvDecrypt);
    }

    private void initListensers(){
        buttonEncrypt.setOnClickListener(EncryptOnclick);  //加密按鈕
        buttonDecrypt.setOnClickListener(DecryptOnclick);  //解密按鈕
    }

    private EncryptOrDecryptConditions initInputValue(){
        //取下拉式選單的值
        String Algorithm = spAlgorithm.getSelectedItem().toString();
        String Mode = spMode.getSelectedItem().toString();
        String Padding = spPadding.getSelectedItem().toString();
        String TdesWay = spTdes.getSelectedItem().toString();
        //取input值
        String plaintextStr = txtPlainText.getText().toString();
        String ivStr = txtIv.getText().toString();
        String keyStr = txtKey.getText().toString();
        byte[] keyByte = convertData.stringHexToByte(keyStr);
        byte[] plainTextByte = convertData.stringHexToByte(plaintextStr);
        byte[] ivByte = convertData.stringHexToByte(ivStr);
        EncryptOrDecryptConditions encryptOrDecryptConditions = new EncryptOrDecryptConditions(ivByte , plainTextByte , keyByte ,
                Algorithm , Mode , Padding , plaintextStr , ivStr , keyStr , TdesWay);
        return encryptOrDecryptConditions;
    }

        private View.OnClickListener DecryptOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EncryptOrDecryptConditions encryptOrDecryptConditions = initInputValue();
                encryptOrDecryptConditions.decrypCondition(tvMsg);
            }
        };

        private  View.OnClickListener EncryptOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EncryptOrDecryptConditions encryptOrDecryptConditions = initInputValue();
                encryptOrDecryptConditions.encrypCondition(tvMsg);
            }
    };

}