package com.androidcicdlab.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.androidcicdlab.app.pipeline.VersionLabelFormatter
import com.androidcicdlab.app.ui.LabHomeScreen
import com.androidcicdlab.app.ui.theme.AndroidCICDLabTheme

/**
 * Single-activity host for the lab home screen.
 *
 * Variant and flavor labels come from [BuildConfig] and flavor string resources
 * so `devDebug` / `qaDebug` / `prodRelease` are visible without extra modules.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val versionLabel = VersionLabelFormatter().format(
            versionName = BuildConfig.VERSION_NAME,
            flavor = BuildConfig.FLAVOR,
            buildType = BuildConfig.BUILD_TYPE,
        )
        val variantLabel = BuildConfig.FLAVOR + BuildConfig.BUILD_TYPE.replaceFirstChar { it.uppercase() }
        val environmentName = getString(R.string.environment_name)
        setContent {
            AndroidCICDLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent,
                ) { innerPadding ->
                    LabHomeScreen(
                        variantLabel = variantLabel,
                        versionLabel = versionLabel,
                        environmentName = environmentName,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
