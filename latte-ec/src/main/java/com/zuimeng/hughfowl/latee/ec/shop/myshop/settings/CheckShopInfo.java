package com.zuimeng.hughfowl.latee.ec.shop.myshop.settings;

import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.shop.ShopBottomDelegate;
import com.zuimeng.hughfowl.latee.ec.shop.profile.ShopProfileDelegate;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragmentDelegate;

/**
 * Created by Rhapsody on 2018/3/30.
 */

public class CheckShopInfo {

    private SupportFragmentDelegate mSupportDelegate;
    private ShopProfileDelegate mDelegate;

    private boolean fullName;
    private boolean shortName;
    private boolean openDate;
    private boolean shopStyle;
    private boolean itemCount;
    private boolean connectWay;
    private boolean totalRight;

    public void setDELEGATE(SupportFragmentDelegate supportDelegate,ShopProfileDelegate delegate){
        mSupportDelegate = supportDelegate;
        mDelegate = delegate;
    }

    public void checkShopInfoEdit() {
        final AVQuery<AVObject> query_name = new AVQuery<>("Shop_Info");
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
                AVObject data = list.get(0);
                fullName = data.getBoolean("mustEditName");
                shortName = data.getBoolean("mustEditShortName");
                openDate = data.getBoolean("mustEditDate");
                shopStyle = data.getBoolean("mustEditStyle");
                itemCount = data.getBoolean("mustEditItemCount");
                connectWay = data.getBoolean("mustEditOwnerInfo");
                totalRight = fullName && shortName && openDate && shopStyle && itemCount && connectWay;
                if (totalRight){
                    improveRight();
                    Toast.makeText(mDelegate.getContext(),"店铺信息初始化成功~",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(mDelegate.getContext(),"请填写所有必填信息~",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void improveRight(){
        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
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
                int user_right = (int) list.get(0).getNumber("user_type");
                if (user_right == 2) {
                    list.get(0).put("user_type", 3);
                    list.get(0).saveInBackground();
                    mSupportDelegate.start(new ShopBottomDelegate());
                }
            }
        });
    }
}
