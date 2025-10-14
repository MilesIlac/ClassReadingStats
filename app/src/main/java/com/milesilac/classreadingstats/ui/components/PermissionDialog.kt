package com.milesilac.classreadingstats.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.milesilac.classreadingstats.ui.theme.ProjectColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreparePermissionDialog(
    event: RequestPermissionEvent = RequestPermissionEvent.Unknown(),
    setHasPermission: (Boolean) -> Unit = {},
) {
    val context = LocalContext.current
    val permissionString = when (event) {
        is RequestPermissionEvent.Camera -> "${event.prefix} Permission"
        is RequestPermissionEvent.Unknown -> "${event.prefix} Permission"
        is RequestPermissionEvent.Write -> "${event.prefix} Permission"
    }
    val permissionRequest = when (event) {
        is RequestPermissionEvent.Camera -> Manifest.permission.CAMERA
        is RequestPermissionEvent.Unknown -> ""
        is RequestPermissionEvent.Write -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    if (ContextCompat.checkSelfPermission(context, permissionRequest) == PackageManager.PERMISSION_GRANTED) {
        setHasPermission(true)
        return
    }

    var showDialog by remember { mutableStateOf(true) }
    var isDeniedDialog by rememberSaveable { mutableStateOf(false) }

    // Create launcher to request one permission asynchronously
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        setHasPermission(isGranted)
        if (isGranted) {
            Toast.makeText(context, "$permissionString Granted", Toast.LENGTH_SHORT).show()
        } else {
            isDeniedDialog = true
            showDialog = true
            Toast.makeText(context, "$permissionString Denied", Toast.LENGTH_SHORT).show()
        }
    }

    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                showDialog = false
            }
        ) {
            PermissionDialogLayout(
                isDeniedDialog = isDeniedDialog,
                permissionString = permissionString,
                onRequestPermission = {
                    showDialog = false
                    permissionLauncher.launch(permissionRequest)
                },
                onOpenAppSettings = {
                    showDialog = false
                    openAppSettings(context = context)
                },
                onBackClick = { showDialog = false }
            )
        }
    }
}

@Composable
fun PermissionDialogLayout(
    isDeniedDialog: Boolean = false,
    permissionString: String = "Camera Permission",
    onRequestPermission: () -> Unit = {},
    onOpenAppSettings: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(
                color = ProjectColors.OffRed1,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val baseMessage = "$permissionString not granted."
        val message = if (isDeniedDialog) {
            "$baseMessage\nIf system auto-denied permission request (i.e. no prompts/dialogs to ask for permission),\nplease open App Settings, go to Permissions\nand manually allow the permission."
        } else baseMessage
        Text(
            text = message,
            color = ProjectColors.OffWhite4,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = {
                    if (isDeniedDialog) {
                        onOpenAppSettings()
                    } else onRequestPermission()
                },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffRed4,
                    contentColor = ProjectColors.OffWhite4,
                    disabledContainerColor = ProjectColors.OffOrange3,
                    disabledContentColor = ProjectColors.OffGreen4
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4
                ),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when {
                            isDeniedDialog -> "Open App Settings"
                            else -> "Request $permissionString"
                        },
                        modifier = Modifier,
                    )
                }
            }
            OutlinedButton(
                onClick = { onBackClick() },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = ProjectColors.OffRed4,
                    contentColor = ProjectColors.OffWhite4,
                    disabledContainerColor = ProjectColors.OffOrange3,
                    disabledContentColor = ProjectColors.OffGreen4
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = ProjectColors.OffWhite4
                ),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Back",
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PermissionDialogLayoutPreview() {
    PermissionDialogLayout()
}

@Preview
@Composable
fun PermissionDeniedDialogLayoutPreview() {
    PermissionDialogLayout(isDeniedDialog = true)
}

sealed class RequestPermissionEvent {
    data class Camera(val prefix: String = "Camera") : RequestPermissionEvent()
    data class Write(val prefix: String = "Write") : RequestPermissionEvent()
    data class Unknown(val prefix: String = "Unknown") : RequestPermissionEvent()
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}