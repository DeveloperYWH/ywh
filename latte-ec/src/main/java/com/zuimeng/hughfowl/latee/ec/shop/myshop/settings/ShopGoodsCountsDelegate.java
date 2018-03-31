package com.zuimeng.hughfowl.latee.ec.shop.myshop.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.shop.profile.ShopProfileDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Rhapsody on 2018/3/23.
 */

public class ShopGoodsCountsDelegate extends LatteDelegate {

    @BindView(R2.id.text_shop_item_count_menu)
    Spinner mSpinner = null;
    @BindView(R2.id.style_count)
    TextView mTextView = null;
    private String mItemCount;

    @Override
    public Object setLayout() {
        return R.layout.delegate2_goods_item_count;
    }

    @OnClick(R2.id.btn_shop_item_count_submit)
    void onClickSubmit() {
        final AVQuery<AVObject> query_name = new AVQuery<>("Shop_Info");
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
                final AVObject data = list.get(0);
                data.put("shop_goods_item_counts", mItemCount);
                data.put("mustEditItemCount",true);
                data.saveInBackground();
                getSupportDelegate().start(new ShopProfileDelegate(), 2);
                LatteLoader.stopLoading();
            }
        });
        Toast.makeText(this.getContext(), "修改成功！", Toast.LENGTH_LONG).show();
    }

    private void init() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mItemCount = mSpinner.getSelectedItem().toString();
                mTextView.setText(mItemCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        init();
        final AVQuery<AVObject> query_name = new AVQuery<>("Shop_Info");
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
                final AVObject data = list.get(0);
                mTextView.setText(data.getString("shop_goods_item_counts"));
                LatteLoader.stopLoading();
            }
        });
    }
}
