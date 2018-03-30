package com.zuimeng.hughfowl.latee.ec.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hughfowl on 2018/3/23.
 */

public class CheckDelegate extends LatteDelegate {

    @BindView(R2.id.invite_code)
    AppCompatEditText mTextView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate2_check_right;
    }

    @OnClick(R2.id.check_shop_button)
    void OnClick() {
        final AVQuery<AVObject> avQuery = new AVQuery<>("Invite_Code");
        LatteLoader.showLoading(getContext());
        avQuery.whereEqualTo("objectId",
                String.valueOf(mTextView.getText()));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "激活码不正确。", Toast.LENGTH_SHORT).show();
                } else {

                    final AVQuery<AVObject> query = new AVQuery<>("_User");
                    query.whereEqualTo("mobilePhoneNumber",
                            String.valueOf(DatabaseManager
                                    .getInstance()
                                    .getDao()
                                    .queryBuilder()
                                    .listLazy()
                                    .get(0).getUserId()));
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            AVObject data = list.get(0);
                            data.put("user_type",2);
                            data.saveInBackground();
                        }
                    });
                    AVObject data = list.get(0);
                    Toast.makeText(getContext(), "激活成功~~", Toast.LENGTH_SHORT).show();
                    getSupportDelegate().start(new ShopBottomDelegate());
                    try {
                        data.delete();
                    } catch (AVException e1) {
                        e1.printStackTrace();
                    }
                    data.deleteInBackground();
                }
            }
        });
        LatteLoader.stopLoading();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}
