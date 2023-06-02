package com.purple.core.designsystem.dialog

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.purple.core.designsystem.component.HiFilledButton
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography
import com.purple.core.model.type.DeleteDialogType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HiAlertDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    content: DeleteDialogType,
) {
    HiTheme() {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally),
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = content.questionText,
                    style = HiTypography.bodyLarge,
                )
            },
            confirmButton = {
                HiFilledButton(
                    onClick = onDelete,
                    enabled = true,
                    text = {
                        Text(
                            text = content.confirmText,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                )
            },
            dismissButton = {
                HiOutlinedButton(
                    onClick = onDismiss,
                    text = {
                        Text(
                            text = "아니요",
                            color = MaterialTheme.colorScheme.primary,
                            style = HiTypography.bodyMedium,
                        )
                    },
                )
            },
        )
    }
}

@Preview
@Composable
private fun PreviewHiAlertDialog() {
    HiAlertDialog(
        onDismiss = {},
        onDelete = {},
        DeleteDialogType.DELETE_GROUP,
    )
}
