package com.purple.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.purple.core.designsystem.icon.HiIcons
import com.purple.core.designsystem.theme.HiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiDropDownTextField(
    value: String,
    onValueChange: (Int) -> Unit,
    iconClick: () -> Unit,
    expanded: Boolean,
    onDismiss: () -> Unit,
    content: List<String>,
    itemClick: (Int) -> Unit,
) {
    HiTheme {
        TextField(
            readOnly = true,
            value = value,
            onValueChange = { onValueChange(it.toInt()) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Unspecified,
            ),
            trailingIcon = {
                IconButton(
                    onClick = iconClick,
                ) {
                    Icon(imageVector = HiIcons.ArrowDropDown, contentDescription = "")
                }
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .background(MaterialTheme.colorScheme.background),
        ) {
            content.forEach { contentItem ->
                DropdownMenuItem(
                    text = { Text(text = contentItem) },
                    onClick = { itemClick(contentItem.toInt()) },
                )
                Divider(
                    thickness = 0.3.dp,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        }
    }
}
