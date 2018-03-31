package com.zuimeng.hughfowl.latee.ec.main.explorer.comments;

import android.support.v7.widget.AppCompatTextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionBean;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ywh on 2018/1/6.
 */

public class CommentsAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public CommentsAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }


    @Override
    protected void convert(final BaseViewHolder helper, SectionBean item) {
        //item.t返回SectionBean类
        final String name = item.t.getmMomentContent();
        final String Id = item.t.getmMomentId();
        final String uid = item.t.getUid();
        final CircleImageView avater=helper.getView(R.id.avatar);
        final AppCompatTextView username=helper.getView(R.id.name);
        helper.setText(R.id.single_comment,name);
        //获取头像
        LatteLoader.showLoading(mContext);
        final AVQuery<AVObject> query = new AVQuery<>("User_avater");
        query.whereEqualTo("user_id",uid);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, AVException e) {

                try {
                    URL url = new URL((list.get(0).getAVFile("image").getUrl()));
                    Glide.with(mContext)
                            .load(url)
                            .into(avater);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //获取用户名
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
        query_name.whereEqualTo("user_id",uid);
        query_name.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                username.setText(list.get(0).getString("user_name").toCharArray(), 0, list.get(0).getString("user_name").toCharArray().length);
            }
        });
        LatteLoader.stopLoading();
    }
}