package com.zuimeng.hughfowl.latee.ec.main.explorer.moments;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.detail.GoodsDetailDelegate;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latee.ec.main.cart.ShopCartDelegate;
import com.zuimeng.hughfowl.latee.ec.main.explorer.comments.CommentsDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latee.ec.main.personal.profile.UserProfileDelegate;
import com.zuimeng.hughfowl.latee.ec.main.sort.content.ContentDelegate;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;

import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by ywh on 2018/1/6.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    private List<AVObject> AVList = new ArrayList<>();
    private MomentsDelegate DELEGATE=null;
    private List<SectionBean> mData = null;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();
    public void SetMomentsDelegate(MomentsDelegate delegate){
        DELEGATE = delegate;
    }

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SectionBean item) {
        //item.t返回SectionBean类型
        android.support.v7.widget.LinearLayoutCompat goods=helper.getView(R.id.goods);
        AppCompatTextView goodsname=helper.getView(R.id.tv_item_shop_cart_title);
        AppCompatTextView des=helper.getView(R.id.tv_item_shop_cart_desc);
        AppCompatTextView price=helper.getView(R.id.tv_item_shop_cart_price);
        AppCompatImageView thumbpic=helper.getView(R.id.image_item_shop_cart);
        final int GoodsId=item.t.getGoodsId();
        final List thumb = item.t.getmMomentThumb();
        final String name = item.t.getmMomentContent();
        final String Id = item.t.getmMomentId();
        final SectionContentItemEntity entity = item.t;
        final RecyclerView comment=helper.getView(R.id.comments);
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        final AppCompatTextView likeamount=helper.getView(R.id.like_amount);
        final AppCompatTextView commentamount=helper.getView(R.id.comment_amount);
        if (GoodsId!=0)
        {
            goods.setVisibility(View.VISIBLE);
            final AVQuery<AVObject> query = new AVQuery<>("goodss_detail");
            query.whereEqualTo("id", GoodsId);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {

                    AVList.addAll(list);
                    final AVObject object = AVList.get(0);
                    final String Jdata = object.toJSONObject().toString();
                    final JSONArray array = JSON.parseObject(Jdata).getJSONArray("data");
                    final JSONObject goodData = array.getJSONObject(0);
                    final String title=goodData.getString("name");
                    final String desc=goodData.getString("des");
                    final String money=goodData.getString("price");
                    final JSONArray thumb = JSON.parseObject(Jdata).getJSONArray("thumb");
                    final JSONObject goodthumb = thumb.getJSONObject(0);
                    final String pic=goodthumb.getString("goods_thumb");
                    goodsname.setText(title);
                    des.setText(desc);
                    price.setText(money);
                    Glide.with(mContext)
                            .load(pic)
                            .into(thumbpic);
                }

            });
            goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final GoodsDetailDelegate delegate = GoodsDetailDelegate.create(GoodsId);
                    DELEGATE.getParentDelegate().getSupportDelegate().start(delegate);
                }
            });
        }
        comment.setLayoutManager(manager);
        LinearLayout sec=helper.getView(R.id.second_line);
        Log.d("thumbsize",String.valueOf(thumb.size()));
        if (thumb.size()<=3)
            sec.setVisibility(View.GONE);
        else
            sec.setVisibility(View.VISIBLE);
        helper.setText(R.id.content, name);
        final AppCompatImageView goodsImageView0 = helper.getView(R.id.thumb0);
        if(thumb.size()>=1)
        {
            goodsImageView0.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(thumb.get(0))
                    .into(goodsImageView0);
        }
        else
            goodsImageView0.setVisibility(View.GONE);
        final AppCompatImageView goodsImageView1 = helper.getView(R.id.thumb1);
        if(thumb.size()>=2)
        {
            Glide.with(mContext)
                    .load(thumb.get(1))
                    .into(goodsImageView1);
            goodsImageView1.setVisibility(View.VISIBLE);
        }
        else
            goodsImageView1.setVisibility(View.GONE);
        final AppCompatImageView goodsImageView2 = helper.getView(R.id.thumb2);
        if(thumb.size()>=3)
        {
            Glide.with(mContext)
                    .load(thumb.get(2))
                    .into(goodsImageView2);
            goodsImageView2.setVisibility(View.VISIBLE);
        }
        else
            goodsImageView2.setVisibility(View.GONE);
        final AppCompatImageView goodsImageView3 = helper.getView(R.id.thumb3);
        if(thumb.size()>=4)
        {
            Glide.with(mContext)
                    .load(thumb.get(3))
                    .into(goodsImageView3);
            goodsImageView3.setVisibility(View.VISIBLE);
        }
        else
            goodsImageView3.setVisibility(View.GONE);
        final AppCompatImageView goodsImageView4 = helper.getView(R.id.thumb4);
        if(thumb.size()>=5)
        {
            Glide.with(mContext)
                    .load(thumb.get(4))
                    .into(goodsImageView4);
            goodsImageView4.setVisibility(View.VISIBLE);
        }
        else
            goodsImageView4.setVisibility(View.GONE);
        final AppCompatImageView goodsImageView5 = helper.getView(R.id.thumb5);
        if(thumb.size()>=6)
        {
            Glide.with(mContext)
                    .load(thumb.get(5))
                    .into(goodsImageView5);
            goodsImageView5.setVisibility(View.VISIBLE);
        }
        else
            goodsImageView5.setVisibility(View.GONE);
        ShineButton collect=helper.getView(R.id.collect);
        final ShineButton like=helper.getView(R.id.like);
        collect.setShapeResource(R.raw.star);
        collect.setBtnColor(Color.rgb(87,87,86));
        collect.setBtnFillColor(Color.rgb(255,128,0));
        collect.setShineCount(8);
        collect.setAllowRandomColor(true);
        like.setShapeResource(R.raw.heart);
        like.setBtnColor(Color.rgb(87,87,86));
        like.setBtnFillColor(Color.rgb(227,23,13));
        like.setShineCount(8);
        like.setAllowRandomColor(true);
        final AppCompatButton submit=helper.getView(R.id.submit);
        final AppCompatEditText write=helper.getView(R.id.comment_write);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AVQuery<AVObject> query_name = new AVQuery<>("User_comments");
                query_name.whereEqualTo("moments_id", Id);
                query_name.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        final AVObject avObject = list.get(0);
                        final String Jdata = avObject.toJSONObject().toString();
                        final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("comments");
                        final  JSONObject sizeData=new JSONObject();
                        JSONObject item = new JSONObject();
                        item.put("content", write.getText());
                        item.put("id",marray.size()+1);
                        item.put("uid",
                                String.valueOf(DatabaseManager
                                        .getInstance()
                                        .getDao()
                                        .queryBuilder()
                                        .listLazy()
                                        .get(0)
                                        .getUserId()));
                        sizeData.putAll(item);
                        marray.add(sizeData);
                        avObject.put("comments",marray);
                        avObject.saveInBackground();
                        Toast.makeText(mContext,"发表成功！",Toast.LENGTH_LONG).show();
                        write.setText("");
                        int amountleft=Integer.valueOf(commentamount.getText().toString());
                        commentamount.setText(String.valueOf(amountleft+1));
                    }
                });
            }
        });
        final LinearLayout content=helper.getView(R.id.content_layout);
        final AppCompatEditText write_edit=helper.getView(R.id.comment_write);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentsDelegate commentsDelegate=new CommentsDelegate();
                commentsDelegate.setId(item);
                DELEGATE.getParentDelegate().getSupportDelegate().start(commentsDelegate);
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout comment_layout=helper.getView(R.id.comment_layout);
                if(comment_layout.getVisibility()==View.GONE)
                {
                    comment_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    comment_layout.setVisibility(View.GONE);
                }
            }
        });
        final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
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
                    final String Jdata = avObject.toJSONObject().toString();
                    final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                    for(int i=0;i<marray.size();i++)
                    {
                        if (Id.equals(marray.getJSONObject(i).getString("id")))
                        {
                            like.setChecked(true);
                        }
                    }

            }
        });

        final AVQuery<AVObject> query_name1 = new AVQuery<>("User_info");
        LatteLoader.showLoading(mContext);
        query_name1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                int amountright=0;
                for(int j=0;j<list.size();j++)
                {
                    final AVObject avObject = list.get(j);
                    final String Jdata = avObject.toJSONObject().toString();
                    final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                    for(int i=0;i<marray.size();i++)
                    {
                        if (Id.equals(marray.getJSONObject(i).getString("id")))
                        {
                            amountright++;
                        }
                    }

                }
                likeamount.setText(String.valueOf(amountright));
                LatteLoader.stopLoading();
            }
        });
        final AVQuery<AVObject> query_name2 = new AVQuery<>("User_comments");
        query_name2.whereEqualTo("moments_id", Id);
        query_name2.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                int amountleft=0;
                final AVObject avObject = list.get(0);
                final String Jdata = avObject.toJSONObject().toString();
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("comments");
                amountleft=marray.size();
                commentamount.setText(String.valueOf(amountleft));
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (like.isChecked())
                {
                    int amountright=Integer.valueOf(likeamount.getText().toString());
                    likeamount.setText(String.valueOf(amountright+1));
                    final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
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
                            final String Jdata = avObject.toJSONObject().toString();
                            final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                            JSONObject moment=new JSONObject();
                            moment.put("id",Id);
                            marray.add(moment);
                            avObject.put("like",marray);
                            avObject.saveInBackground();
                        }
                    });
                }
                else
                {
                    int amountright=Integer.valueOf(likeamount.getText().toString());
                    likeamount.setText(String.valueOf(amountright-1));
                    final AVQuery<AVObject> query_name = new AVQuery<>("User_info");
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
                            final String Jdata = avObject.toJSONObject().toString();
                            final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("like");
                            for(int i=0;i<marray.size();i++)
                            {
                                if (Id.endsWith(marray.getJSONObject(i).getString("id")))
                                {
                                    marray.remove(i);
                                }
                            }
                            avObject.put("like",marray);
                            avObject.saveInBackground();
                        }
                    });
                }
            }
        });
    }
}