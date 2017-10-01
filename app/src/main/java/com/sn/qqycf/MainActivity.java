package com.sn.qqycf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 使用环信
 * 1.拷贝SDK
 * 2.配置build.gradle
 * 3.配置清单文件及Key等
 * 4.打包混淆,把代码放到proguard-rules即可
 * 5.Application的初始化
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
