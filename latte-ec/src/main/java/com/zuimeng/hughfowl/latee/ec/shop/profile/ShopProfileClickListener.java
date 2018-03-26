package com.zuimeng.hughfowl.latee.ec.shop.profile;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.date.DateDialogUtil;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;
import com.zuimeng.hughfowl.latte.util.callback.IGlobalCallback;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Rhapsody on 2018/3/20.
 */

public class ShopProfileClickListener extends SimpleClickListener {

    private final ShopProfileDelegate DELEGATE;

    public ShopProfileClickListener(ShopProfileDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                Toast.makeText(DELEGATE.getContext(), "只支持jpg格式哦！", Toast.LENGTH_LONG).show();
                //开始照相机或选择图片
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(final Uri args) {
                                final ImageView avatar = view.findViewById(R.id.img_arrow_avatar);
                                Log.e(TAG, String.valueOf(avatar) );
                                if (avatar != null){
                                    Glide.with(DELEGATE)
                                            .load(args)
                                            .into(avatar);
                                }
                                final AVQuery<AVObject> query = new AVQuery<>("Shop_Logo");
                                query.whereEqualTo("user_id",
                                        String.valueOf(DatabaseManager
                                                .getInstance()
                                                .getDao()
                                                .queryBuilder()
                                                .listLazy()
                                                .get(0).getUserId()));
                                query.findInBackground(new FindCallback<AVObject>() {
                                    @Override
                                    public void done(final List<AVObject> list, AVException e) {
                                        LatteLoader.showLoading(DELEGATE.getContext());
                                        final AVFile image;
                                        try {
                                            image = AVFile.withAbsoluteLocalPath("user_avatar.jpg", args.getPath());
                                            image.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(AVException e) {
                                                    if (e == null) {
                                                        list.get(0).put("image", image);
                                                        list.get(0).saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(AVException e) {
                                                                if (e == null) {
                                                                    LatteLoader.stopLoading();
                                                                    Toast.makeText(DELEGATE.getContext(), "上传成功", Toast.LENGTH_LONG).show();
                                                                } else {
                                                                    Toast.makeText(DELEGATE.getContext(), e.getMessage() + "上传失败", Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            }, new ProgressCallback() {
                                                @Override
                                                public void done(Integer integer) {

                                                }
                                            });
                                        } catch (FileNotFoundException e1) {
                                            e1.printStackTrace();
                                        }

                                    }
                                });
                            }
                        });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                final LatteDelegate fullNameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(fullNameDelegate);
                break;
            case 3:
                final LatteDelegate shortNameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(shortNameDelegate);
                break;
            case 4:
                final LatteDelegate englishNameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(englishNameDelegate);
                break;
            case 5:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(final String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
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
                                final AVObject avObject = list.get(0);
                                avObject.put("shop_open_date", date);
                                avObject.saveInBackground();
                            }
                        });
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
            case 6:
                final LatteDelegate shopStyleDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(shopStyleDelegate);
                break;
            case 7:
                final LatteDelegate shopGoodsItemCount = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(shopGoodsItemCount);
                break;
            case 8:
                final LatteDelegate shopSummaryDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(shopSummaryDelegate);
                break;
            case 9:
                final LatteDelegate OwnerDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(OwnerDelegate);
                break;
            case 10:
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
                        Number user_right;
                        user_right = list.get(0).getNumber("user_type");
                        if ((int)user_right == 2){
                            list.get(0).put("user_type",3);
                            list.get(0).saveInBackground();
                        }
                    }
                });
                DELEGATE.getSupportDelegate().start(bean.getDelegate(),2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
