package com.zuimeng.hughfowl.latee.ec.main.personal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.personal.address.AddressDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.billdate.BillDateDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListAdapter;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListItemType;
import com.zuimeng.hughfowl.latee.ec.main.personal.order.OrderListDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.profile.UserProfileDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.settings.SettingsDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.CheckDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.ShopBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.bottom.BottomItemDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rhapsody on 2018/1/6.
 */

public class PersonalDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings = null;
    @BindView(R2.id.img_user_avatar)
    CircleImageView mcircleImageView = null;
    @BindView(R2.id.user_name)
    TextView mtextView = null;

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar() {
        getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
    }
    
    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder() {
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }


    private void startOrderListByType() {
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().getSupportDelegate().start(delegate);
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {


        //获取头像
        final AVQuery<AVObject> query = new AVQuery<>("User_avater");
        LatteLoader.showLoading(getContext());
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

                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        URL url = null;
                        try {
                            url = new URL((list.get(0).getAVFile("image").getUrl()));

                            try {
                                mcircleImageView.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
                                LatteLoader.stopLoading();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        }


                    }
                }).start();



            }
        });

        //获取用户名
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
        LatteLoader.showLoading(getContext());
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
                //noinspection ResultOfMethodCallIgnored
                mtextView.setText( list.get(0).getString("user_name").toCharArray(),0,list.get(0).getString("user_name").toCharArray().length);
            }
        });


        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setText("收货地址")
                .build();

        final ListBean bill_date = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setDelegate(new BillDateDelegate())
                .setText("尾款日历")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .build();

        final ListBean my_shop = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setDelegate(new CheckDelegate())
                .setText("我的店铺")
                .build();


        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(bill_date);
        data.add(system);
        data.add(my_shop);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
//
//    public Bitmap returnBitMap(String url){
//        Bitmap bitmap = null;
//        try {
//            //加载一个网络图片
//            InputStream is = new URL(url).openStream();
//            bitmap = BitmapFactory.decodeStream(is);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
}
