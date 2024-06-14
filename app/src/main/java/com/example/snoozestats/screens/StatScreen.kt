package com.example.snoozestats.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snoozestats.MainViewModel
import com.example.snoozestats.R
import com.example.snoozestats.data.HealthMonitoring
import com.example.snoozestats.data.Sleep

@Composable
fun StatScreen(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
) {
    val tabIndex = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Статистика",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        TabRow(
            selectedTabIndex = tabIndex.value,
            onTabSelected = { tabIndex.value = it }
        )
        when (tabIndex.value) {
            0 -> Tab1Content(mainViewModel)
            1 -> Tab2Content(mainViewModel)
        }
    }
}

@Composable
fun TabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Tab(
            text = { Text("Здоровье") },
            selected = selectedTabIndex == 0,
            onClick = { onTabSelected(0) }
        )
        Tab(
            text = { Text("Сон") },
            selected = selectedTabIndex == 1,
            onClick = { onTabSelected(1) }
        )
    }
}

@Composable
fun Tab2Content(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
) {
    val items by mainViewModel.lastSevenItems.collectAsState(initial = emptyList<Sleep>())
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
    ) {
        items(items) {it->
            val colors = listOf<Color>(Color.Red, Color(0xFFF2871D), Color.Yellow,Color(0xFFB3D435), Color.Green)
            val colorColumn = colors[it.emoji]
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${it.time}",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(
                    modifier = Modifier
                        .background(
                            color = colorColumn,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .height((it.time * 15).dp)
                        .width(50.dp)
                )
                Text(
                    text = it.date,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Spacer(modifier = Modifier
                    .height(2.dp)
                    .width(100.dp))
            }
        }
    }
}
@Composable
fun Tab1Content(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
) {
    val openDialog = mainViewModel.openDialog
    val healthItems by mainViewModel.lastSevenHealthsItems.collectAsState(initial = emptyList<HealthMonitoring>())
    val realWater = mainViewModel.realWater
    val height = mainViewModel.height
    val weight = mainViewModel.weight

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp, end = 25.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
            ) {
                items(healthItems) {healthItem->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.weight),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${healthItem.weight} кг"
                            )
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Default.Height,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${healthItem.height} см"
                            )
                        }
                        Row {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${healthItem.realWater} мл"
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .height((healthItem.realWater / 6).dp)
                                .heightIn(min = 20.dp, max = 300.dp)
                                .width(40.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(20.dp)
                                )
                        )

                        Text(text = healthItem.date)
                    }
                }
            }
        }
        IconButton(
            onClick = {
                openDialog.value = true
            },
            colors = IconButtonDefaults.filledIconButtonColors()
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Ведите данные") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    TextField(
                        value = realWater.value,
                        label = { Text(text = "Сколько выпили воды в мл:")},
                        placeholder = { Text(text = "120")},
                        onValueChange = { realWater.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = height.value,
                        label = { Text(text = "Ваш рост в см:")},
                        placeholder = { Text(text = "178")},
                        onValueChange = { height.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextField(
                        value = weight.value,
                        label = { Text(text = "Ваш вес в кг:")},
                        placeholder = { Text(text = "64.5")},
                        onValueChange = { weight.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mainViewModel.insertHealthItem()
                        openDialog.value = false
                    }
                ) {
                    Text(text = "Сохранить")
                }
            }
        )
    }
}

