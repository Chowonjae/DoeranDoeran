package com.purple.core.designsystem.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.purple.core.designsystem.theme.HiTheme
import com.purple.core.designsystem.theme.HiTypography
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HiDatePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
) {
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val isDateError = remember(selectedDate.value) {
        mutableStateOf(selectedDate.value.isAfter(LocalDate.now()))
    }
    HiTheme {
        Popup {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            ) {
                CalendarView(
                    header = if (isDateError.value) {
                        Header.Custom(
                            header = {
                                Text(
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .fillMaxWidth(),
                                    text = "오늘 날짜 이후는 검색 할 수 없어요!",
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.error,
                                    style = HiTypography.titleMedium,
                                )
                            },
                        )
                    } else {
                        null
                    },
                    useCaseState = rememberUseCaseState(
                        onCloseRequest = { onDismiss() },
                        onFinishedRequest = { if (!isDateError.value) onConfirm(selectedDate.value) },
                        onDismissRequest = { onDismiss() },
                    ),
                    config = CalendarConfig(
                        yearSelection = true,
                        monthSelection = true,
                        style = CalendarStyle.MONTH,
                    ),
                    selection = CalendarSelection.Date(
                        selectedDate = selectedDate.value,
                        onSelectDate = {
                            selectedDate.value = it
                        },
                    ),
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PreviewHiDatePickerDialog() {
    HiDatePickerDialog(onDismiss = {}, onConfirm = {})
}
