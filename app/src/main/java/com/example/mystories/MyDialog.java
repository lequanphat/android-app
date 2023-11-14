package com.example.mystories;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDialog {
    public static void showMessage(Context context, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage(content);
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi nút "Đóng" được nhấn
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static void showConfirm(Context context, String content, DialogInterface.OnClickListener yesClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận");
        builder.setMessage(content);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (yesClickListener != null) {
                    yesClickListener.onClick(dialog, which);

                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
