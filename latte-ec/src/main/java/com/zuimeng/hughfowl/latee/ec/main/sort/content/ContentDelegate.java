package com.zuimeng.hughfowl.latee.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.net.RestClient;
import com.zuimeng.hughfowl.latte.net.callback.ISuccess;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ywh on 2018/1/5.
 */

public class ContentDelegate extends LatteDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    @BindView(R2.id.rv_list_content)
    RecyclerView mRecyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    public static ContentDelegate newInstance(int contentId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    private void initData() {
        String id="1";
        final List<SectionBean> dataList = new ArrayList<>();
        final AVQuery<AVObject> query1 = new AVQuery<>("Sort_left");
        query1.whereEqualTo("id",mContentId+1);
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for(AVObject obj:list)
                {
                    String name=obj.getString("name");
                    final String title = "这里是"+name+"的"+"卖场";

                    //添加title
                    final SectionBean sectionTitleBean = new SectionBean(true, title);
                    sectionTitleBean.setId(1);
                    sectionTitleBean.setIsMore(true);
                    dataList.add(0,sectionTitleBean);
                    final AVQuery<AVObject> query = new AVQuery<>("Sort_right");
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            //商品内容循环
                            for(AVObject obj:list) {
                                final int goodsId =obj.getInt("goods_id");
                                final String goodsName = obj.getString("goods_name");
                                final String goodsThumb = obj.getAVFile("goods_thumb").getUrl();
                                //获取内容
                                final SectionContentItemEntity itemEntity = new SectionContentItemEntity();
                                itemEntity.setGoodsId(goodsId-1);
                                itemEntity.setGoodsName(goodsName);
                                itemEntity.setGoodsThumb(goodsThumb);
                                //添加内容
                                dataList.add(goodsId,new SectionBean(itemEntity));
                                Log.d("name",goodsName);
                                //商品内容循环结束

                                //Section循环结束
                            }
                            final SectionAdapter sectionAdapter =
                                    new SectionAdapter(R.layout.item_section_content,
                                            R.layout.item_section_header, dataList);
                            mRecyclerView.setAdapter(sectionAdapter);

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData();
    }
}
