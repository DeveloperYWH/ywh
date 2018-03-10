package com.zuimeng.hughfowl.latee.ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;
import com.zuimeng.hughfowl.latte.app.Latte;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.date.DateDialogUtil;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;
import com.zuimeng.hughfowl.latte.util.callback.IGlobalCallback;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Rhapsody
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final UserProfileDelegate DELEGATE;
    private int mWhich = 0;

    private String[] mGenders = new String[]{"男", "女", "保密"};


    public UserProfileClickListener(UserProfileDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onItemClick(final BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                Toast.makeText(DELEGATE.getContext(),"只支持jpg格式哦！",Toast.LENGTH_LONG).show();
                //开始照相机或选择图片
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(final Uri args) {
                                final ImageView avatar = view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(DELEGATE)
                                        .load(args)
                                        .into(avatar);
                                final AVQuery<AVObject> query = new AVQuery<>("User_avater");
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
                                            image = AVFile.withAbsoluteLocalPath("user_avatar.jpg",args.getPath());
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
                               /*RestClient.builder()
                                        .url(UploadConfig.UPLOAD_IMG)
                                        .loader(DELEGATE.getContext())
                                        .file(args.getPath())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSuccess(String response) {
                                                //LatteLogger.d("ON_CROP_UPLOAD", response);
                                                final String path = JSON.parseObject(response).getJSONObject("result")
                                                        .getString("path");

                                                //通知服务器更新信息
                                                RestClient.builder()
                                                        .url("user_profile.php")
                                                        .params("avatar", path)
                                                        .loader(DELEGATE.getContext())
                                                        .success(new ISuccess() {
                                                            @Override
                                                            public void onSuccess(String response) {
                                                                //获取更新后的用户信息，然后更新本地数据库
                                                                //没有本地数据的APP，每次打开APP都请求API，获取信息
                                                            }
                                                        })
                                                        .build()
                                                        .post();
                                            }
                                        })
                                        .build()
                                        .upload();*/
                            }
                        });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                final LatteDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        mWhich = which;
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
            case 5:

                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders,mWhich, listener);
        builder.show();
    }

    //private void getUserName()

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
