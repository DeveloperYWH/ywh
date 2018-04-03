package com.zuimeng.hughfowl.latee.ec.shop.myshop.goodseries;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.widget.AutoPhotoLayout;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;
import com.zuimeng.hughfowl.latte.util.callback.IGlobalCallback;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditSeriesListDelegate extends LatteDelegate {

    @BindView(R2.id.reedit_series_name)
    TextInputEditText mSeriesName = null;
    @BindView(R2.id.recreate_series_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;
    AVFile mAvFile = null;
    private int mPosition;


    @Override
    public Object setLayout() {
        return R.layout.delegate2_series_edit;
    }

    public void getPosition(int position) {
        mPosition = position;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mAutoPhotoLayout.setMaxNum(1);
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(@Nullable Uri args) throws FileNotFoundException {
                        mAutoPhotoLayout.onCropTarget(args);
                        mAvFile = AVFile.withAbsoluteLocalPath("seriesImg", args.getPath());
                    }
                });
    }


    @OnClick(R2.id.recreate_series_btn)
    void OnClickCreateSeries() {

        String userId = String.valueOf(DatabaseManager
                .getInstance()
                .getDao()
                .queryBuilder()
                .listLazy()
                .get(0)
                .getUserId());


        LatteLoader.showLoading(getContext());

        final AVQuery<AVObject> query = new AVQuery<>("Series");
        query.whereEqualTo("user_id", userId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                AVObject data = list.get(mPosition);
                if (mAvFile != null)
                    data.put("image", mAvFile);
                boolean isRepeat = false;
                if (mSeriesName.length() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (i != mPosition) {
                            if (mSeriesName.getText().toString().equals(list.get(i).getString("name"))) {
                                mSeriesName.setError("商品系列名称不能重复");
                                isRepeat = true;
                                break;
                            }
                        }
                    }
                    if (!isRepeat) {
                        data.put("name", mSeriesName.getText());
                        data.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                getSupportDelegate().start(new SeriesListDelegate());
                            }
                        });
                    }
                } else if (mSeriesName.length() == 0) {
                    data.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            getSupportDelegate().start(new SeriesListDelegate());
                        }
                    });
                }
            }
        });
        LatteLoader.stopLoading();
    }
}
