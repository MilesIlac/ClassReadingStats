package com.milesilac.classreadingstats.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.RectF
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.milesilac.classreadingstats.ui.components.OCRTargetRegion
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import kotlin.math.hypot

@Composable
fun CameraScanPage(
    scanEvent: CameraScanEvent = CameraScanEvent.None,
    suggestText: String = "",
    onScan: (Bitmap, String) -> Unit = { _,_ -> },
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onBackClick: () -> Unit = {},
    onVisible: () -> Unit = {},
) {
    onVisible()
    BackHandler { onBackClick() }
    val isPreview = LocalInspectionMode.current

    var currentMLKitText by remember { mutableStateOf("") }

    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }

    var targetRegion by remember { mutableStateOf(RectF(0F, 0F, 0F, 0F)) }
    var testBitmap by remember { mutableStateOf<Bitmap?>(null) }

    if (isPreview.not()) {
        DisposableEffect(Unit) {
            val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(context)
            ) { image ->
                previewView.bitmap?.toCustomBitmap(targetRegion.toRect())?.let { bitmap ->
                    val inputImage = InputImage.fromBitmap(bitmap, 0)
                    textRecognizer.process(inputImage)
                        .addOnSuccessListener { visionText ->
                            onScan(bitmap, visionText.text)
                            testBitmap = bitmap
//                        currentMLKitText = visionText.text
                            val centerX = inputImage.width / 2
                            val centerY = inputImage.height / 2

                            // Find text block closest to the center
                            val centeredBlock = visionText.textBlocks.minByOrNull {
                                val box = it.boundingBox
                                if (box != null) {
                                    val blockCenterX = box.centerX()
                                    val blockCenterY = box.centerY()
                                    hypot(
                                        (centerX - blockCenterX).toDouble(),
                                        (centerY - blockCenterY).toDouble()
                                    )
                                } else Double.MAX_VALUE
                            }
                            centeredBlock?.let {
                                currentMLKitText = it.text
                            }
                        }
                        .addOnFailureListener {}
                        .addOnCompleteListener { image.close() }
                } ?: run { image.close() }
            }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)

            onDispose { cameraProvider.unbindAll() }
        }
    }

    Column {
        Box(
            modifier = Modifier
                .weight(1F)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx -> previewView }
            )
            OCRTargetRegion(
                onBoxChange = { newTargetRegion ->
                    targetRegion = newTargetRegion
                }
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 40.dp
                    )
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Suggested: $suggestText",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "MLKit Detected: $currentMLKitText",
                    modifier = Modifier,
                    color = ProjectColors.OffWhite4,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        when (scanEvent) {
            CameraScanEvent.None -> {
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color.Blue, Color.Blue),
                            ),
                            alpha = 0.3F
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    testBitmap?.let { BitmapPreview(it) }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //dialogs
    }
}

@Preview
@Composable
fun CameraScanPagePreview() {
    CameraScanPage()
}

sealed class CameraScanEvent {
    data object None: CameraScanEvent()
}


@Composable
fun BitmapPreview(bitmap: Bitmap) {
    val imageBitmap = remember(bitmap) { bitmap.asImageBitmap() }

    Image(
        bitmap = imageBitmap,
        contentDescription = "Bitmap preview",
        modifier = Modifier
            .aspectRatio(bitmap.width.toFloat() / bitmap.height)
    )
}

private fun Bitmap.toCustomBitmap(targetRegion: Rect): Bitmap? {
    val x = (targetRegion.left).coerceAtLeast(0)
    val y = targetRegion.top.coerceAtLeast(0)
    val width = targetRegion.width()
    val height = targetRegion.height()
    return runCatching {
        Bitmap.createBitmap(
            this,
            x, y,
            width, height,
        )
    }.getOrElse { null }
}