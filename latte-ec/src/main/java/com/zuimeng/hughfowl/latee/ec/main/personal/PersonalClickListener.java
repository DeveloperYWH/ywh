package com.zuimeng.hughfowl.latee.ec.main.personal;/*
package com.zuimeng.hughfowl.latee.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.zuimeng.hughfowl.latee.ec.main.personal.list.ListBean;
import com.zuimeng.hughfowl.latte.delegates.LatteDelegate;

*/
/**
 * Created by hczz
 *//*


public class PersonalClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    public PersonalClickListener(LatteDelegate delegate) {
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
*/