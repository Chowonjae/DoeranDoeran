package com.purple.core.model.type

enum class InputDialogType(
    val question: String,
    val placeHolder: String,
    val supportingText: String,
) {
    ROOM_NAME(
        "그룹 이름",
        "ex) 우리 가족",
        "내가 보고 싶은 그룹 이름을 입력해주세요",
    ),
    EDIT_ROOM_NAME(
        "변경할 그룹 이름",
        "ex) 우리 가족",
        "변경할 그룹 이름을 입력해주세요",
    ),
    NAME(
        "그룹에서 사용할 이름",
        "ex) 김둘리 or 아들",
        "이름을 입력해주세요",
    ),
    EDIT_NAME(
        "변경할 이름",
        "ex) 이짱구 or 딸",
        "변경할 이름을 입력해주세요",
    ),
    QUESTION_PASSWORD(
        "비밀번호 질문",
        "ex) 첫째 딸의 생일은?",
        "그룹과 관련된 질문을 입력해주세요",
    ),
    EDIT_QUESTION_PASSWORD(
        "변경할 비밀번호 질문",
        "ex) 첫째 아들의 생일은?",
        "변경할 질문을 입력해주세요",
    ),
    CREATE_PASSWORD(
        "비밀번호 입력",
        "******",
        "비밀번호를 입력해주세요",
    ),
    EDIT_PASSWORD(
        "변경할 비밀번호 입력",
        "******",
        "변경할 비밀번호를 입력해주세요",
    ),
}
