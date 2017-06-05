package com.loft_9086.tx.v2.module;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.NetworkImageView;
import com.loft_9086.tx.v2.R;
import com.loft_9086.tx.v2.WebActivity2;
import com.loft_9086.tx.v2.app.App;
import com.loft_9086.tx.v2.base.BaseListFragment;
import com.loft_9086.tx.v2.base.BasePageAdapter;
import com.loft_9086.tx.v2.bean.NewsBean;
import com.loft_9086.tx.v2.net.ConnectionManager;
import com.loft_9086.tx.v2.net.ConnectionUtil;

import java.util.List;
/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class NewsFragment extends BaseListFragment {

    private static final String ARG_POSITION = "position";

    public static NewsFragment newInstance(int position)
    {
        NewsFragment liveFragment = new NewsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION,position);
        liveFragment.setArguments(b);
        return liveFragment;
    }
    @Override
    protected List convertToBeanList(String json) {
        NewsBean beanFromJson = App.getInstance().getBeanFromJson(json, NewsBean.class);
        List<NewsBean.NewsEntity> msg = beanFromJson.msg;
        return msg;
    }

    @Override
    protected BasePageAdapter initAdapter() {
        return new NewsAdapter();
    }

    @Override
    protected boolean isSwipeRefreshLayoutEnabled() {
        return true;
    }

    @Override
    protected int getSizeInPage() {
        return 0;
    }

    @Override
    protected Request initRequest(int start) {
        return ConnectionManager.getInstance().getNewsLists(getContext(),1,this);
    }

    @Override
    protected boolean isPageEnabled() {
        return false;
    }

    @Override
    protected boolean isDataGot() {
        return false;
    }

    class NewsAdapter extends BasePageAdapter {

        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.msg_listview_item, viewGroup, false);
            return new ItemViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            if (viewHoder instanceof ItemViewHodler) {
                final ItemViewHodler holder = (ItemViewHodler) viewHoder;
                final NewsBean.NewsEntity msgBean = (NewsBean.NewsEntity) mItems.get(position);
                holder.mMsg_author.setText(msgBean.summary);
                String logofile = msgBean.logofile;
                ConnectionUtil.getInstance().loadImgae(holder.mMsg_pic,logofile);
                holder.mMsg_title.setText(msgBean.title);
                holder.mMsg_time.setText(msgBean.publishdate);
                holder.mLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebActivity2.onNewInstance(getActivity(),msgBean.title,msgBean.url);
                        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    }
                });
            }
        }
    }

    class ItemViewHodler extends RecyclerView.ViewHolder {

        private LinearLayout mLl;
        private NetworkImageView mMsg_pic;
        private TextView mMsg_title;
        private TextView mMsg_author;
        private TextView mMsg_time;

        public ItemViewHodler(View itemView) {
            super(itemView);
            bindViews();
        }
        private void bindViews() {
            mLl = (LinearLayout) itemView.findViewById(R.id.ll);
            mMsg_pic = (NetworkImageView) itemView.findViewById(R.id.msg_pic);
            mMsg_title = (TextView) itemView.findViewById(R.id.msg_title);
            mMsg_author = (TextView) itemView.findViewById(R.id.msg_author);
            mMsg_time = (TextView) itemView.findViewById(R.id.msg_time);
        }
    }
}
