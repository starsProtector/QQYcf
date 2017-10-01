package com.sn.qqycf;


import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

/**
 * date:2017/10/1
 * author:易宸锋(dell)
 * function:用于全局的初始化,生命周期和进程的生命周期一致
 */
public class QQApplicatin extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //A.初始化环信SDK
        initHuanxin();
    }

    private void initHuanxin() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证,false
        options.setAcceptInvitationAlways(false);
        /**
         * 下面的代码是为了避免环信被初始化2次
         */
        int pid = android.os.Process.myPid();

        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }


        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源.
        EMClient.getInstance().setDebugMode(true);
    }

    /**
     * 拿到进程的名称
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;

    }

}
