package com.purple.hello.feature.rooms

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import com.purple.core.designsystem.component.HiIconButton
import com.purple.core.designsystem.icon.HiIcons
import com.purple.hello.feature.rooms.viewmodel.CameraViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    backToDetail: (roomId: Long) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
    )

    val isCaptured = remember { mutableStateOf(false) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    if (isError) {
        Text(text = "업로드 실패.. 네트워크 확인바람")
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        if (!isCaptured.value) {
            when {
                cameraPermissionState.status.isGranted -> {
                    CameraCapture(
                        executor = Executors.newSingleThreadExecutor(),
                        onImageCaptured = {
                            bitmap.value = it
                            isCaptured.value = true
                        },
                    )
                }
                else -> {
                    // TODO: deny 했을 때 새로 권한요청하는 dialog 필요
                    Text(text = "카메라 권한이 필요합니다")
                }
            }
        } else {
            bitmap.value?.let {
                WriteFeedScreen(
                    feedImageBitmap = it,
                    onClickBackButton = { isCaptured.value = false },
                    onClickUploadButton = {
                        coroutineScope.launch(Dispatchers.IO) {
                            runCatching {
                                cameraViewModel.uploadFeed(
                                    bitmap.value!!.toFile(context),
                                )
                            }.onFailure {
                                isError = true
                            }.onSuccess {
                                launch(Dispatchers.Main) {
                                    backToDetail(cameraViewModel.roomId)
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun CameraCapture(
    executor: Executor,
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageCaptured: (Bitmap) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onBackground),
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
        val imageCaptureUseCase by remember {
            mutableStateOf(
                ImageCapture.Builder()
                    .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .build(),
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CameraPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3 / 4f),
                onUseCase = { previewUseCase = it },
            )
            Surface(
                onClick = {
                    imageCaptureUseCase.takePhoto(
                        executor = executor,
                        onImageCaptured = onImageCaptured,
                        onError = {},
                    )
                },
                modifier = Modifier.size(90.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.background,
                content = { },
            )
        }

        LaunchedEffect(previewUseCase) {
            val cameraProvider = context.getCameraProvider()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    previewUseCase,
                    imageCaptureUseCase,
                )
            } catch (ex: Exception) {
                Log.e("CameraCapture", "Failed to bind camera use cases", ex)
            }
        }
    }
}

@Composable
private fun ColumnScope.WriteFeedScreen(
    feedImageBitmap: Bitmap,
    onClickBackButton: () -> Unit,
    onClickUploadButton: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        HiIconButton(
            onClick = onClickBackButton,
            icon = {
                Icon(
                    imageVector = HiIcons.ArrowBack,
                    contentDescription = "카메라로 돌아가자...",
                )
            },
        )
        HiIconButton(
            onClick = onClickUploadButton,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "피드 업로드",
                )
            },
        )
    }

    Image(
        bitmap = feedImageBitmap.asImageBitmap(),
        contentDescription = "",
        contentScale = ContentScale.Fit,
    )
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
private fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FIT_CENTER,
    onUseCase: (UseCase) -> Unit = { },
) {
    AndroidView(
        modifier = modifier.clipToBounds(),
        factory = { context ->
            val previewView = PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                this.scaleType = scaleType
            }
            onUseCase(
                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    },
            )
            previewView
        },
    )
}

private fun ImageCapture.takePhoto(
    executor: Executor,
    onImageCaptured: (Bitmap) -> Unit,
    onError: (ImageCaptureException) -> Unit,
) {
    takePicture(
        executor,
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onError(exception: ImageCaptureException) {
                Log.e("kilo", "Take photo error:", exception)
                onError(exception)
            }

            override fun onCaptureSuccess(image: ImageProxy) {
                //get bitmap from image
                val rotationDegrees = image.imageInfo.rotationDegrees

                val bitmap = imageProxyToBitmap(image, rotationDegrees.toFloat())

                onImageCaptured(bitmap)
                image.close()
            }
        },
    )
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

private fun imageProxyToBitmap(image: ImageProxy, degrees: Float): Bitmap {
    val planeProxy = image.planes[0]
    val buffer: ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)

    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    val matrix = Matrix()
    matrix.postRotate(degrees)

    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

private fun Bitmap.toFile(context: Context): File {
    val file = File(context.cacheDir, "image.jpg")
    val out = FileOutputStream(file)
    compress(Bitmap.CompressFormat.JPEG, 50, out)
    out.flush()
    out.close()
    return file
}
