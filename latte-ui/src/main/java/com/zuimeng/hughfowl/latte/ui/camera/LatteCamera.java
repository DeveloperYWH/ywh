package com.zuimeng.hughfowl.latte.ui.camera;

/**
 * Created by Rhapsody on 2018/1/21.
 * 照相机调用类
 */

import android.net.Uri;

import com.zuimeng.hughfowl.latte.delegates.PermissionCheckerDelegate;
import com.zuimeng.hughfowl.latte.util.file.FileUtil;

public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}