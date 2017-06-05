package com.loft_9086.tx.v2.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.loft_9086.tx.v2.R;
import com.loft_9086.tx.v2.WebActivity2;
import com.loft_9086.tx.v2.base.BasePageAdapter;
import com.loft_9086.tx.v2.bean.ZhiPo_Fm02;
import com.loft_9086.tx.v2.net.ConnectionUtil;
import com.loft_9086.tx.v2.utils.LogUtil;
import com.loft_9086.tx.v2.view.DividerItemDecoration;
import com.loft_9086.tx.v2.view.TitleView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class ShiShiCaiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_POSITION = "position";
    private int position;
    protected RecyclerView mViewList;
    private TextView mEmptyTips;
    BasePageAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    protected TitleView mTitleView;
    public static ShiShiCaiFragment newInstance(int position)
    {
        ShiShiCaiFragment shishiCaiFragment = new ShiShiCaiFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION,position);
        shishiCaiFragment.setArguments(b);
        return shishiCaiFragment;
    }
    protected RecyclerView.LayoutManager initLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                mViewList.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        mViewList = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mViewList.setLayoutManager(initLayoutManager());
        mAdapter = new LiveAdapter();
        mAdapter.init(mViewList, false);
        mViewList.setItemAnimator(new DefaultItemAnimator());
        mViewList.addItemDecoration(new DividerItemDecoration(mViewList.getContext(), LinearLayoutManager.VERTICAL));
        mEmptyTips = (TextView) rootView.findViewById(R.id.empty_propt);
        mEmptyTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(ConnectionUtil.getInstance().isNetworkConnected(mViewList.getContext())){
                    mSwipeRefreshLayout.setRefreshing(true);
                 requestData();
                    //从新请求数据
                } else {
                    Toast.makeText(mTitleView.getContext(), R.string.network_not_connection, Toast.LENGTH_SHORT).show();
                }

            }
        });
//        mViewList.setOnTouchListener(touchListener);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);
        mViewList.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refrsh_color1, R.color.swipe_refrsh_color2, R.color.swipe_refrsh_color3,
                R.color.swipe_refrsh_color4);

        requestData();
        return rootView;
    }

    protected void requestData() {

        hideEmpty();
        BmobQuery<ZhiPo_Fm02> query = new BmobQuery<>();
        query.findObjects(new FindListener<ZhiPo_Fm02>() {
            @Override
            public void done(List<ZhiPo_Fm02> list, BmobException e) {
                mAdapter.setItems(list);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.onFinishLoading(false);
            }
        });
    }

//    protected void showLoading(){
//        if(mLoadingDialog == null){
//            mLoadingDialog = new ProgressDialog(mViewList.getContext());
//            mLoadingDialog.setCancelable(false);
//        }
//        mLoadingDialog.show();
//    }
    public void reloadData(){
        if(mAdapter != null){
            mAdapter.clearData();
        }
        requestData();
    }
    public void hideEmpty(){
        mViewList.setVisibility(View.VISIBLE);
        mEmptyTips.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        if(ConnectionUtil.getInstance().isNetworkConnected(mViewList.getContext())){
            reloadData();
        } else {
            Toast.makeText(mTitleView.getContext(),R.string.network_not_connection,Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    class LiveAdapter extends BasePageAdapter{

        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_shishicai, viewGroup, false);
            return new ShiShiCaiHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            LogUtil.d("position","position:" + position);
            if ( viewHoder instanceof ShiShiCaiHodler)
            {
                ShiShiCaiHodler lh = (ShiShiCaiHodler) viewHoder;
                final ZhiPo_Fm02 zhiPo = (ZhiPo_Fm02) mAdapter.getItems().get(position);
                TextView titleView = lh.titleView;
                titleView.setText("" + zhiPo.getTitle());
                TextView text = lh.text;
                text.setText(zhiPo.getJianjie());
                NetworkImageView iv = lh.iv;
                ConnectionUtil.getInstance().loadImgae(iv,zhiPo.pic);
                lh.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebActivity2.onNewInstance(getActivity(),zhiPo.title,zhiPo.url);
                        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    }
                });
            }
        }

        class ShiShiCaiHodler extends RecyclerView.ViewHolder{
           public TextView titleView;
           public TextView text;
            public  NetworkImageView iv;
            public LinearLayout ll;

            public ShiShiCaiHodler(View itemView) {
                super(itemView);
                 ll = (LinearLayout)itemView.findViewById(R.id.ll);
                titleView = (TextView) itemView.findViewById(R.id.title);
                text = (TextView) itemView.findViewById(R.id.text);
                 iv = (NetworkImageView) itemView.findViewById(R.id.iv);
            }
        }
    }
}
