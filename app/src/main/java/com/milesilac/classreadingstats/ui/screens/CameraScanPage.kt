package com.milesilac.classreadingstats.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.RectF
import android.view.ScaleGestureDetector
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.milesilac.classreadingstats.model.ClassSection
import com.milesilac.classreadingstats.model.initClassSection
import com.milesilac.classreadingstats.model.toSectionString
import com.milesilac.classreadingstats.repository.StudentWithScannedGrade
import com.milesilac.classreadingstats.ui.components.ConfirmAddDeleteListBottomSheetLayout
import com.milesilac.classreadingstats.ui.components.EditStudentNameDialogLayout
import com.milesilac.classreadingstats.ui.components.OCRTargetRegion
import com.milesilac.classreadingstats.ui.theme.ProjectColors
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import kotlin.math.abs
import kotlin.math.hypot
import androidx.core.graphics.createBitmap


@SuppressLint("ClickableViewAccessibility")
@Composable
fun CameraScanPage(
    scanEvent: CameraScanEvent = CameraScanEvent.None,
    section: ClassSection = initClassSection(),
    detectedList: List<StudentWithScannedGrade> = listOf(),
    onScan: (Set<Pair<String,String>>) -> Unit = {},
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onUpdateName: (StudentDetailEditEvent) -> Unit = {},
    onUpdateGrade: (UpdateTempClassSheetsForInputGrades) -> Unit = {},
    onBackClick: () -> Unit = {},
    onVisible: () -> Unit = {},
) {
    onVisible()
    BackHandler { onBackClick() }
    val isPreview = LocalInspectionMode.current

    //TODO camera permission request

    var currentMLKitText by remember { mutableStateOf("") }
    var currentList by remember { mutableStateOf<List<StudentWithScannedGrade>>(listOf()) }
    val currentListFirsts = currentList.map { it.studentName }
    if (detectedList.isNotEmpty()) {
        currentList = currentList.toMutableList().apply {
            detectedList.forEach { newEntry ->
                when {
                    newEntry.studentName !in currentListFirsts -> {
                        add(newEntry)
                    }
                    else -> {
                        if (newEntry.inputGrade.all { it.isDigit() }) {
                            val lastIndex = this.indexOfLast { it.studentName == newEntry.studentName }
                            if (lastIndex != -1) {
                                this[lastIndex] = newEntry
                            }
                        }
                    }
                }
            }
        }.sortedBy { it.orderId }
    }

    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }

    var targetRegion by remember { mutableStateOf(RectF(0F, 0F, 0F, 0F)) }
    var testBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var testTextLines by remember { mutableStateOf(listOf<Text.Line>()) }

    if (isPreview.not()) {
        DisposableEffect(Unit) {
            val textRecognizer = TextRecognition
                .getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val cameraInfo = cameraProvider.getCameraInfo(cameraSelector)
            val previewBuilder = androidx.camera.core.Preview.Builder().apply {
                if (androidx.camera.core.Preview.getPreviewCapabilities(cameraInfo).isStabilizationSupported) {
                    setPreviewStabilizationEnabled(true)
                }
            }
            val preview = previewBuilder.build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(context)
            ) { image ->
                previewView.bitmap?.toCustomBitmap(
                    when (scanEvent) {
                        is CameraScanEvent.EditInputGrades -> null
                        else -> targetRegion.toRect()
                    }
                )?.let { bitmap ->
                    val grayBM = preprocessImage(bitmap)
                    val inputImage = InputImage.fromBitmap(grayBM, 0)
                    textRecognizer.process(inputImage)
                        .addOnSuccessListener { visionText ->
                            testBitmap = bitmap
                            val centerX = inputImage.width / 2
                            val centerY = inputImage.height / 2
                            println("classInits lookup ${visionText.text}")

                            when (scanEvent) {
                                CameraScanEvent.AddStudentName -> {
                                    // Find text block closest to the center
                                    val centeredBlock = visionText.textBlocks.minByOrNull { block ->
                                        val box = block.boundingBox
                                        if (box != null) {
                                            val blockCenterX = box.centerX()
                                            val blockCenterY = box.centerY()
                                            hypot(
                                                (centerX - blockCenterX).toDouble(),
                                                (centerY - blockCenterY).toDouble()
                                            )
                                        } else Double.MIN_VALUE
                                    }
                                    centeredBlock?.let { block ->
                                        val centeredLine = block.lines.minByOrNull { line ->
                                            val box = line.boundingBox
                                            if (box != null) {
                                                val blockCenterX = box.centerX()
                                                val blockCenterY = box.centerY()
                                                hypot(
                                                    (centerX - blockCenterX).toDouble(),
                                                    (centerY - blockCenterY).toDouble()
                                                )
                                            } else Double.MIN_VALUE
                                        }
                                        centeredLine?.let { line ->
                                            currentMLKitText = line.text
                                        }
                                    }
                                }
                                is CameraScanEvent.EditInputGrades -> {
                                    val studentList = mutableSetOf<Pair<String,String>>()
                                    studentList.apply {
                                        addAll(
                                            getTablePairsFromLines(
                                                text = visionText,
                                                onVisualize = { testTextLines = it }
                                            )
                                        )
                                    }
                                    onScan(studentList)
                                }
                                CameraScanEvent.None -> {
                                    currentMLKitText = visionText.text
                                }
                            }
                        }
                        .addOnFailureListener {}
                        .addOnCompleteListener { image.close() }
                } ?: run { image.close() }
            }

            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)

            val scaleGestureDetector = ScaleGestureDetector(
                context,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        val currentZoomRatio = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1f
                        val scaleFactor = detector.scaleFactor
                        val newZoom = (currentZoomRatio * scaleFactor).coerceIn(
                            camera.cameraInfo.zoomState.value?.minZoomRatio ?: 1f,
                            camera.cameraInfo.zoomState.value?.maxZoomRatio ?: 5f
                        )
                        camera.cameraControl.setZoomRatio(newZoom)
                        return true
                    }
                })

            previewView.setOnTouchListener { _, event ->
                scaleGestureDetector.onTouchEvent(event)
                true
            }

            onDispose {cameraProvider.unbindAll() }
        }
    }

    Column(
        modifier = Modifier
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .weight(1F)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx -> previewView }
            )
            // Overlay drawing bounding boxes
            Canvas(modifier = Modifier.fillMaxSize()) {
                val scaleX = size.width / previewView.width.toFloat()
                val scaleY = size.height / previewView.height.toFloat()

                testTextLines.forEach { block ->
                    block.boundingBox?.let { rect ->
                        val left = rect.left * scaleX
                        val top = rect.top * scaleY
                        val right = rect.right * scaleX
                        val bottom = rect.bottom * scaleY

                        drawRect(
                            color = ProjectColors.OffRed4,
                            topLeft = Offset(left, top),
                            size = Size(right - left, bottom - top),
                            style = Stroke(width = 2F)
                        )
                    }
                }
            }
            if (scanEvent !is CameraScanEvent.EditInputGrades) {
                OCRTargetRegion(
                    onBoxChange = { newTargetRegion ->
                        targetRegion = newTargetRegion
                    }
                )
            }
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
                    text = "Suggested: ${detectedList.getOrNull(0)?.studentName}",
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
            CameraScanEvent.AddStudentName -> {
                val names = runCatching {
                    currentMLKitText.split(",", limit = 2)
                }.getOrElse { listOf("","") }
                val lastName = names.getOrNull(0) ?: ""
                val firstNameEtc = names.getOrNull(1) ?: ""
                EditStudentNameDialogLayout(
                    isScanMode = true,
                    lastName = lastName,
                    firstNameEtc = firstNameEtc,
                    onOkayClick = {
                        onUpdateName(StudentDetailEditEvent.EventName(studentName = it))
                    },
                    onBackClick = { onBackClick() }
                )
            }
            is CameraScanEvent.EditInputGrades -> {
                val mappedList = currentList.map {
                    Pair("${it.orderId} ${it.studentName}", it.inputGrade)
                }
                ConfirmAddDeleteListBottomSheetLayout(
                    sectionString = section.toSectionString(isSpaced = true),
                    list = mappedList,
                    onConfirmClick = {
                        when (scanEvent) {
                            is CameraScanEvent.EditInputGrades.InputGST -> {
                                val studentsWithGSTScore = currentList.map {
                                    val gstScore = runCatching { it.inputGrade.toDouble() }.getOrElse { 0.0 }
                                    StudentWithGSTScore(
                                        studentPersistenceId = it.studentPersistenceId,
                                        gstScore = gstScore
                                    )
                                }
                                onUpdateGrade(
                                    UpdateTempClassSheetsForInputGrades.EventGST(
                                        sectionPersistenceId = section.persistenceId,
                                        studentInputs = studentsWithGSTScore,
                                    )
                                )
                            }
                            is CameraScanEvent.EditInputGrades.InputORTotalWords -> {
                                val studentsWithORTotalWords = currentList.map {
                                    val totalNumberOfWordsInSelection = runCatching { it.inputGrade.toDouble() }.getOrElse { -1.0 }
                                    StudentWithNewORTotalWords(
                                        studentPersistenceId = it.studentPersistenceId,
                                        hasPostTest = when {
                                            scanEvent.hasPostTest == null -> it.hasPostTest
                                            else -> scanEvent.hasPostTest
                                        },
                                        isPostTest = scanEvent.testEditType == TestEditType.POSTTEST,
                                        orTotalWords = totalNumberOfWordsInSelection
                                    )
                                }
                                onUpdateGrade(
                                    UpdateTempClassSheetsForInputGrades.EventORTotalWords(
                                        sectionPersistenceId = section.persistenceId,
                                        studentInputs = studentsWithORTotalWords
                                    )
                                )
                            }
                            is CameraScanEvent.EditInputGrades.InputORTotalMiscues -> {
                                val studentsWithORMiscues = currentList.map {
                                    val numberOfMiscues = runCatching { it.inputGrade.toDouble() }.getOrElse { -1.0 }
                                    StudentWithNewORMiscues(
                                        studentPersistenceId = it.studentPersistenceId,
                                        hasPostTest = when {
                                            scanEvent.hasPostTest == null -> it.hasPostTest
                                            else -> scanEvent.hasPostTest
                                        },
                                        isPostTest = scanEvent.testEditType == TestEditType.POSTTEST,
                                        orMiscues = numberOfMiscues
                                    )
                                }
                                onUpdateGrade(
                                    UpdateTempClassSheetsForInputGrades.EventORMiscues(
                                        sectionPersistenceId = section.persistenceId,
                                        studentInputs = studentsWithORMiscues
                                    )
                                )
                            }
                            is CameraScanEvent.EditInputGrades.InputRCInputPercentage -> {
                                val studentsWithRCInputPercentage = currentList.map {
                                    val inputPercentage = runCatching { it.inputGrade.toDouble() }.getOrElse { -1.0 }
                                    StudentWithNewRCInputPercentage(
                                        studentPersistenceId = it.studentPersistenceId,
                                        hasPostTest = when {
                                            scanEvent.hasPostTest == null -> it.hasPostTest
                                            else -> scanEvent.hasPostTest
                                        },
                                        isPostTest = scanEvent.testEditType == TestEditType.POSTTEST,
                                        rcPercentage = inputPercentage
                                    )
                                }
                                onUpdateGrade(
                                    UpdateTempClassSheetsForInputGrades.EventRCInputPercentage(
                                        sectionPersistenceId = section.persistenceId,
                                        studentInputs = studentsWithRCInputPercentage
                                    )
                                )
                            }
                        }
                    },
                    onBackClick = { onBackClick() }
                )
            }
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

private fun preprocessImage(source: Bitmap?): Bitmap {
    val rgba = Mat()
    Utils.bitmapToMat(source, rgba)

    val gray = Mat()
    Imgproc.cvtColor(rgba, gray, Imgproc.COLOR_RGBA2GRAY)
    Imgproc.threshold(gray, gray, 0.0, 255.0, Imgproc.THRESH_BINARY or Imgproc.THRESH_OTSU)

    val result = createBitmap(gray.cols(), gray.rows())
    Utils.matToBitmap(gray, result)

    rgba.release()
    gray.release()

    return result
}

//fun getTablePairs(text: Text, rowTolerance: Int = 8): List<Pair<String, String>> {
//    val blocks = text.textBlocks
//    val rows = mutableListOf<MutableList<Text.TextBlock>>()
//
//    // Step 1: Group into rows
//    for (block in blocks) {
//        val blockCenterY = ((block.boundingBox?.top ?: 0) + (block.boundingBox?.bottom ?: 0)) / 2
//        var addedToRow = false
//
//        for (row in rows) {
//            val rowCenterY = ((row[0].boundingBox?.top ?: 0) + (row[0].boundingBox?.bottom ?: 0)) / 2
//            if (abs(blockCenterY - rowCenterY) <= rowTolerance) {
//                row.add(block)
//                addedToRow = true
//                break
//            }
//        }
//
//        if (!addedToRow) {
//            rows.add(mutableListOf(block))
//        }
//    }
//
//    val resultPairs = mutableListOf<Pair<String, String>>()
//
//    // Step 2: Sort each row left to right
//    for (row in rows) {
//        val sortedRow = row.sortedBy { it.boundingBox?.left ?: 0 }
//
//        // Step 3: Pair each block with the one to its right
//        for (i in 0 until sortedRow.size - 1) {
//            val current = sortedRow[i]
//            val right = sortedRow[i + 1]
//            resultPairs.add(Pair(current.text, right.text))
//        }
//    }
//
//    return resultPairs
//}

fun getTablePairsFromLines(
    text: Text, rowTolerance: Int = 8, onVisualize: (List<Text.Line>) -> Unit = {}
): List<Pair<String, String>> {
    val lines = text.textBlocks.flatMap { it.lines }
    onVisualize(lines)

    val rows = mutableListOf<MutableList<Text.Line>>()

    // Step 1: Group lines into rows using Y-center alignment
    for (line in lines) {
        val lineCenterY = ((line.boundingBox?.top ?: 0) + (line.boundingBox?.bottom ?: 0)) / 2
        var addedToRow = false

        for (row in rows) {
            val rowCenterY = ((row[0].boundingBox?.top ?: 0) + (row[0].boundingBox?.bottom ?: 0)) / 2
            if (abs(lineCenterY - rowCenterY) <= rowTolerance) {
                row.add(line)
                addedToRow = true
                break
            }
        }

        if (!addedToRow) {
            rows.add(mutableListOf(line))
        }
    }

    val resultPairs = mutableListOf<Pair<String, String>>()

    // Step 2: Sort lines within each row by horizontal position
    for (row in rows) {
        val sortedRow = row.sortedBy { it.boundingBox?.left ?: 0 }

        // Step 3: Pair each line with the next one in the row
        for (i in 0 until sortedRow.size - 1) {
            val current = sortedRow[i]
            val right = sortedRow[i + 1]
            resultPairs.add(Pair(current.text, right.text))
        }
    }

    return resultPairs
}

@Preview
@Composable
fun CameraScanPagePreview() {
    CameraScanPage()
}

sealed class CameraScanEvent {
    data object AddStudentName : CameraScanEvent()
    sealed class EditInputGrades : CameraScanEvent() {
        data object InputGST : EditInputGrades()
        data class InputORTotalWords(
            val testEditType: TestEditType,
            val hasPostTest: Boolean?
        ) : EditInputGrades()
        data class InputORTotalMiscues(
            val testEditType: TestEditType,
            val hasPostTest: Boolean?
        ) : EditInputGrades()
        data class InputRCInputPercentage(
            val testEditType: TestEditType,
            val hasPostTest: Boolean?
        ) : EditInputGrades()
    }
    data object None : CameraScanEvent()
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

private fun Bitmap.toCustomBitmap(targetRegion: Rect?): Bitmap? {
    var x: Int
    var y: Int
    var width: Int
    var height: Int
    targetRegion?.let {
        x = (it.left).coerceAtLeast(0)
        y = it.top.coerceAtLeast(0)
        width = it.width()
        height = it.height()
    } ?: run {
        x = 0
        y = 0
        width = this.width
        height = this.height
    }
    return runCatching {
        Bitmap.createBitmap(
            this,
            x, y,
            width, height,
        )
    }.getOrElse { null }
}