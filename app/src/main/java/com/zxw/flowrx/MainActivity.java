package com.zxw.flowrx;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements MyCallback{

    private TextView mTextMessage;
    private Button toastBtn;

    @BindView(R.id.thirdBtn)
    Button thirdButton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toastBtn = (Button) findViewById(R.id.toastBtn);

        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GuardUtil.getInstance().testCallback(new MyCallback() {
                            @Override
                            public void notifyTips() {
                                Log.e("zzz", "这里 111");
                            }
                        });
                    }
                });

                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GuardUtil.getInstance().testCallback(new MyCallback() {
                            @Override
                            public void notifyTips() {
                                Log.e("zzz", "我是测试 222");
                            }
                        });
                    }
                });

                thread1.start();
                thread2.start();
            }
        });

        Button secondBtn = (Button) findViewById(R.id.secondBtn);

        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });


        GuardUtil.getInstance().addCallbackListener(this);



        ActivityManager.RunningAppProcessInfo info = new ActivityManager.RunningAppProcessInfo();
        info.pid = 12;

        ButterKnife.bind(this);
    }


    private boolean isActivityRunning() {
        String packageName = this.getPackageName();
        String topActivityClassName = null;
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runTaskInfos = activityManager.getRunningTasks(1);
        if (runTaskInfos != null) {
            ComponentName name = runTaskInfos.get(0).topActivity;
            topActivityClassName = name.getClassName();
        }
        return packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName);
    }


    @OnClick(R.id.thirdBtn)
    public void onClick(){
        startActivity(new Intent(MainActivity.this, ThirdActivity.class));
    }


    public void showToast() {

//        Toast.makeText(this, "展示Toast 11", Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "展示Toast 22", Toast.LENGTH_LONG).show();

//        showDialog();

        showPopupWindow();
    }


    private void testRxjava() {

        Observable.just(blockMethod("A"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("zxw", s);
                    }
                });


//        Observable.defer(()->Observable.just(blockMethod("B")))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.e("zxw",s);
//                    }
//                });

    }

    private String blockMethod(String msg) {

        String result = "block:" + msg;
        Log.e("zxw", result);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    //情形1.android.view.WindowManager$BadTokenException: Unable to add window --token null is not valid; is your activity running?
    public void showPopupWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null));
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAtLocation(toastBtn, Gravity.CENTER, 0, 0);
    }


    //情形2.android.view.WindowManager$BadTokenException: Unable to add window --token null is not for an application ?
    //不能用getApplicationContext()

    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("test dialog")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "展示 yes", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "展示 no", Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog dialog = builder.create();

                dialog.show();
    }

    @Override
    public void notifyTips() {
        Log.e("xxx", "Main 打印 ");
    }
}
