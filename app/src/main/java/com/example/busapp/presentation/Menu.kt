package com.example.busapp.presentation

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.busapp.models.User
import com.google.firebase.auth.FirebaseUser

@Composable
fun DrawerContentMenu(
    name: String?,
    email: String?,
    photoUrl: Uri?,
    items: List<DrawerItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DrawerItem) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {

        UserInformationCard(name, email, photoUrl)
        Divider()
        items.forEach() {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(it)
                }
                .padding(16.dp),
                verticalAlignment = CenterVertically
            ) {
                Icon(imageVector = it.icon, contentDescription = it.text)
                Spacer(modifier = Modifier.width(32.dp))
                Text(it.text)
            }
        }
    }
}

@Composable
fun UserInformationCard(name: String?, email: String?, photoUrl: Uri?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        AsyncImage(model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Column {
            name?.let { Text(text = it) }
            email?.let { Text(text = it) }
        }
    }
}