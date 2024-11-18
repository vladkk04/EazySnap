package com.arkul.eazysnap

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkul.eazysnap.data.CountryManager
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AppAndroidPreview()
        }
    }
}

@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp",
    showBackground = true
)
@Composable
fun AppAndroidPreview() {
    val context = LocalContext.current
    val countryManager = CountryManager(context)
    App(countryManager)
}

