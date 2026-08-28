package com.androidcicdlab.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.androidcicdlab.app.ui.LabHomeScreen
import com.androidcicdlab.app.ui.theme.AndroidCICDLabTheme
import org.junit.Rule
import org.junit.Test

class LabHomeScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun homeShowsVariantAndTitle() {
        composeRule.setContent {
            AndroidCICDLabTheme {
                LabHomeScreen(
                    variantLabel = "qaDebug",
                    versionLabel = "1.0.0 (qadebug)",
                    environmentName = "qa",
                )
            }
        }
        composeRule.onNodeWithTag("lab_home").assertIsDisplayed()
        composeRule.onNodeWithText("AndroidCICDLab").assertIsDisplayed()
        composeRule.onNodeWithTag("variant_chip").assertIsDisplayed()
        composeRule.onNodeWithText("qaDebug").assertIsDisplayed()
        composeRule.onNodeWithText("qa").assertIsDisplayed()
    }
}
