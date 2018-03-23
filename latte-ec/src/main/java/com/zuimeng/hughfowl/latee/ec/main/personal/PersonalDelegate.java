package com.zuimeng.hughfowl.latee.ec.main.personal;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
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
    CircleImageView mCircleImageView = null;
    @BindView(R2.id.user_name)
    TextView mtextView = null;

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;
    Number user_right = 0;
    private AlertDialog mDialog = null;

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
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull final View rootView) {

        this.mDialog = new AlertDialog.Builder(PersonalDelegate.this.getContext()).create();

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

                try {
                    URL url = new URL((list.get(0).getAVFile("image").getUrl()));
                    Glide.with(rootView)
                            .load(url)
                            .into(mCircleImageView);
                    LatteLoader.stopLoading();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                mtextView.setText(list.get(0).getString("user_name").toCharArray(), 0, list.get(0).getString("user_name").toCharArray().length);
            }
        });

        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
        LatteLoader.showLoading(getContext());
        avQuery.whereEqualTo("mobilePhoneNumber",
                String.valueOf(DatabaseManager
                        .getInstance()
                        .getDao()
                        .queryBuilder()
                        .listLazy()
                        .get(0)
                        .getUserId()));
        avQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {

                user_right = list.get(0).getNumber("user_type");

                final List<ListBean> data = new ArrayList<>();
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

                data.add(address);
                data.add(bill_date);
                data.add(system);

                if ((int) user_right != 1) {
                    final ListBean my_shop = new ListBean.Builder()
                            .setItemType(ListItemType.ITEM_NORMAL)
                            .setId(4)
                            .setDelegate(new ShopBottomDelegate())
                            .setText("我的店铺")
                            .build();
                    data.add(my_shop);
                }

                if ((int) user_right == 1) {
                    final ListBean my_shop = new ListBean.Builder()
                            .setItemType(ListItemType.ITEM_NORMAL)
                            .setId(4)
                            .setText("我的店铺")
                            .build();
                    data.add(my_shop);
                }

                //设置RecyclerView
                final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                mRvSettings.setLayoutManager(manager);
                final ListAdapter adapter = new ListAdapter(data);
                mRvSettings.setAdapter(adapter);
                mRvSettings.addOnItemTouchListener(new PersonalClickListener(PersonalDelegate.this));
            }
        });
    }

    public void beginCreateDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_creat_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.dialog_creat_shop).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getParentDelegate().getSupportDelegate().start(new CheckDelegate());
                    mDialog.cancel();
                }
            });
            window.findViewById(R.id.dialog_creat_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.cancel();
                }
            });
        }
    }
}
