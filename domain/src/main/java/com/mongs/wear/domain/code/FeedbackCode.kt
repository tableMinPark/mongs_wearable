package com.mongs.wear.domain.code

enum class FeedbackCode(
    val groupCode: String,
    val message: String,
    val secondaryMessage: String,
) {
    AUTH("FB_AUTH", "인증", "로그인,로그아웃"),
    BATTLE("FB_BATTLE", "배틀", "매칭,배틀,승리보상"),
    COLLECTION("FB_COLLECTION", "컬렉션", "컬렉션등록"),
    CONFIGURE("FB_CONFIGURE", "앱설정", "배경설정"),
    MANAGEMENT("FB_MANAGEMENT", "캐릭터", "상호작용"),
    MEMBER("FB_MEMBER", "회원정보", "스타포인트"),
    SLOT("FB_SLOT", "슬롯", "슬롯추가,슬롯삭제"),
    COMMON("FB_COMMON", "기타", "이외"),
    ;
}