package com.zuimeng.hughfowl.latte.delegates;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import com.zuimeng.hughfowl.latte.ui.camera.CameraImageBean;
import com.zuimeng.hughfowl.latte.ui.camera.LatteCamera;
import com.zuimeng.hughfowl.latte.ui.camera.RequestCodes;
import com.zuimeng.hughfowl.latte.ui.scanner.ScannerDelegate;
import com.zuimeng.hughfowl.latte.util.callback.CallbackManager;
import com.zuimeng.hughfowl.latte.util.callback.CallbackType;
import com.zuimeng.hughfowl.latte.util.callback.IGlobalCallback;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by hughfowl on 2017/8/23.
 */

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    //不是直接调用方法
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    //这个是真正调用的方法
    public void startCameraWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.checkWriteWithCheck(this);
        PermissionCheckerDelegatePermissionsDispatcher.checkRedWithCheck(this);
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithCheck(this);
    }

    //扫描二维码(不直接调用)
    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(BaseDelegate delegate) {
        delegate.getSupportDelegate().startForResult(new ScannerDelegate(), RequestCodes.SCAN);
    }

    public void startScanWithCheck(BaseDelegate delegate) {
        PermissionCheckerDelegatePermissionsDispatcher.startScanWithCheck(this, delegate);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void checkWrite(){
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void checkRed(){
    }
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onWriteaDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onWriteNever() {
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onRedDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onRedNever() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked")
                    final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
