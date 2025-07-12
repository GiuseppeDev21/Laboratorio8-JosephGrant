package com.avanisoam.businesscardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avanisoam.businesscardapp.ui.theme.BusinessCardAppTheme
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    private val myViewModel by viewModels<DataStoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    myViewModel.toggleSettings()
                                }
                            )
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by myViewModel.uiState.collectAsState()

                    if (uiState.showSettings) {
                        DataStoreScreen(viewModel = myViewModel)
                    } else {
                        val profileInfo = ProfileInfo(
                            name = uiState.name,
                            role = uiState.role,
                            year = uiState.year
                        )
                        val contactInfo = ContactInfo(
                            phone = uiState.phone,
                            handle = uiState.handle,
                            email = uiState.email,
                            showContactInfo = uiState.showContactInfo
                        )
                        BusinessCard(
                            profileInfo = profileInfo,
                            contactInfo = contactInfo,
                            showContactInfo = uiState.showContactInfo
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard(
    profileInfo: ProfileInfo,
    contactInfo: ContactInfo,
    showContactInfo: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        ProfileInfo(
            profileInfo = profileInfo,
            modifier = modifier.align(Alignment.Center)
        )
        ContactInfo(
            contactInfo = contactInfo,
            showContactInfo = showContactInfo,
            modifier = modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun ProfileInfo(profileInfo: ProfileInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.mi_foto_perfil),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .background(Color(0xFF073042))
                .size(height = 100.dp, width = 100.dp)
        )

        Text(
            text = profileInfo.name,
            fontSize = 40.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
        )

        Text(
            text = profileInfo.role,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFF006D3A),
        )

        Text(
            text = "${profileInfo.year} years of Experience",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun ContactInfo(contactInfo: ContactInfo,
                showContactInfo: Boolean,
                modifier: Modifier
) {
    if (showContactInfo) {
        Column(modifier = modifier.padding(bottom = 20.dp)) {
            ContactRow(text = contactInfo.phone, icon = Icons.Filled.Call)
            ContactRow(text = contactInfo.handle, icon = Icons.Filled.Share)
            ContactRow(text = contactInfo.email, icon = Icons.Filled.Email)
        }
    }
}


@Composable
fun ContactRow(
    text: String, icon: ImageVector, textBlur: Dp = 0.dp,
    useWeight : Boolean = false, useBasic : Boolean = false
) {
    Row {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(start = 10.dp),
            Color(0xFF006D3A)
        )

        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 18.dp, top = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardAppTheme {

    }
}