package com.ysxsoft.grainandoil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.permissions.RxPermissions;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.service.DownloadService;
import com.ysxsoft.grainandoil.fragment.ClassifyFragment;
import com.ysxsoft.grainandoil.fragment.HomeFragment;
import com.ysxsoft.grainandoil.fragment.MyFragment;
import com.ysxsoft.grainandoil.fragment.ShopCardFragment;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.HomeDialogBean;
import com.ysxsoft.grainandoil.modle.UpdataVersionBean;
import com.ysxsoft.grainandoil.utils.ActivityPageManager;
import com.ysxsoft.grainandoil.utils.AppUtil;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.ImageLoadUtil;
import com.ysxsoft.grainandoil.utils.IsLoginUtils;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.utils.StatusBarUtil;
import com.ysxsoft.grainandoil.utils.updata.HProgressDialogUtils;
import com.ysxsoft.grainandoil.utils.updata.UpdateAppHttpUtil;
import com.ysxsoft.grainandoil.utils.VersionUtils;
import com.ysxsoft.grainandoil.view.LoginActivity;
import com.ysxsoft.grainandoil.view.RechargeBalanceActivity;
import com.ysxsoft.grainandoil.widget.HomeDialog;
import com.ysxsoft.grainandoil.widget.MyViewPager;
import com.ysxsoft.grainandoil.widget.UpdataDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private Fragment currentFragment = new Fragment();//（全局）
    private HomeFragment homeFragment = new HomeFragment();
    private ClassifyFragment classifyFragment = new ClassifyFragment();
    private ShopCardFragment shopCardFragment = new ShopCardFragment();
    private MyFragment myFragment = new MyFragment();
    private FrameLayout fl_content;
    private RadioGroup rg_home;
    private RadioButton rb_home, rb_classify, rb_shop_card, rb_my;
    private String uid;
    private MyViewPager vp_content;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String flag, show;
    private RxPermissions rxPermissions;
    private boolean isShowDownloadProgress;
    private UpdataDialog dialog;
    private ProgressBar proBar;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences spUid = getSharedPreferences("UID", Context.MODE_PRIVATE);
        uid = spUid.getString("uid", "");
        setHalfTransparent();
        setFitSystemWindow(false);
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        show = intent.getStringExtra("show");
        uid = intent.getStringExtra("uid");
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //申请的权限全部允许
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            showToastMessage("未授权权限，部分功能不能使用");
                        }
                    }
                });

        initView();
        initData();
        if ("show".equals(show)) {
            showDialog();
        }
        UpdataVersion();
    }

    private void UpdataVersion() {
        NetWork.getService(ImpService.class)
                .UpdataVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdataVersionBean>() {
                    private UpdataVersionBean updataVersionBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(updataVersionBean.getCode())) {
                            String versionName = AppUtil.getVersionName(mContext);
                            int i = VersionUtils.compareVersion(versionName, updataVersionBean.getData().getNew_version());
                            // 0代表相等，1代表version1大于version2，-1代表version1小于version2
                            if (i == -1) {
                                dialog = new UpdataDialog(mContext);
                                TextView tv_update = dialog.findViewById(R.id.tv_update);
                                proBar = dialog.findViewById(R.id.proBar);
                                tv_update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        proBar.setVisibility(View.VISIBLE);
                                        downloadAPK(updataVersionBean.getData().getApk_file_url());
                                    }
                                });
                                dialog.show();


//                                new UpdateAppManager
//                                        .Builder()
//                                        //当前Activity
//                                        .setActivity(MainActivity.this)
//                                        //更新地址
//                                        .setUpdateUrl(updataVersionBean.getData().getApk_file_url())
//                                        //实现httpManager接口的对象
//                                        .setHttpManager(new UpdateAppHttpUtil())
//                                        .build()
//                                        .update();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpdataVersionBean updataVersionBean) {

                        this.updataVersionBean = updataVersionBean;
                    }
                });


    }

    //  进度
    private int mProgress;
    //  文件保存路径
    private String mSavePath;
    //  判断是否停止
    private boolean mIsCancel = false;
    //  版本名称
//    private String mVersion_name = "1.0";

    /**
     * 下载APk
     *
     * @param apk_file_url
     */
    private void downloadAPK(final String apk_file_url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                        mSavePath = sdPath + "oil";
                        File dir = new File(mSavePath);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(apk_file_url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, AppUtil.getVersionName(mContext));
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 接收消息
     */
    @SuppressLint("HandlerLeak")
    private Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置进度条
                    proBar.setProgress(mProgress);
                    break;
                case 2:
                    // 隐藏当前下载对话框
                    dialog.dismiss();
                    // 安装 APK 文件
                    installAPK();
            }
        }

        ;
    };

    /**
     * 安装Apk
     */
    private void installAPK() {
        File apkFile = new File(mSavePath, AppUtil.getVersionName(mContext));
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
//      安装完成后，启动app（源码中少了这句话）

        if (null != apkFile) {
            try {
                //兼容7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            startInstallPermissionSettingActivity();
                            return;
                        }
                    }
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    mContext.startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            // 给目标应用一个临时授权
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri data = FileProvider.getUriForFile(mContext, "com.example.administrator.newspolice.fileProvider", apkFile);
//            intent.setDataAndType(data, "application/vnd.android.package-archive");
//        } else {
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//        }

//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.parse("file://" + apkFile.toString());
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        mContext.startActivity(intent);
    }

    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    private void showDiyDialog(UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
        String updateLog = updateApp.getUpdateLog();
        String msg = "";
        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog;
        }
        new AlertDialog.Builder(this)
                .setTitle(String.format("发现新版本"))
                .setMessage(msg)
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //显示下载进度
                        if (isShowDownloadProgress) {
                            updateAppManager.download(new DownloadService.DownloadCallback() {
                                @Override
                                public void onStart() {
                                    HProgressDialogUtils.showHorizontalProgressDialog(MainActivity.this, "下载进度", false);
                                }

                                /**
                                 * 进度
                                 * @param progress  进度 0.00 -1.00 ，总大小
                                 * @param totalSize 总大小 单位B
                                 */
                                @Override
                                public void onProgress(float progress, long totalSize) {
                                    HProgressDialogUtils.setProgress(Math.round(progress * 100));
                                }

                                /**
                                 *
                                 * @param total 总大小 单位B
                                 */
                                @Override
                                public void setMax(long total) {

                                }


                                @Override
                                public boolean onFinish(File file) {
                                    HProgressDialogUtils.cancel();
                                    return true;
                                }

                                @Override
                                public void onError(String msg) {
                                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    HProgressDialogUtils.cancel();
                                }

                                @Override
                                public boolean onInstallAppAndAppOnForeground(File file) {
                                    return false;
                                }
                            });
                        } else {
                            //不显示下载进度
                            updateAppManager.download();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void showDialog() {
        NetWork.getService(ImpService.class)
                .getRegulation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeDialogBean>() {
                    private HomeDialogBean homeDialogBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(homeDialogBean.getCode())) {
                            HomeDialog.setUrl(homeDialogBean.getData().getRegulation());
                            HomeDialog dialog = new HomeDialog(mContext);

                            ImageView img_tupian = dialog.findViewById(R.id.img_tupian);
                            ImageLoadUtil.GlideGoodsImageLoad(mContext, homeDialogBean.getData().getRegulation(), img_tupian);
                            img_tupian.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (IsLoginUtils.isloginFragment(mContext)) {
                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                        intent.putExtra("flag", "1");
                                        startActivity(intent);
                                    } else {
                                        Intent recharge = new Intent(mContext, RechargeBalanceActivity.class);
                                        recharge.putExtra("uid", getSharedPreferences("UID", Context.MODE_PRIVATE).getString("uid", ""));
                                        startActivity(recharge);
                                    }
                                }
                            });
                            dialog.show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HomeDialogBean homeDialogBean) {

                        this.homeDialogBean = homeDialogBean;
                    }
                });
    }

    private void initView() {
        fl_content = getViewById(R.id.fl_content);
        rg_home = getViewById(R.id.rg_home);
        rb_home = getViewById(R.id.rb_home);
        rb_classify = getViewById(R.id.rb_classify);
        rb_shop_card = getViewById(R.id.rb_shop_card);
        rb_my = getViewById(R.id.rb_my);
        vp_content = getViewById(R.id.vp_content);

    }

    private void initData() {
        fragments.add(homeFragment);
        fragments.add(classifyFragment);
        fragments.add(shopCardFragment);
        fragments.add(myFragment);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        vp_content.setAdapter(adapter);
        if ("3".equals(flag)) {
            vp_content.setCurrentItem(2);
            rg_home.check(R.id.rb_shop_card);
        } else if ("2".equals(flag)) {
            vp_content.setCurrentItem(1);
            rg_home.check(R.id.rb_classify);
        } else {
            rg_home.check(R.id.rb_home);
            vp_content.setCurrentItem(0);
        }
        homeFragment.setUid(uid);
        rg_home.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        homeFragment.setUid(uid);
                        vp_content.setCurrentItem(0);
                        break;

                    case R.id.rb_classify:
                        classifyFragment.setUid(uid);
                        vp_content.setCurrentItem(1);
                        break;

                    case R.id.rb_shop_card:
                        if (IsLoginUtils.isloginFragment(mContext)) {
                            ActivityPageManager instance = ActivityPageManager.getInstance();
                            instance.finishAllActivity();
                            startActivity(LoginActivity.class);
                        } else {
                            vp_content.setCurrentItem(2);
                        }
                        break;

                    case R.id.rb_my:
                        if (IsLoginUtils.isloginFragment(mContext)) {
                            ActivityPageManager instance = ActivityPageManager.getInstance();
                            instance.finishAllActivity();
                            startActivity(LoginActivity.class);
                        } else {
                            vp_content.setCurrentItem(3);
                        }
                        break;

                }
            }
        });

    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    private boolean isBack = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isBack) {
                finish();
            } else {
                showToastMessage("再按一次退出");
                isBack = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isBack = false;
                    }
                }, 3000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
