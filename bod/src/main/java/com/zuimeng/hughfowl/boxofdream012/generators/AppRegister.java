package com.zuimeng.hughfowl.boxofdream012.generators;

import com.example.annotations.AppRegisterGenerator;
import com.zuimeng.hughfowl.latte.wechat.templates.AppRegisterTemplate;

/**
 * Created by hughfowl on 2017/9/20.
 */

@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.zuimeng.hughfowl.boxofdream012",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
