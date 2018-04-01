package com.zuimeng.hughfowl.latee.ec.main.personal;

import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.zuimeng.hughfowl.latee.ec.database.DatabaseManager;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;

import java.util.List;


public class PersonalClickListener extends SimpleClickListener {

    private final PersonalDelegate DELEGATE;

    public PersonalClickListener(PersonalDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            case 2:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            case 3:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                break;
            case 4:
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
                        Number user_right = list.get(0).getNumber("user_type");
                        if((int) user_right != 1)
                        DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
                        else
                        DELEGATE.beginCreateDialog();
                    }
                });
                break;
            case 5:
                DELEGATE.getParentDelegate().getSupportDelegate().start(bean.getDelegate());
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
