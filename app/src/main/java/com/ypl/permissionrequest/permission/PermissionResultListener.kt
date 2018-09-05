package com.ypl.permissionrequest.permission

/**
 * author: 00829bill
 * date:  2018/5/31 10:43
 * describe:申请权限的回调
 */
interface PermissionResultListener {

    fun granted(permission: String)

    fun denied(permission: String)

    fun deniedForever(permission: String)

}