package com.android.bestbetvisual.MiscClass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.android.bestbetvisual.R


class ClassShareApp(val context: Context) {

    private val appPackageName:String? = context.packageName // getPackageName() from Context or Activity object
    private val intent :Intent = Intent()
    private val shareAppMsg:String =
            "Get \"${context.getString(R.string.app_name)}\" on Google Play Store via \n" +
                    "https://play.google.com/store/apps/details?id=$appPackageName for your Realtime Betting"
    init {
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
    }
    fun shareApp(){
        intent.putExtra(Intent.EXTRA_TEXT,shareAppMsg)
        startActivity(context, Intent.createChooser(intent,"Share to: "), Bundle())
    }
}