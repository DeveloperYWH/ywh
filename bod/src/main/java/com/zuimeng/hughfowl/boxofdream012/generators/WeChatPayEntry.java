package com.zuimeng.hughfowl.boxofdream012.generators;

import com.example.annotations.PayEntryGenerator;
import com.zuimeng.hughfowl.latte.wechat.templates.WXPayEntryTemplate;

/**
 * Created by hughfowl on 2017/9/20.
 */
@SuppressWarnings("unused")

@PayEntryGenerator(
        packageName = "com.zuimeng.hughfowl.boxofdream012",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
