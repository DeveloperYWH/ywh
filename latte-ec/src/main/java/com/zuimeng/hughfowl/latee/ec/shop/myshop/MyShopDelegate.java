package com.zuimeng.hughfowl.latee.ec.shop.myshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by hughfowl on 2018/1/28.
 */

public class MyShopDelegate extends BottomItemDelegate {

    @BindView(R2.id.img_user_avatar_shop)
    CircleImageView mCircleImageView = null;
    @BindView(R2.id.text_view)
    TextView mTextView = null;
    private Bundle mArgs = null;
    @BindView(R2.id.shop_display)
    RecyclerView mRecyclerView = null;


    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private List<ShopSectionBean> mData = null;
    private ShopSectionDataConverter mShopSectionDataConverter = null;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            getParentDelegate().getSupportDelegate().replaceFragment(new EcBottomDelegate(), false);
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出店铺", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mArgs = new Bundle();
    }

    public void initData() {

        final AVQuery<AVObject> query = new AVQuery<>("shop_display");
        LatteLoader.showLoading(getContext());
        query.whereEqualTo("userId",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0).getUserId()));

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done( List<AVObject> list, AVException e) {
                if (e == null) {
                    mShopSectionDataConverter = new ShopSectionDataConverter().setList(list);

                    mData = mShopSectionDataConverter.convert();
                    final ShopDisplayAdapter shopDisplayAdapter =
                            new ShopDisplayAdapter(R.layout.item2_myshop_content,
                            R.layout.item2_shop_section_header,mData);
                    if(mRecyclerView!=null)
                        mRecyclerView.setAdapter(shopDisplayAdapter);
                }
            }
        });
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate2_my_shop;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {

        LatteLoader.showLoading(getContext());
        //获取头像
        final AVQuery<AVObject> query = new AVQuery<>("User_avater");
        query.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, AVException e) {
                URL url;
                try {
                    url = new URL((list.get(0).getAVFile("image").getUrl()));
                    Glide.with(rootView)
                            .load(url)
                            .into(mCircleImageView);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //获取用户名
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
        query_name.whereEqualTo("user_id",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        query_name.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                mTextView.setText(list.get(0).getString("user_name").toCharArray(),
                        0, list.get(0).getString("user_name").toCharArray().length);
            }
        });

        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        initData();
        LatteLoader.stopLoading();
    }

}


