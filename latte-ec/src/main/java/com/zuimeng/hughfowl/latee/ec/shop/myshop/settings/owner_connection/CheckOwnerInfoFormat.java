package com.zuimeng.hughfowl.latee.ec.shop.myshop.settings.owner_connection;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Rhapsody on 2018/3/30.
 */

public class CheckOwnerInfoFormat {

    private static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    private static boolean checkQQ(String QQ) {
        if (!(QQ.length() < 5 || QQ.length() > 15)) {
            if (!QQ.startsWith("0")) {
                char[] arr = QQ.toCharArray();
                for (char anArr : arr) {
                    if (anArr < '0' || anArr > '9') {
                        return false;
                    }
                }
                return true;
            } else return false;
        } else return false;
    }

    public boolean checkForm(TextView qq, TextView wx, TextView phone) {
        final String qqNum = qq.getText().toString();
        final String wxNum = wx.getText().toString();
        final String phoneNum = phone.getText().toString();

        boolean isPass = true;

        if (!checkQQ(qqNum)) {
            qq.setError("错误的qq号码");
            isPass = false;
        } else {
            qq.setError(null);
        }

        if (wxNum.length()<6||wxNum.length()>20) {
            wx.setError("错误的微信号码");
            isPass = false;
        } else {
            wx.setError(null);
        }

        if (phoneNum.isEmpty() || !isMobileNO(phoneNum)) {
            phone.setError("手机号码错误");
            isPass = false;
        } else {
            phone.setError(null);
        }
        return isPass;
    }

}
