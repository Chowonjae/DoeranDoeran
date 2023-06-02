package com.purple.core.designsystem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.purple.core.designsystem.component.HiFilledButton
import com.purple.core.designsystem.component.HiOutlinedButton
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography
import com.purple.core.model.InputData
import com.purple.core.model.createInputDataByInputType
import com.purple.core.model.type.InputDialogType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HiInputDialog(
    questionContent: List<InputData>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    properties: DialogProperties = DialogProperties(
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    ),
    confirmButtonText: String,
    dismissButtonText: String,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties,
    ) {
        HiDialogContent(
            questionContent = questionContent,
            onDismiss = onDismiss,
            onConfirm = onConfirm,
            confirmButtonText = confirmButtonText,
            dismissButtonText = dismissButtonText,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiDialogContent(
    questionContent: List<InputData>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String,
) {
    val errorIndex = remember { mutableStateOf<Int?>(null) }

    HiTheme {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth)
                .padding(DialogPadding),
        ) {
            LazyColumn(
                modifier = Modifier.padding(DialogPadding)
            ) {
                itemsIndexed(questionContent, key = null) { index, data ->
                    val isError = index == errorIndex.value
                    var textFieldValue by remember { mutableStateOf(data.inputValue) }

                    Column(
                        modifier = Modifier
                            .padding(ContentPadding),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(TitlePadding),
                        ) {
                            Text(
                                color = MaterialTheme.colorScheme.onSurface,
                                text = data.question,
                                style = HiTypography.headlineSmall,
                            )
                        }
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { newInputText ->
                                textFieldValue = newInputText
                                data.inputValue = newInputText
                            },
                            placeholder = { Text(text = data.placeHolder) },
                            supportingText = { Text(text = data.supportingText) },
                            singleLine = true,
                            maxLines = 1,
                            textStyle = HiTypography.bodyLarge,
                            isError = isError,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
            Row {
                HiOutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(1f),
                    text = { Text(text = dismissButtonText) },
                )
                HiFilledButton(
                    onClick = {
                        checkInvalid(questionContent).let { invalidIndex ->
                            if(invalidIndex == -1) onConfirm()
                            else errorIndex.value = invalidIndex
                        }
                    },
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(1f),
                    text = { Text(text = confirmButtonText) },
                )
            }
        }
    }
}

private fun checkInvalid(inputDataList: List<InputData>): Int {
    inputDataList.forEachIndexed { index, data ->
        if(data.inputValue.isEmpty()) return index
    }
    return -1
}

private val DialogPadding = PaddingValues(all = 24.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val ContentPadding = PaddingValues(bottom = 24.dp)
private val MinWidth = 280.dp
private val MaxWidth = 560.dp

@Preview
@Composable
private fun PreviewHiInputDialog() {
    HiInputDialog(
        questionContent = listOf(
            createInputDataByInputType(InputDialogType.ROOM_NAME, inputValue = "기존 데이터"),
            createInputDataByInputType(InputDialogType.NAME, inputValue = "기존 데이터"),
            InputData(
                question = "비밀번호 질문",
                placeHolder = "비밀번호 입력",
                supportingText = "질문에 맞는 비밀번호를 입력해주세요",
                inputValue = "",
            ),
        ),
        onDismiss = {},
        onConfirm = {},
        confirmButtonText = "ok",
        dismissButtonText = "nope",
    )
}
