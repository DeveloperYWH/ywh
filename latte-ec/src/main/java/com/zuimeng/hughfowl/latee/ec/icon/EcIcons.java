package com.zuimeng.hughfowl.latee.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by hughfowl on 2017/8/22.
 */

public enum EcIcons implements Icon{
    icon_scan('\ue631'),
    icon_home_page('\ue602'),
    icon_find_1('\ue606'),
    icon_message('\ue6ba'),
    icon_shop_cart('\ue603'),
    icon_sort('\ue605'),
    icon_myself('\ue604'),
    icon_ali_pay('\ue665'),
    icon_extra_money('\ue607'),
    icon_waiting_for_pay('\ue608'),
    icon_waiting_for_comment('\ue609'),
    icon_waiting_for_delivery('\ue60a'),
    icon_take_delivering('\ue60b'),
    icon_pitch_on('\ue60e'),
    icon_plus('\ue60c'),
    icon_minus('\ue60d');
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
