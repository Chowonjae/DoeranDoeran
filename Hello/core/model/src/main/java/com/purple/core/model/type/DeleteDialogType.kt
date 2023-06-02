package com.purple.core.model.type

enum class DeleteDialogType(
    val questionText: String,
    val confirmText: String,
) {
    EXIT_GROUP(
        "정말 방에서 나가시나요?",
        "나가기",
    ),
    DELETE_GROUP(
        "정말 방을 삭제하실 건가요?",
        "삭제",
    ),
    LOGOUT(
        "로그아웃 하기",
        "로그아웃",
    ),
    DELETE_USER(
        "회원 탈퇴 하기",
        "탈퇴",
    ),
    DELETE_NOTIFICATIONS(
        "알림 목록 모두 비우기",
        "비우기",
    ),
}
