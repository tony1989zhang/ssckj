package com.loft_9086.tx.v2.module.sscls;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.loft_9086.tx.v2.R;
import com.loft_9086.tx.v2.app.App;
import com.loft_9086.tx.v2.base.BaseListFragment;
import com.loft_9086.tx.v2.base.BasePageAdapter;
import com.loft_9086.tx.v2.bean.SscHistoryBean;
import com.loft_9086.tx.v2.bean.SscHistoryPaoBean;
import com.loft_9086.tx.v2.net.ConnectionManager;
import com.loft_9086.tx.v2.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4 0004.
 */

public class HistorySsFragment extends BaseListFragment {
    public String url;
    public String title;
    public int type = 1;
    private static final String SSC_HOSITORY_TITLE = "ssc_hostitory_title";
    private  static final String SSC_HOSIROTRY_URL = "ssc_hository_url";
    private  static final String SSC_HOSIROTRY_TYPE = "ssc_hository_type";


    public static HistorySsFragment newInstance(String title,String url,int type) {
        HistorySsFragment f = new HistorySsFragment();
        Bundle b = new Bundle();
        b.putInt(SSC_HOSIROTRY_TYPE, type);
        b.putString(SSC_HOSITORY_TITLE, title);
        b.putString(SSC_HOSIROTRY_URL, url);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.list_sschistory_normal;
    }

    @Override
    public void getDate() {
        super.getDate();
        Bundle arguments = getArguments();
        type = arguments.getInt(SSC_HOSIROTRY_TYPE);
        title = arguments.getString(SSC_HOSITORY_TITLE);
        url = arguments.getString(SSC_HOSIROTRY_URL);
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        mTitleView.setTitle(title);
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setTitleBackVisibility(View.VISIBLE);
        mTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    protected List convertToBeanList(String json) {
        if (type == 2)
        {
            SscHistoryPaoBean beanFromJson = App.getInstance().getBeanFromJson(json, SscHistoryPaoBean.class);
            List<SscHistoryPaoBean.DataBean> data = beanFromJson.data;
            return data;
        }
            else
        {
            SscHistoryBean  beanFromJson = App.getInstance().getBeanFromJson(json, SscHistoryBean.class);
            List<SscHistoryBean.ItemsBean> items = beanFromJson.items;
            return items;
        }
    }

    @Override
    protected BasePageAdapter initAdapter() {
        if (type == 2)
            return new FmAdapter();
         else
        return new Fm2Adapter();
    }

    @Override
    protected boolean isSwipeRefreshLayoutEnabled() {
        return false;
    }

    @Override
    protected int getSizeInPage() {
        return 0;
    }

    @Override
    protected Request initRequest(int start) {
        return ConnectionManager.getInstance().getSscHistory(getContext(),url,this);
    }

    @Override
    protected boolean isPageEnabled() {
        return false;
    }

    @Override
    protected boolean isDataGot() {
        return false;
    }




    class FmAdapter extends BasePageAdapter {
        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = View.inflate(viewGroup.getContext(), R.layout.item_quote, null);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_item3, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            if (viewHoder instanceof ItemViewHodler) {
                final ItemViewHodler holder = (ItemViewHodler) viewHoder;
                SscHistoryPaoBean.DataBean item = (SscHistoryPaoBean.DataBean) mItems.get(position);
                holder.mTv_1.setText("" + item.open_date);
                holder.mTv_31.setText("" + item.num1);
                setTvBg(item.num1, holder.mTv_31);
                holder.mTv_32.setText("" + item.num2);
                setTvBg(item.num2, holder.mTv_32);
                holder.mTv_33.setText("" + "" + item.num3);
                setTvBg(item.num3, holder.mTv_33);
                holder.mTv_34.setText("" + item.num4);
                setTvBg(item.num4, holder.mTv_34);
                holder.mTv_35.setText("" +  item.num5);
                setTvBg(item.num5, holder.mTv_35);
                holder.mTv_36.setText("" + item.num6);
                setTvBg(item.num6, holder.mTv_36);
                holder.mTv_37.setText("" + item.num7);
                setTvBg(item.num7, holder.mTv_37);
                holder.mTv_38.setText("" + item.num8);
                setTvBg(item.num8, holder.mTv_38);
                holder.mTv_39.setText("" + item.num9);
                setTvBg(item.num9, holder.mTv_39);
                holder.mTv_310.setText("" + item.num10);
                setTvBg(item.num10, holder.mTv_310);
                int itemSum = item.num1 + item.num2;
                String bigOrSmail = "小";
                int txColor  = Color.WHITE;
                int txColor2  = Color.WHITE;
                if (itemSum > 11)
                {   bigOrSmail = "大";
                    txColor = Color.RED;
                }
                String shuangOrDan = "单";
                if (itemSum % 2 == 0)
                {   shuangOrDan = "双";
                    txColor2 = Color.RED;
                }

                String gyhStr = itemSum + " " + bigOrSmail + " " + shuangOrDan;
                SpannableString ss = new SpannableString(gyhStr);
                ss.setSpan(new ForegroundColorSpan(txColor), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(txColor2), 4, gyhStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.m_tv_gyh.setText(ss);

                String longhu1 = " 虎";
                String longhu2 = " 虎";
                String longhu3 = " 虎";
                String longhu4 = " 虎";
                String longhu5 = " 虎";
                int colorlonghu1 = Color.WHITE;
                int colorlonghu2 = Color.WHITE;
                int colorlonghu3 = Color.WHITE;
                int colorlonghu4 = Color.WHITE;
                int colorlonghu5 = Color.WHITE;
                if (item.num1 - item.num10 >0 ) {
                    longhu1 = " 龙";
                    colorlonghu1 = Color.RED;
                }
                if (item.num2 - item.num9 > 0) {
                    longhu2 = " 龙";
                    colorlonghu2 = Color.RED;
                }
                if (item.num3 - item.num8 > 0){
                    longhu3 = " 龙";
                    colorlonghu3 = Color.RED;
                }
                if (item.num4 - item.num7 > 0){
                    longhu4 = " 龙";
                    colorlonghu4 = Color.RED;
                }
                if (item.num5 - item.num6 > 0)
                {    longhu5 = " 龙";
                    colorlonghu5 = Color.RED;
                }


                String htmlLh = "<font size=\"3\" color=\"" +
                        colorlonghu1 +
                        "\">" +
                        longhu1 +
                        "</font><font size=\"3\" color=\"" +
                        colorlonghu2 +
                        "\">" +
                        longhu2 +
                        "</font>"+
                        "<font size=\"3\" color=\"" +
                        colorlonghu3 +
                        "\">" +
                        longhu3 +
                        "</font>"+
                        "<font size=\"3\" color=\"" +
                        colorlonghu4 +
                        "\">" +
                        longhu4 +
                        "</font>"+
                        "<font size=\"3\" color=\"" +
                        colorlonghu5 +
                        "\">" +
                        longhu5 +
                        "</font>";

                holder.m_tv_lh.setText(Html.fromHtml(htmlLh));


                holder.m_ll_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.m_ll_child.getVisibility() == LinearLayout.GONE)
                        {
                            holder.m_ll_child.setVisibility(View.VISIBLE);
                        }else{
                            holder.m_ll_child.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }

        public void setTvBg(int i, TextView tv) {
            switch (i) {
                case 1:
                    tv.setBackgroundResource(R.drawable.shape_solid_purple);
                    break;
                case 2:
                    tv.setBackgroundResource(R.drawable.shape_solid_blue);
                    break;
                case 3:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 4:
                    tv.setBackgroundResource(R.drawable.shape_solid_cygreen);
                    break;
                case 5:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 6:
                    tv.setBackgroundResource(R.drawable.shape_solid_dgreen);
                    break;
                case 7:
                    tv.setBackgroundResource(R.drawable.shape_solid_dcyan);
                    break;
                case 8:
                    tv.setBackgroundResource(R.drawable.shape_solid_dyellow);
                    break;
                case 9:
                    tv.setBackgroundResource(R.drawable.shape_solid_dblue);
                    break;
                case 10:
                    tv.setBackgroundResource(R.drawable.shape_solid_titlecolor);
                    break;
            }
        }

        class ItemViewHodler extends RecyclerView.ViewHolder {

            public TextView mTv_1;
            public TextView mTv_31;
            public TextView mTv_32;
            public TextView mTv_33;
            public TextView mTv_34;
            public TextView mTv_35;
            public TextView mTv_36;
            public TextView mTv_37;
            public TextView mTv_38;
            public TextView mTv_39;
            public TextView mTv_310;
            public LinearLayout m_ll_child;
            public TextView m_tv_gyh;
            public TextView m_tv_lh;
            public LinearLayout m_ll_parent;



            public ItemViewHodler(View itemView) {
                super(itemView);
//                x.view().inject(this, itemView);
                bindViews(itemView);
            }
            private void bindViews(View itemView) {
                m_ll_parent = (LinearLayout) itemView.findViewById(R.id.ll_parent);
                mTv_1 = (TextView) itemView.findViewById(R.id.tv_1);
                mTv_31 = (TextView) itemView.findViewById(R.id.tv_31);
                mTv_32 = (TextView) itemView.findViewById(R.id.tv_32);
                mTv_33 = (TextView) itemView.findViewById(R.id.tv_33);
                mTv_34 = (TextView) itemView.findViewById(R.id.tv_34);
                mTv_35 = (TextView) itemView.findViewById(R.id.tv_35);
                mTv_36 = (TextView) itemView.findViewById(R.id.tv_36);
                mTv_37 = (TextView) itemView.findViewById(R.id.tv_37);
                mTv_38 = (TextView) itemView.findViewById(R.id.tv_38);
                mTv_39 = (TextView) itemView.findViewById(R.id.tv_39);
                mTv_310 = (TextView) itemView.findViewById(R.id.tv_310);
                m_ll_child = (LinearLayout) itemView.findViewById(R.id.ll_child);
                m_tv_gyh = (TextView) itemView.findViewById(R.id.tv_gyh);
                m_tv_lh = (TextView) itemView.findViewById(R.id.tv_lh);
            }
        }
    }
    class Fm2Adapter extends BasePageAdapter {
        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = View.inflate(viewGroup.getContext(), R.layout.item_quote, null);
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_type_1, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            if (viewHoder instanceof ItemViewHodler) {
                ItemViewHodler holder = (ItemViewHodler) viewHoder;
                SscHistoryBean.ItemsBean item = (SscHistoryBean.ItemsBean) mItems.get(position);
                Log.e("item","item:" + item.toString());
                holder.mTv_1.setText("" + item.date);
                holder.mTv_2.setText("" + item.time);
                ArrayList<TextView> tvLists = new ArrayList<>();
                tvLists.add(holder.mTv_31);
                tvLists.add(holder.mTv_32);
                tvLists.add(holder.mTv_33);
                tvLists.add(holder.mTv_34);
                tvLists.add(holder.mTv_35);
                tvLists.add(holder.mTv_36);
                tvLists.add(holder.mTv_37);
                tvLists.add(holder.mTv_38);
                tvLists.add(holder.mTv_39);
                tvLists.add(holder.mTv_310);
                ArrayList<View> viewLists = new ArrayList<>();
                viewLists.add(holder.mRl_11);
                viewLists.add(holder.mRl_12);
                viewLists.add(holder.mRl_13);
                viewLists.add(holder.mRl_14);
                viewLists.add(holder.mRl_15);
                viewLists.add(holder.mRl_16);
                viewLists.add(holder.mRl_17);
                viewLists.add(holder.mRl_18);
                viewLists.add(holder.mRl_19);
                viewLists.add(holder.mRl_110);
                String[] split1 = item.result.split(",");
                int[] split = StringUtils.stringtoInt(split1);



                for (int i = 0 ;i < split.length;i++)
                {
                    setTvBg(split[i], tvLists.get(i));
                    Log.e("split","split:" + split.length + "split" + split[i]);
                                    viewLists.get(i).setVisibility(View.VISIBLE);
                }

            }
        }

        public void setTvBg(int i, TextView tv) {
               tv.setText("" + i);
            switch (i) {
                case 1:
                    tv.setBackgroundResource(R.drawable.shape_solid_purple);
                    break;
                case 2:
                    tv.setBackgroundResource(R.drawable.shape_solid_blue);
                    break;
                case 3:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 4:
                    tv.setBackgroundResource(R.drawable.shape_solid_cygreen);
                    break;
                case 5:
                    tv.setBackgroundResource(R.drawable.shape_solid_cyan);
                    break;
                case 6:
                    tv.setBackgroundResource(R.drawable.shape_solid_dgreen);
                    break;
                case 7:
                    tv.setBackgroundResource(R.drawable.shape_solid_dcyan);
                    break;
                case 8:
                    tv.setBackgroundResource(R.drawable.shape_solid_dyellow);
                    break;
                case 9:
                    tv.setBackgroundResource(R.drawable.shape_solid_dblue);
                    break;
                case 10:
                    tv.setBackgroundResource(R.drawable.shape_solid_titlecolor);
                    break;
            }
        }

        class ItemViewHodler extends RecyclerView.ViewHolder {

            public TextView mTv_1;
            public TextView mTv_2;
            public TextView mTv_31;
            public TextView mTv_32;
            public TextView mTv_33;
            public TextView mTv_34;
            public TextView mTv_35;
            public TextView mTv_36;
            public TextView mTv_37;
            public TextView mTv_38;
            public TextView mTv_39;
            public TextView mTv_310;
            public RelativeLayout mRl_11;
            public RelativeLayout mRl_12;
            public RelativeLayout mRl_13;
            public RelativeLayout mRl_14;
            public RelativeLayout mRl_15;
            public RelativeLayout mRl_16;
            public RelativeLayout mRl_17;
            public RelativeLayout mRl_18;
            public RelativeLayout mRl_19;
            public RelativeLayout mRl_110;


            public ItemViewHodler(View itemView) {
                super(itemView);
                mTv_1 = (TextView) itemView.findViewById(R.id.tv_1);
                mTv_2 = (TextView) itemView.findViewById(R.id.tv_2);
                mTv_31 = (TextView) itemView.findViewById(R.id.tv_31);
                mTv_32 = (TextView) itemView.findViewById(R.id.tv_32);
                mTv_33 = (TextView) itemView.findViewById(R.id.tv_33);
                mTv_34 = (TextView) itemView.findViewById(R.id.tv_34);
                mTv_35 = (TextView) itemView.findViewById(R.id.tv_35);
                mTv_36 = (TextView) itemView.findViewById(R.id.tv_36);
                mTv_37 = (TextView) itemView.findViewById(R.id.tv_37);
                mTv_38 = (TextView) itemView.findViewById(R.id.tv_38);
                mTv_39 = (TextView) itemView.findViewById(R.id.tv_39);
                mTv_310 = (TextView) itemView.findViewById(R.id.tv_310);
                mRl_110 = (RelativeLayout) itemView.findViewById(R.id.rl_10);
                mRl_19 = (RelativeLayout) itemView.findViewById(R.id.rl_9);
                mRl_18 = (RelativeLayout) itemView.findViewById(R.id.rl_8);
                mRl_17 = (RelativeLayout) itemView.findViewById(R.id.rl_7);
                mRl_16 = (RelativeLayout) itemView.findViewById(R.id.rl_6);
                mRl_15 = (RelativeLayout) itemView.findViewById(R.id.rl_5);
                mRl_14 = (RelativeLayout) itemView.findViewById(R.id.rl_4);
                mRl_13 = (RelativeLayout) itemView.findViewById(R.id.rl_3);
                mRl_12 = (RelativeLayout) itemView.findViewById(R.id.rl_2);
                mRl_11 = (RelativeLayout) itemView.findViewById(R.id.rl_1);
            }
        }
    }

}
