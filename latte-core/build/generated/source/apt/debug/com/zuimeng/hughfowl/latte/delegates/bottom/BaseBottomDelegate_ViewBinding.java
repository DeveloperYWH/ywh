// Generated code from Butter Knife. Do not modify!
package com.zuimeng.hughfowl.latte.delegates.bottom;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.zuimeng.hughfowl.latte.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseBottomDelegate_ViewBinding<T extends BaseBottomDelegate> implements Unbinder {
  protected T target;

  @UiThread
  public BaseBottomDelegate_ViewBinding(T target, View source) {
    this.target = target;

    target.mBottomBar = Utils.findRequiredViewAsType(source, R.id.bottom_bar, "field 'mBottomBar'", LinearLayoutCompat.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mBottomBar = null;

    this.target = null;
  }
}
