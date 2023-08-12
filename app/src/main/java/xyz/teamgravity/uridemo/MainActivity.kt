package xyz.teamgravity.uridemo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import xyz.teamgravity.uridemo.ui.theme.URIDemoTheme
import java.io.File

class MainActivity : ComponentActivity() {

    private companion object {
        const val TAG = "tupac"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resourceUri = Uri.parse("android.resource://$packageName/drawable/mcgregor")
        val resourceBytes = contentResolver.openInputStream(resourceUri)?.use { it.readBytes() }
        Log.d(TAG, "resourceUri: $resourceUri")

        val file = File(filesDir, "mcgregor.jpg")
        resourceBytes?.let(file::writeBytes)
        val fileUri = file.toUri()
        Log.d(TAG, "fileUri: $fileUri")

        val dataUri = Uri.parse("data:text/plain;charset=UTF-8,Tupac%20Shakur")
        Log.d(TAG, "dataUri: $dataUri")

        setContent {
            URIDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent(),
                            onResult = { contentUri ->
                                Log.d(TAG, "contentUri: $contentUri")
                            }
                        )

                        Button(
                            onClick = {
                                launcher.launch("image/*")
                            }
                        ) {
                            Text(text = stringResource(id = R.string.get_content_uri))
                        }
                    }
                }
            }
        }
    }
}