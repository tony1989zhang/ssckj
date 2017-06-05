package com.loft_9086.tx.v2;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.loft_9086.tx.v2.base.BasePageAdapter;
import com.loft_9086.tx.v2.bean.Ssckj_Banner;
import com.loft_9086.tx.v2.bean.ZhiPo_Fm01;
import com.loft_9086.tx.v2.module.sszcp.WebAct;
import com.loft_9086.tx.v2.net.ConnectionUtil;
import com.loft_9086.tx.v2.utils.LogUtil;
import com.loft_9086.tx.v2.utils.PermissionUtils;
import com.loft_9086.tx.v2.view.AdDialog;
import com.loft_9086.tx.v2.view.DividerItemDecoration;
import com.loft_9086.tx.v2.view.TitleView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class LiveFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_POSITION = "position";
    private int position;
    protected RecyclerView mViewList;
    private TextView mEmptyTips;
    BasePageAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    protected TitleView mTitleView;
    public static LiveFragment newInstance(int position)
    {
        LiveFragment liveFragment = new LiveFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION,position);
        liveFragment.setArguments(b);
        return liveFragment;
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
        //mViewList.setOnTouchListener(touchListener);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);
        mViewList.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refrsh_color1, R.color.swipe_refrsh_color2, R.color.swipe_refrsh_color3,
                R.color.swipe_refrsh_color4);
        requestData();
        PermissionUtils.requestPermission(getActivity(),PermissionUtils.CODE_READ_PHONE_STATE,mPermissionGrant);
        return rootView;
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    initAlderDialogDate();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    break;
                case PermissionUtils.CODE_CAMERA:
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(getActivity(), requestCode, permissions, grantResults, mPermissionGrant);
    }
    private void initAlderDialogDate() {
        BmobQuery<Ssckj_Banner> query = new BmobQuery<>();
        query.findObjects(new FindListener<Ssckj_Banner>() {
            @Override
            public void done(List<Ssckj_Banner> list, BmobException e) {
                imageList.clear();
                imageListUrl.clear();
                if (list != null && list.size() > 0)
                {
                    for (int i = 0;i < list.size();i++)
                    {
                        imageList.add(list.get(i).pic);
                        imageListUrl.add(list.get(i).picUrl);
                    }
                    if (imageList != null && imageList.size() > 0)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showAlerDialog();
                        }
                    });

                }
            }
        });
    }

    private void showAlerDialog() {
        // 读取要显示的图片
        AdDialog dialog = new AdDialog(getActivity(), R.style.FullScreenDialogTheme, imageList);
        dialog.setOnDialogOnClickListener(new AdDialog.OnDialogOnClickListener() {
            @Override
            public void getAdDialogOnCLickListener(int position) {
                if (imageListUrl != null && imageListUrl.size() >0)
                WebAct.onNewInstant(getActivity(),imageListUrl.get(position));
            }
        });
        dialog.show();
    }


    protected void requestData() {

        hideEmpty();
        BmobQuery<ZhiPo_Fm01> query = new BmobQuery<>();
        query.findObjects(new FindListener<ZhiPo_Fm01>() {
            @Override
            public void done(List<ZhiPo_Fm01> list, BmobException e) {
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
    List<String> imageList = new ArrayList<>();
    List<String> imageListUrl = new ArrayList<>();



    class LiveAdapter extends BasePageAdapter{

        @Override
        protected RecyclerView.ViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_zhipo, viewGroup, false);
            return new LiveViewHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            LogUtil.d("position","position:" + position);
            if ( viewHoder instanceof  LiveViewHodler)
            {
                    LiveViewHodler lh = (LiveViewHodler) viewHoder;
                    final ZhiPo_Fm01 zhiPo = (ZhiPo_Fm01) mAdapter.getItems().get(position);
                    TextView titleView = lh.titleView;
                    titleView.setText(zhiPo.getTitle());
                    NetworkImageView iv = lh.iv;
                   ConnectionUtil.getInstance().loadImgae(iv,zhiPo.pic);



                lh.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WebActivity.onNewInstance(getActivity(),zhiPo.title,zhiPo.picUrl);
                    }
                });
            }
        }
        class LiveViewHodler extends RecyclerView.ViewHolder{
           public TextView titleView;
            public  NetworkImageView iv;
            public LinearLayout ll;

            public LiveViewHodler(View itemView) {
                super(itemView);
                 ll = (LinearLayout)itemView.findViewById(R.id.ll);
                titleView = (TextView) itemView.findViewById(R.id.title_view);
                 iv = (NetworkImageView) itemView.findViewById(R.id.iv);
            }
        }
    }
}
