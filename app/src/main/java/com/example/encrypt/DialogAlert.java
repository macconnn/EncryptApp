package com.example.encrypt;


import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class DialogAlert extends MainActivity {

    private Context context;

    DialogAlert(Context context){
        this.context = context;
    }

    public void alert(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.context)
                .setTitle("警告")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }


}
