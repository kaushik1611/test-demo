package com.inexture.test.demo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.inexture.test.demo.R
import com.inexture.test.demo.databinding.ActivityHomeBinding
import com.livinglifetechway.k4kotlin.setBindingView
import com.livinglifetechway.k4kotlin.toastNow
import java.net.URL


class HomeActivity : AppCompatActivity() {

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = setBindingView(R.layout.activity_home)

        // handle dynamic link if present
        handleDynamicLink()

        // get the notification extras and show it on the screen
        // this can be used with the extra data passed in the notification
        mBinding.notificationData.text = intent.extras?.keySet()
                ?.joinToString(separator = "\n") { key -> "$key => ${intent.extras.get(key)}" }

    }

    /**
     * Checks if the dynamic link is received or not
     * Handles the navigation if dynamic link is present
     */
    private fun handleDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    // Get deep link from result
                    if (pendingDynamicLinkData != null) {
                        // deep link has been found
                        val deepLink: Uri? = pendingDynamicLinkData.link
                        val str = "Dynamic Link Received: $deepLink"
                        mBinding.dynamicLinkData.text = str

                        // parse the link
                        // We are handling domain: example.com
                        // get the url for easy parsing
                        val url = URL(deepLink.toString())

                        // check for the valid host
                        if (url.host == "example.com" || url.host == "www.example.com") {
                            // path after extracting host
                            var path = url.path

                            // check for initial "/" character, remove if present
                            if (path.substring(0, 1) == "/") {
                                path = path.substring(1)
                            }

                            // check url path for navigation
                            when {
                                path.startsWith("type1") -> startActivity(Intent(this, DynamicLink1Activity::class.java))
                                path.startsWith("type2") -> startActivity(Intent(this, DynamicLink2Activity::class.java))
                                else -> toastNow("Sorry, the app cannot handle this type of URL yet")
                            }
                        }
                    }
                }
                .addOnFailureListener(this) { e -> Log.d(TAG, "getDynamicLink:onFailure", e) }
    }
}
