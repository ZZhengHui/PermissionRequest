package com.ypl.permissionrequest.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.ypl.permissionrequest.R
import java.util.*

/**
 * author: 00829bill
 * date: 2018/5/31 10:41
 * describe:请求权限实现类
 */
class PermissionRequestImpl internal constructor(private val activity: Activity, private val callBack: PermissionResultListener) : PermissionRequestListener {
    private var pers: HashMap<String, String>? = null
    //    private val applicationContext = PenglaiApplication.getSingleInstance().applicationContext
    override fun requestPermission(permissions: Array<String>) {
        if (pers == null) pers = HashMap() else pers!!.clear()
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                pers!!.put(permission, permission)   //加入未申请的权限
            } else {
                callBack.granted(permission)
            }
        }
        if (pers!!.size > 0) {
            val permission = ArrayList<String>()
            for ((_, value) in pers!!) {
                permission.add(value)
            }
            val perms = permission.toTypedArray()
            ActivityCompat.requestPermissions(activity, perms, 0x001)
        }
    }

    override fun onPermissionCallBack(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.size > 1)
            for (i in permissions.indices) {
                grantedPermission(permissions[i], grantResults, i)
            }
        else
            grantedPermission(permissions[0], grantResults, 0)
    }

    private fun grantedPermission(permission: String, grantResults: IntArray, position: Int) {
        if (grantResults[position] == PackageManager.PERMISSION_GRANTED) {
            pers!!.remove(permission)
            callBack.granted(permission)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                callBack.denied(permission)
            } else {
                lackOfPermission(permission)
            }
        }
    }

    private fun lackOfPermission(permission: String) {
        var perName = ""
        when (permission) {
            Manifest.permission.CALL_PHONE -> perName = "打电话"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> perName = "读写本地文件"
            Manifest.permission.SEND_SMS -> perName = "发短信"
            Manifest.permission.CAMERA -> perName = "拍照"
        }
        Toast.makeText(activity, activity.getString(R.string.lack_of_permission, perName), Toast.LENGTH_SHORT).show()
        callBack.deniedForever(perName)
    }
}
