package com.example.snoozestats.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.snoozestats.MainViewModel
import com.example.snoozestats.R
import com.example.snoozestats.data.MainDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
    context: Context
) {
    val bd = Room.databaseBuilder(
        context,
        MainDatabase::class.java,
        "main.db"
    ).build()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Версия: 1.1.4")
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Политика Политика конфиденциальности",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {

                },
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Стереть данные с устройства",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .clickable {
                    val dao2 = bd.dao

                    CoroutineScope(Dispatchers.IO).launch {
                        dao2.deleteInfoTable()
                        dao2.deleteHealthMonitoringTable()
                    }
                },
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = TextDecoration.Underline
        )
    }
}