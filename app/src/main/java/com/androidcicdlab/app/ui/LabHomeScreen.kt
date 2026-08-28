package com.androidcicdlab.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidcicdlab.app.R
import com.androidcicdlab.app.pipeline.PipelineStage
import com.androidcicdlab.app.ui.theme.AndroidCICDLabTheme
import com.androidcicdlab.app.ui.theme.labScreenGradient

/**
 * Home screen for the CI/CD lab: current variant, local pipeline, and the
 * Gradle-vs-Actions split. Visual language stays Material 3 with a purple wash.
 *
 * @param variantLabel Gradle variant, for example `devDebug`.
 * @param versionLabel Formatted version from [com.androidcicdlab.app.pipeline.VersionLabelFormatter].
 * @param environmentName Product flavor environment (`dev`, `qa`, or `prod`).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LabHomeScreen(
    variantLabel: String,
    versionLabel: String,
    environmentName: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(labScreenGradient())
            .testTag("lab_home"),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(R.string.home_tagline),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetaChip(text = variantLabel, modifier = Modifier.testTag("variant_chip"))
                MetaChip(text = versionLabel)
                MetaChip(text = environmentName)
            }
            PipelineCard()
            SplitCard()
        }
    }
}

@Composable
private fun MetaChip(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
private fun PipelineCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(stringResource(R.string.home_pipeline_title), style = MaterialTheme.typography.titleMedium)
            Text(
                text = stringResource(R.string.home_pipeline_body),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = PipelineStage.entries.joinToString(" → ") { it.name },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun SplitCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(stringResource(R.string.home_split_title), style = MaterialTheme.typography.titleMedium)
            Text(
                text = stringResource(R.string.home_split_body),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LabHomePreview() {
    AndroidCICDLabTheme {
        LabHomeScreen(
            variantLabel = "devDebug",
            versionLabel = "1.0.0 (devDebug)",
            environmentName = "dev",
        )
    }
}
