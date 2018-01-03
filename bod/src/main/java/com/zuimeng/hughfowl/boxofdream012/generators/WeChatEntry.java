package com.zuimeng.hughfowl.boxofdream012.generators;

import com.example.annotations.EntryGenerator;
import com.zuimeng.hughfowl.latte.wechat.templates.WXEntryTemplate;

/**
 * Created by hughfowl on 2017/9/20.
 */

@SuppressWarnings("unused")

@EntryGenerator(
        packageName = "com.zuimeng.hughfowl.boxofdream012",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
