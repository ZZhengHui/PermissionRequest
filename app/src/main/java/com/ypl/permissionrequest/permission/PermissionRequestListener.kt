package com.ypl.permissionrequest.permission

import android.support.annotation.NonNull;
/**
 * author: 00829bill
 * date: 2018/5/31 10:41
 * describe:申请权限功能
 */
interface PermissionRequestListener {

    fun requestPermission(permissions: Array<String>)

    fun onPermissionCallBack(requestCode: Int, @NonNull permissions: Array<out String>, @NonNull grantResults: IntArray)

}