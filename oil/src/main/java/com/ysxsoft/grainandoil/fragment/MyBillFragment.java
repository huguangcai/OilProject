package com.ysxsoft.grainandoil.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.base.RBaseAdapter;
import com.ysxsoft.grainandoil.base.RViewHolder;
import com.ysxsoft.grainandoil.modle.ReMoenyBean;

import java.util.List;

@SuppressLint("ValidFragment")
public class MyBillFragment extends Fragment {

    private List<ReMoenyBean.DataBean.ListBean> data;
    private RecyclerView recyclerView;

    public static MyBillFragment getInstance(List<ReMoenyBean.DataBean.ListBean> data) {
        MyBillFragment sf = new MyBillFragment();
        sf.data = data;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_bill_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new RBaseAdapter<ReMoenyBean.DataBean.ListBean>(getActivity(), R.layout.my_bill_item_fragment, data) {
            @Override
            protected void fillItem(RViewHolder holder, ReMoenyBean.DataBean.ListBean item, int position) {
                TextView tv_moth = holder.getView(R.id.tv_moth);
                TextView tv_end = holder.getView(R.id.tv_end);
                TextView tv_time = holder.getView(R.id.tv_time);
                TextView tv_money = holder.getView(R.id.tv_money);
                tv_moth.setText(item.getMonth() + "月账单");
                tv_end.setText(item.getValues());
                tv_money.setText(item.getPrice());
                tv_time.setText(item.getAddtime());
            }

            @Override
            protected int getViewType(ReMoenyBean.DataBean.ListBean item, int position) {
                return NORMAL;
            }
        });
    }

}
