package com.arkul.eazysnap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        PhoneNumberUtil.getInstance().apply {
            this.supportedRegions.forEach { gg ->
                Log.d("debug", this.getCountryCodeForRegion(gg).toString())
            }
        }

        
        setContent {
            App()
        }
    }
}

@Preview(showSystemUi = true, device = "spec:width=411dp,height=891dp",
    showBackground = true
)

@Composable
fun AppAndroidPreview() {
    App()
}

