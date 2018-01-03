package com.zuimeng.hughfowl.latee.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by hughfowl on 2017/8/22.
 */

public enum EcIcons implements Icon{
    icon_scan('\ue620'),
    icon_ali_pay('\ue502');
    private char character;

    EcIcons(char character){
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
