package com.purple.hello.login

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.purple.core.designsystem.component.SignInButton
import com.purple.core.designsystem.theme.HiTheme
import com.purple.hello.R
import com.purple.hello.login.google.GoogleAuthResultContract
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1

    val googleAuthResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleAuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    Log.e(TAG, "구글 로그인 실패")
                } else {
                    coroutineScope.launch {
                        val idToken = account.idToken
                        Log.i(TAG, "구글 로그인 성공: $idToken")
                        loginViewModel.googleLogin(idToken!!)
                        /* TODO : send ID Token to server and validate */
                    }
                }
            } catch (e: ApiException) {
                Log.w(TAG, "구글 로그인 실패", e)
            }
        }

    val context = LocalContext.current
    val kakaoLoginFunction: () -> Unit = {
        coroutineScope.launch {
            try {
                // 서비스 코드에서는 간단하게 로그인 요청하고 oAuthToken 을 받아올 수 있다.
                val accessToken = UserApiClient.loginWithKakao(context).accessToken
                Log.i(TAG, " $accessToken")
                loginViewModel.kakaoLogin(accessToken)
            } catch (error: Throwable) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    Log.i(TAG, "사용자가 명시적으로 취소")
                } else {
                    Log.e(TAG, "인증 에러 발생", error)
                }
            }
        }
    }

    HiTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                HiLogoImage()
                Spacer(modifier = Modifier.height(12.dp))
                SignInKakaoButton(kakaoLoginFunction)
                Spacer(modifier = Modifier.height(12.dp))
                SignInGoogleButton { googleAuthResultLauncher.launch(signInRequestCode) }
            }
        }
    }
}

@Composable
fun HiLogoImage() {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(all = 24.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dorandoran),
            contentDescription = "로고 이미지",
        )
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(
            text = "도란도란",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
private fun SignInKakaoButton(
    onClick: () -> Unit,
) {
    SignInButton(
        onClick = onClick,
        buttonContentColor = MaterialTheme.colorScheme.outlineVariant,
        painter = painterResource(id = R.drawable.ic_kakaotalk),
        contentText = "카카오 로그인",
    )
}

@Composable
private fun SignInGoogleButton(
    onClick: () -> Unit,
) {
    SignInButton(
        onClick = onClick,
        buttonContentColor = MaterialTheme.colorScheme.onPrimary,
        painter = painterResource(id = R.drawable.ic_google_logo),
        contentText = "구글 로그인",
    )
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen()
}
