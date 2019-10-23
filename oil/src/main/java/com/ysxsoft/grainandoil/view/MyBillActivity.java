package com.ysxsoft.grainandoil.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.fragment.MyBillFragment;
import com.ysxsoft.grainandoil.impservice.ImpService;
import com.ysxsoft.grainandoil.modle.ReMoenyBean;
import com.ysxsoft.grainandoil.utils.BaseActivity;
import com.ysxsoft.grainandoil.utils.NetWork;
import com.ysxsoft.grainandoil.widget.OnTabSelectListener;
import com.ysxsoft.grainandoil.widget.SlidingTabLayout;
import com.ysxsoft.grainandoil.widget.ViewFindUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyBillActivity extends BaseActivity implements View.OnClickListener, OnTabSelectListener {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private View img_back;
    private MyPagerAdapter mAdapter;
    private ViewPager vp_content;
    private String uid;
    private ArrayList<String> mTitles=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bill_layout);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        requestData();
        initView();
        initListener();
    }

    private void requestData() {
        NetWork.getService(ImpService.class)
                .getReMoeny(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReMoenyBean>() {
                    private ReMoenyBean reMoenyBean;

                    @Override
                    public void onCompleted() {
                        if ("0".equals(reMoenyBean.getCode())){
                            List<ReMoenyBean.DataBean> data = reMoenyBean.getData();
                            for (int i = 0; i < data.size(); i++) {
                                mTitles.add(data.get(i).getYear());
                            }

                            for (int i = 0; i <mTitles.size() ; i++) {
                              mFragments.add(MyBillFragment.getInstance(data.get(i).getList()));
                            }

                            View decorView = getWindow().getDecorView();
                            vp_content = ViewFindUtils.find(decorView, R.id.vp_content);
                            mAdapter = new MyPagerAdapter(getSupportFragmentManager());
                            vp_content.setAdapter(mAdapter);
                            SlidingTabLayout stl_tab = ViewFindUtils.find(decorView, R.id.stl_tab);
                            stl_tab.setViewPager(vp_content);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ReMoenyBean reMoenyBean) {

                        this.reMoenyBean = reMoenyBean;
                    }
                });
    }

    private void initView() {
        img_back = getViewById(R.id.img_back);
        TextView tv_title = getViewById(R.id.tv_title);
        tv_title.setText("我的账单");
    }

    private void initListener() {
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
