package com.zuimeng.hughfowl.latee.ec.detail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.joanzapata.iconify.widget.IconTextView;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.R2;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.EcBottomDelegate;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;
import com.zuimeng.hughfowl.latte.ui.animation.BezierAnimation;
import com.zuimeng.hughfowl.latte.ui.animation.BezierUtil;
import com.zuimeng.hughfowl.latte.ui.banner.HolderCreator;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.widget.CircleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by hughfowl on 2018/1/19.
 */

public class GoodsDetailDelegate extends LatteDelegate implements
        AppBarLayout.OnOffsetChangedListener,
        BezierUtil.AnimationListener {

    private List<AVObject> AVList = new ArrayList<>();

    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private int mGoodsId = -1;
    private String mGoodsThumbUrl = null;
    private int mShopCount = 0;
    private boolean isExist = false;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);

    @OnClick(R2.id.rl_add_shop_cart)
    void onClickAddShopCart() {
        LatteLoader.showLoading(getContext());
        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg);
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImg, this);

    }

    @OnClick(R2.id.icon_shop_cart)
    void onClickShopCart() {
        Toast.makeText(getContext(), "切换到购物车", Toast.LENGTH_LONG).show();
        EcBottomDelegate delegate = new EcBottomDelegate();
        delegate.setFlag(3);
        getSupportDelegate().replaceFragment(delegate, false);

    }


    private void setShopCartCount() {
        if (mShopCount == 0) {
            mCircleTextView.setVisibility(View.GONE);
        }
        final AVObject object = AVList.get(0);
        final String Jdata = object.toJSONObject().toString();
        final JSONArray array = JSON.parseObject(Jdata).getJSONArray("thumb");
        final JSONObject data = array.getJSONObject(0);
        mGoodsThumbUrl = data.getString("goods_thumb");

    }

    public static GoodsDetailDelegate create(int goodsId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBar.addOnOffsetChangedListener(this);
        mCircleTextView.setCircleBackground(Color.RED);
        initData();
        initTabLayout();
    }

    private void initPager() {
        final AVObject object = AVList.get(0);
        final String Jdata = object.toJSONObject().toString();
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), Jdata);
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor
                (ContextCompat.getColor(getContext(), R.color.app_main));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        final AVQuery<AVObject> query = new AVQuery<>("goodss_detail");
        query.whereEqualTo("id", mGoodsId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                AVList.addAll(list);
                initBanner();
                initGoodsInfo();
                initPager();
                setShopCartCount();
            }

        });
    }

    private void initGoodsInfo() {
        final AVObject object = AVList.get(0);
        final String Jdata = object.toJSONObject().toString();
        final JSONArray array = JSON.parseObject(Jdata).getJSONArray("data");
        final JSONObject goodData = array.getJSONObject(0);
        final String data = goodData.toJSONString();
        getSupportDelegate().
                loadRootFragment(R.id.frame_goods_info, GoodsInfoDelegate.create(data));
    }

    private void initBanner() {
        final AVObject avObject = AVList.get(0);
        final String Jdata = avObject.toJSONObject().toString();
        final JSONArray array = JSON.parseObject(Jdata).getJSONArray("banner_pics");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject content_data = array.getJSONObject(i);
            final String goodsThumb = content_data.getString("goods_thumb");
            images.add(goodsThumb);
        }
        mBanner
                .setPages(new HolderCreator(), images)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


    @Override
    public void onAnimationEnd() {
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);
        //与购物车数据逻辑连接部分
        mShopCount++;
        mCircleTextView.setVisibility(View.VISIBLE);
        mCircleTextView.setText(String.valueOf(mShopCount));
        final AVQuery<AVObject> query = new AVQuery<>("goodss_detail");
        query.whereEqualTo("id", mGoodsId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                AVList.addAll(list);
                final AVObject avObject = AVList.get(0);
                final String Jdata = avObject.toJSONObject().toString();
                final JSONArray marray = JSON.parseObject(Jdata).getJSONArray("data");
                final JSONObject goodData = marray.getJSONObject(0);
                //final int uid = goodData.getInteger("uid");
                final String name = goodData.getString("name");
                final String des = goodData.getString("des");
                final String price = goodData.getString("price");
                final JSONArray array = JSON.parseObject(Jdata).getJSONArray("banner_pics");
                final JSONObject content_data = array.getJSONObject(0);
                final String goodsThumb = content_data.getString("goods_thumb");

                final AVQuery<AVObject> query = new AVQuery<>("Cart_Datas");
                LatteLoader.showLoading(getContext());
                query.whereEqualTo("user_id",
                        String.valueOf(DatabaseManager
                                .getInstance()
                                .getDao()
                                .queryBuilder()
                                .listLazy()
                                .get(0).getUserId()));
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        final AVObject data = list.get(0);
                        final String Jdata = data.toJSONObject().toString();
                        final JSONArray cart_list = JSON.parseObject(Jdata).getJSONArray("shop_cart_data");
                        if (cart_list != null) {
                            isExist = false;
                            final int goodSize = cart_list.size();
                            for (int j = 0; j < goodSize; j++) {
                                final JSONObject cart_data = cart_list.getJSONObject(j);
                                if (cart_data.getInteger("id") == mGoodsId) {
                                    JSONObject object = cart_list.getJSONObject(j);
                                    object.put("count", mShopCount);
                                    data.put("shop_cart_data", cart_list);
                                    data.saveInBackground();
                                    isExist = true;
                                }
                            }
                            if (!isExist) {
                                JSONObject item = new JSONObject();
                                item.put("thumb", goodsThumb);
                                item.put("desc", des);
                                item.put("title", name);
                                item.put("price", price);
                                item.put("id", mGoodsId);
                                item.put("count", mShopCount);
                                JSONObject cartInfo = new JSONObject();
                                cartInfo.putAll(item);
                                cart_list.add(cartInfo);
                                data.put("shop_cart_data", cart_list);
                                data.saveInBackground();
                            }
                        }
                        if (cart_list == null) {
                            JSONObject item = new JSONObject();
                            item.put("thumb", goodsThumb);
                            item.put("desc", des);
                            item.put("title", name);
                            item.put("price", price);
                            item.put("id", mGoodsId);
                            item.put("count", mShopCount);
                            JSONArray cartInfo = new JSONArray();
                            cartInfo.add(item);
                            data.put("shop_cart_data", cartInfo);
                            data.saveInBackground();
                        }
                        LatteLoader.stopLoading();
                    }
                });
            }
        });
    }
}
