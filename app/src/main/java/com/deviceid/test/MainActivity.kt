package com.deviceid.test

import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val DEVICE_ID = "device-id"
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "device-test"
    var wifiManager: WifiManager? = null
    var sharedPref: SharedPreferences? = null
    var telephonyManager: TelephonyManager? = null
    val TAG = "DEVICE_ID_TEST"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val deviceID = getDeviceId(this, sharedPref, wifiManager, telephonyManager)
//        deviceId.text = deviceID
        Log.d(TAG, "$deviceID")

        getDeviceID(telephonyManager!!)?.apply {
            deviceId.text = this
            Log.d(TAG, "DEVICE ID: $this")
        }
        getAndroidID(this).apply {
            androidID.text = this
            Log.d(TAG, "ANDROID ID: $this")
        }
        getMacAddress(wifiManager!!).apply {
            macAddress.text = this
            Log.d(TAG, "MAC ADDRESS: $this")
        }
        UUID.randomUUID().toString().apply {
            randomUUID.text = this
            Log.d(TAG, "randomUUID: $this")
        }
    }

    fun getDeviceId(
        context: Context,
        sharedPreferences: SharedPreferences?,
        wifiManager: WifiManager?,
        telephonyManager: TelephonyManager?
    ): String? {
        var deviceId = sharedPreferences!!.getString(DEVICE_ID, "")
        if (!TextUtils.isEmpty(deviceId)) return deviceId
        deviceId = generateDeviceId(context, wifiManager!!, telephonyManager!!)
        // sharedPreferences.edit().putString(DEVICE_ID, deviceId).apply()
        return deviceId
    }

    private fun generateDeviceId(
        context: Context,
        wifiManager: WifiManager,
        telephonyManager: TelephonyManager
    ): String? {
//        if (BuildConfig.DEBUG && false) return "867686020986012"
//        if (DeviceUtils.INSTANCE.isInEmulator()) {
//            return "12345";//Constants.DEFAULT_EMULATOR_DEVICEID
//        }
        var deviceId: String? = null
        deviceId = getDeviceID(telephonyManager)
        if (!TextUtils.isEmpty(deviceId)) return deviceId

        deviceId = getAndroidID(context)
        if (!TextUtils.isEmpty(deviceId)) return deviceId

        deviceId = getMacAddress(wifiManager)
        if (!TextUtils.isEmpty(deviceId)) return deviceId

        return UUID.randomUUID().toString()
    }

    private fun getDeviceID(telephonyManager: TelephonyManager): String? {
        try {
            return telephonyManager.deviceId
        } catch (e: SecurityException) {
            Log.e(TAG, "getDeviceID : " + e.stackTrace)
        }
        return "error"
    }

    private fun getAndroidID(context: Context): String? {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
    }

    private fun getMacAddress(wifiManager: WifiManager): String? {
        return wifiManager.connectionInfo.macAddress
    }
}