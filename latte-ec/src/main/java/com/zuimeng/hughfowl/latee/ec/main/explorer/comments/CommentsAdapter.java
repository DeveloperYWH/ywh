package com.zuimeng.hughfowl.latee.ec.main.explorer.comments;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.main.explorer.moments.SectionBean;

import java.util.List;

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
        helper.setText(R.id.single_comment,name);

    }
}