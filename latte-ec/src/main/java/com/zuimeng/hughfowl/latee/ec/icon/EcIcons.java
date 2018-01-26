package com.zuimeng.hughfowl.latee.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by hughfowl on 2017/8/22.
 */

public enum EcIcons implements Icon{
    icon_scan('\ue631'),
    icon_find('\ue615'),
    icon_message('\ue6ba');
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
