package com.zxw.flowrx;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by xiangwuzhu on 17/11/7.
 */

public class SecondActivity extends AppCompatActivity implements MyCallback{

    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        view = findViewById(R.id.btn);

        showDialog();

        GuardUtil.getInstance().addCallbackListener(this);
    }


    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("test dialog")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SecondActivity.this, " 执行 notify", Toast.LENGTH_LONG).show();
                        GuardUtil.getInstance().notifyAllCallback();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SecondActivity.this, "展示 no", Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog dialog = builder.create();


        //情形3.android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@XXX is not valid; is your activity running?异常处理。

        if(!isFinishing()) {
            dialog.show();
        }
    }


    public void showPopupWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null));
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @Override
    public void notifyTips() {
        Log.e("xxx", "Second 打印 ");
    }
}
