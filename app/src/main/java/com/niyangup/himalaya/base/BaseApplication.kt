package com.niyangup.himalaya.base

import android.app.Application
import android.content.Context
import android.os.Handler
import com.niyangup.himalaya.utils.LogUtil
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest
import com.ximalaya.ting.android.opensdk.datatrasfer.DeviceInfoProviderDefault
import com.ximalaya.ting.android.opensdk.datatrasfer.IDeviceInfoProvider

class BaseApplication : Application() {

    companion object {
        private lateinit var sHandle: Handler

        fun getHandle(): Handler {
            return sHandle;
        }
    }


    override fun onCreate() {
        super.onCreate()
        val mXimalaya = CommonRequest.getInstanse()
        if (DTransferConstants.isRelease) {
            val mAppSecret = "8646d66d6abe2efd14f2891f9fd1c8af"
            mXimalaya.setAppkey("9f9ef8f10bebeaa83e71e62f935bede8")
            mXimalaya.setPackid("com.app.test.android")
            mXimalaya.init(this, mAppSecret, getDeviceInfoProvider(this))
        } else {
            val mAppSecret = "0a09d7093bff3d4947a5c4da0125972e"
            mXimalaya.setAppkey("f4d8f65918d9878e1702d49a8cdf0183")
            mXimalaya.setPackid("com.ximalaya.qunfeng")
            mXimalaya.init(this, mAppSecret, getDeviceInfoProvider(this))
        }

        LogUtil.init(this.packageName, false)
        sHandle = Handler()
    }

    private fun getDeviceInfoProvider(context: Context?): IDeviceInfoProvider {
        return object : DeviceInfoProviderDefault(context) {
            override fun oaid(): String {
                return "oaid"
            }
        }
    }
}