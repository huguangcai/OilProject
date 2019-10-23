package com.ysxsoft.grainandoil.widget.pop;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ysxsoft.grainandoil.R;
import com.ysxsoft.grainandoil.base.RBaseAdapter;
import com.ysxsoft.grainandoil.base.RViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Sincerly on 2019/4/27 0027
 **/
public class SelectLevelsPopupWindow {
    private PopupWindow popupWindow;
    private Activity activity;
    private String cid = "";

    public void init(Activity activity, String cid) {
        this.activity = activity;
        this.cid = cid;
    }

    public void showPop(View view, final List<Levels> data, final OnMenuSelectListener selectListener) {
        View convertView = View.inflate(activity, R.layout.pop_select_level, null);
        RecyclerView  recyclerView = convertView.findViewById(R.id.recyclerView);
        View  v = convertView.findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new RBaseAdapter<Levels>(activity, R.layout.item_pop_select_level, data) {
            @Override
            protected void fillItem(RViewHolder holder, Levels item, int position) {
                TextView menu = holder.getView(R.id.menu);
                if (item.getCid().equals(cid)) {
                    menu.setTextColor(activity.getResources().getColor(R.color.colorAccent));
                } else {
                    menu.setTextColor(activity.getResources().getColor(R.color.color333333));
                }
                menu.setText(item.getCname());
            }

            @Override
            protected int getViewType(Levels item, int position) {
                return NORMAL;
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null){
                    popupWindow.dismiss();
                }
            }
        });
        RBaseAdapter adapter = (RBaseAdapter) recyclerView.getAdapter();
        adapter.setOnItemClickListener(new RBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RViewHolder holder, View view, int position) {
                if (selectListener != null) {
                    Levels levels = data.get(position);
                    selectListener.select(levels.getCid(), levels.getCname());
                }
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(convertView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(null);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAsDropDown(view);
        popupWindow.setFocusable(true);
    }

    public boolean isShowing() {
        return popupWindow != null && popupWindow.isShowing();
    }

    public static class Levels {
        private String cid;
        private String cname;

        public String getCid() {
            return cid == null ? "" : cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname == null ? "" : cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }
    }

    public interface OnMenuSelectListener {
        void select(String cid, String cname);
    }
}
