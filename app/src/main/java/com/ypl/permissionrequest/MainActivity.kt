package com.ypl.permissionrequest

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ypl.permissionrequest.permission.PermissionRequestImpl
import com.ypl.permissionrequest.permission.PermissionResultListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PermissionResultListener {
    private var requestImpl: PermissionRequestImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestImpl = PermissionRequestImpl(this@MainActivity, this)
        requestPermission.setOnClickListener({
            requestImpl!!.requestPermission(arrayOf(Manifest.permission.CALL_PHONE))
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestImpl!!.onPermissionCallBack(requestCode, permissions, grantResults)
    }

    override fun granted(permission: String) {
        when (permission) {
            Manifest.permission.CALL_PHONE -> {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"))
                startActivity(intent)
            }
        }
    }

    override fun denied(permission: String) {

    }

    override fun deniedForever(permission: String) {

    }
}
