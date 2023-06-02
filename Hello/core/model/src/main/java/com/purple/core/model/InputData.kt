package com.purple.core.model

import com.purple.core.model.type.InputDialogType

data class InputData(
    var question: String,
    val placeHolder: String,
    var supportingText: String,
    var inputValue: String,
) {
    constructor(question: String, placeHolder: String, supportingText: String) : this(
        question = question,
        placeHolder = placeHolder,
        supportingText = supportingText,
        inputValue = "",
    )
}

fun createInputDataByInputType(type: InputDialogType, inputValue: String): InputData {
    return InputData(
        question = type.question,
        placeHolder = type.placeHolder,
        supportingText = type.supportingText,
        inputValue = inputValue,
    )
}

data class JoinRoomInputValue(
    val roomName: InputData,
    val nickName: InputData,
    val password: InputData,
)
