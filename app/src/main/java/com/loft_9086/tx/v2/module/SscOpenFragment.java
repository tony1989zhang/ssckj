package com.loft_9086.tx.v2.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loft_9086.tx.v2.R;
import com.loft_9086.tx.v2.app.App;
import com.loft_9086.tx.v2.base.BasePageAdapter;
import com.loft_9086.tx.v2.bean.PaoMaBean;
import com.loft_9086.tx.v2.bean.ShiShiCai_KaiJiang;
import com.loft_9086.tx.v2.bean.SscBean;
import com.loft_9086.tx.v2.module.sscls.SscHositoryAct;
import com.loft_9086.tx.v2.net.ConnectionManager;
import com.loft_9086.tx.v2.net.ConnectionUtil;
import com.loft_9086.tx.v2.utils.LogUtil;
import com.loft_9086.tx.v2.utils.StringUtils;
import com.loft_9086.tx.v2.view.DividerItemDecoration;
import com.loft_9086.tx.v2.view.TAGView;
import com.loft_9086.tx.v2.view.TimeCountDownTextView;
import com.loft_9086.tx.v2.view.TitleView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class SscOpenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_POSITION = "position";
    private int position;
    protected RecyclerView mViewList;
    private TextView mEmptyTips;
    BasePageAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    protected TitleView mTitleView;
    public static SscOpenFragment newInstance(int position)
    {
        SscOpenFragment shishiCaiFragment = new SscOpenFragment();
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
        BmobQuery<ShiShiCai_KaiJiang> query  = new BmobQuery<>();
        query.findObjects(new FindListener<ShiShiCai_KaiJiang>() {
            @Override
            public void done(List<ShiShiCai_KaiJiang> list, BmobException e) {
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
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_ssc_kj, viewGroup, false);
            return new ShiShiCaiHodler(inflate);
        }

        @Override
        public void doBindViewHolder(RecyclerView.ViewHolder viewHoder, int position) {
            LogUtil.d("position","position:" + position);
            if ( viewHoder instanceof ShiShiCaiHodler)
            {
               final ShiShiCaiHodler lh = (ShiShiCaiHodler) viewHoder;
                final ShiShiCai_KaiJiang zhiPo = (ShiShiCai_KaiJiang) mAdapter.getItems().get(position);
                Log.e("zhiPo","zhiPo" + zhiPo.toString());
                TextView titleView = lh.title;
                titleView.setText("" + zhiPo.getTitile());

                loadSscKj(lh, zhiPo);
                lh.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "点击跳转", Toast.LENGTH_SHORT).show();
                        SscHositoryAct.onNewInstant(getActivity(),zhiPo.titile,zhiPo.SscKj_History,zhiPo.type);
                        getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    }
                });

            }
        }
         Timer timer;
        private void loadSscKj(final ShiShiCaiHodler lh, final ShiShiCai_KaiJiang zhiPo) {

            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            timer = new Timer();


            ConnectionManager.getInstance().getSScKaiJiang(getContext(), zhiPo.get_award_data, new ConnectionUtil.OnDataLoadEndListener() {
                @Override
                public void OnLoadEnd(String ret) {
                    lh.ll.removeAllViews();
                    if (zhiPo.type == 2) {
                        PaoMaBean beanFromJson = App.getInstance().getBeanFromJson(ret, PaoMaBean.class);
                        Log.e("beanFromJson","beanFromJson:" + beanFromJson.toString());
                        if(beanFromJson == null || beanFromJson.current == null || beanFromJson.next == null)
                        {
                            return;
                        }
                        String awardNumbers = beanFromJson.current.awardNumbers;
                        if (StringUtils.isEmpty(awardNumbers))
                        {
                            return;
                        }
                        Integer integer = Integer.valueOf(beanFromJson.next.awardTimeInterval);
                        Log.e("integer","integer:" + integer);
                        lh.timeCountDowntv.setCountDownTimes(integer*1000);
                        String[] split = awardNumbers.split(",");
                        for (int i = 0;i < split.length ;i ++)
                        {
                            TAGView inflate = (TAGView) LayoutInflater.from(getContext()).inflate(R.layout.view_tag, null);
                            String text = split[i];
                            if (text.length()<2)
                                text = "0" + text;

                            inflate.setText(text);
                            lh.ll.addView(inflate);
                        }
                    }else{
                        SscBean sscBean = App.getInstance().getBeanFromJson(ret, SscBean.class);
                        String result = sscBean.items.current.result;
                        int i1 = Integer.valueOf(sscBean.items.next.interval);
                        Log.e("il","il" + i1);
                        lh.timeCountDowntv.setCountDownTimes(i1);
                        String[] split = result.split(",");
                        for (int i = 0; i< split.length;i++)
                        {
                            if (getActivity()!= null)
                            { TAGView inflate = (TAGView) LayoutInflater.from(getActivity()).inflate(R.layout.view_tag, null);
                            String text = split[i];
                            if (text.length()<2)
                                text = "0" + text;

                            inflate.setText(text);
                            lh.ll.addView(inflate);}
                        }
                    }


                    lh.timeCountDowntv.start();
                    lh.timeCountDowntv.setOnCountDownFinishListener(new TimeCountDownTextView.onCountDownFinishListener() {
                        @Override
                        public void onFinish() {
                          //  lh.timeCountDowntv.set
                            if (zhiPo.type == 2){
                                timer.schedule(new RequestTimerTask(lh,zhiPo), 30000);//3秒后执行TimeTask的run方法
                            }else{
                                timer.schedule(new RequestTimerTask(lh,zhiPo), 8000);
                            }

                        }
                    });
                }
            });
        }

        public class RequestTimerTask extends TimerTask{
            private  ShiShiCaiHodler lh;
            ShiShiCai_KaiJiang zhiPo;
            public RequestTimerTask(final ShiShiCaiHodler lh, final ShiShiCai_KaiJiang zhiPo){
                this.lh = lh;
                this.zhiPo = zhiPo;
            }

            @Override
            public void run() {
                loadSscKj(lh, zhiPo);
            }
        }

        class ShiShiCaiHodler extends RecyclerView.ViewHolder{
           public TextView title;
           public TimeCountDownTextView timeCountDowntv;
            public LinearLayout ll;

            public ShiShiCaiHodler(View itemView) {
                super(itemView);
                 ll = (LinearLayout)itemView.findViewById(R.id.ll);
                title = (TextView) itemView.findViewById(R.id.title);
                timeCountDowntv = (TimeCountDownTextView) itemView.findViewById(R.id.countdown);
            }
        }
    }
}
