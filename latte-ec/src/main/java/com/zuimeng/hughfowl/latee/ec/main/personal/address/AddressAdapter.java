package com.zuimeng.hughfowl.latee.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.zuimeng.hughfowl.latee.ec.R;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latte.ui.loader.LatteLoader;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleFields;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleItemEntity;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleRecyclerAdapter;
import com.zuimeng.hughfowl.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by hughfowl on 2018/1/23.
 */

public class AddressAdapter extends MultipleRecyclerAdapter {
    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name = entity.getField(MultipleFields.NAME);
                final String phone = entity.getField(AddressItemFields.PHONE);
                final String address = entity.getField(AddressItemFields.ADDRESS);
                final boolean isDefault = entity.getField(MultipleFields.TAG);
                final int id = entity.getField(MultipleFields.ID);

                final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
                final AppCompatTextView deleteTextView = holder.getView(R.id.tv_address_delete);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AVQuery<AVObject> query = new AVQuery<>("User_address");
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
                                final String Jdata = list.get(0).toJSONObject().toString();
                                final JSONArray address = JSON.parseObject(Jdata).getJSONArray("user_address");
                                //内容循环
                                final int addressSize = address.size();
                                for (int j = 0;  j <  addressSize; j++) {
                                    final JSONObject content_data = address.getJSONObject(j);
                                    if (id == content_data.getInteger("id")){
                                        address.remove(j);
                                        remove(holder.getLayoutPosition());
                                        list.get(0).put("user_address",address);
                                        list.get(0).saveInBackground();
                                        break;
                                    }


                                }



                            }
                        });

                    }
                });

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);
                break;
            default:
                break;
        }
    }
}
