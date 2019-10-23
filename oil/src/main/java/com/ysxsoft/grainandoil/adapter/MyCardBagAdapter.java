package com.ysxsoft.grainandoil.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.com.ListBaseAdapter;
import com.ysxsoft.grainandoil.com.SuperViewHolder;
import com.ysxsoft.grainandoil.modle.BankCardListBean;

public class MyCardBagAdapter extends ListBaseAdapter<BankCardListBean.DataBean> {
    private OnEditorListener onEditorListener;

    public MyCardBagAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.bank_card_item_layout;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        BankCardListBean.DataBean dataBean = mDataList.get(position);
        TextView tv_open_bank = holder.getView(R.id.tv_open_bank);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_bank_card_num = holder.getView(R.id.tv_bank_card_num);
        ImageView img_editor = holder.getView(R.id.img_editor);
        TextView tv_delete = holder.getView(R.id.tv_delete);
        tv_open_bank.setText(dataBean.getHouse());
        tv_name.setText(dataBean.getRealname());
        tv_bank_card_num.setText(dataBean.getNumber());
        img_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditorListener!=null){
                    onEditorListener.editorClick(position);
                }
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditorListener!=null){
                    onEditorListener.DeleteClick(position);
                }
            }
        });
    }

    public interface OnEditorListener{
        void editorClick(int position);
        void DeleteClick(int position);

    }
    public void setOnEditorListener(OnEditorListener onEditorListener){
        this.onEditorListener = onEditorListener;
    }
}
