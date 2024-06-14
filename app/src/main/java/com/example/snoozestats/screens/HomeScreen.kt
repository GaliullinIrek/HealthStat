package com.example.snoozestats.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snoozestats.MainViewModel
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
) {
    var startSleeping by remember { mutableStateOf(mainViewModel.startSleeping.value) }
    var endSleeping by remember { mutableStateOf(mainViewModel.endSleeping.value) }
    val openDialogStart = remember { mutableStateOf(false) }
    val startTimeState = rememberTimePickerState(20, 10, true)
    val endTimeState = rememberTimePickerState(7, 20, true)
    val openDialogEnd = remember { mutableStateOf(false) }
    val result by remember{ mutableStateOf(mainViewModel.resultSleep)}
    val sleepToDouble by remember { mutableStateOf(mainViewModel.sleepDouble) }
    val buttonEnabled = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = result.value,
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 70.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                Column(
                    modifier = Modifier.clickable {
                        openDialogStart.value = true
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DarkMode,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = "Во сколько уснули?",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.clickable {
                    openDialogEnd.value = true
                },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.WbSunny,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "Во сколько проснулись?",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Как чувстуете себя?")
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            IconButton(onClick = {
                mainViewModel.sleepQuality.value = 1
            }) {
                Text(
                    text = "\uD83D\uDE34",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            IconButton(onClick = {
                mainViewModel.sleepQuality.value = 2
            }) {
                Text(
                    text = "\uD83E\uDD71",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            IconButton(onClick = {
                mainViewModel.sleepQuality.value = 3
            }) {
                Text(
                    text = "\uD83D\uDE14",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            IconButton(onClick = {
                mainViewModel.sleepQuality.value = 4
            }) {
                Text(
                    text = "\uD83D\uDC4C",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            IconButton(onClick = {
                mainViewModel.sleepQuality.value = 5
            }) {
                Text(
                    text = "\uD83D\uDE0A",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                mainViewModel.startSleeping.value = startSleeping
                mainViewModel.endSleeping.value = endSleeping

                mainViewModel.insetItems()
            },
            enabled = buttonEnabled.value
        ) {
            Text(text = "Сохранить")
        }
    }
    if (openDialogStart.value) {
        AlertDialog(
            onDismissRequest = { openDialogStart.value = false },
            title = { Text(text = "Во сколько вы легли?") },
            text = {
                Column {
                    TimePicker(state = startTimeState, layoutType = TimePickerLayoutType.Vertical)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        result.value =""
                        startSleeping = "${startTimeState.hour.toString().padStart(2, '0')}:${startTimeState.minute.toString().padStart(2, '0')}"
                        if(endSleeping != "") {
                            val formatter = DateTimeFormatter.ofPattern("H:mm")
                            val startTime = LocalTime.parse(startSleeping, formatter)
                            val endTime = LocalTime.parse(endSleeping, formatter)

                            val sleepDuration = Duration.between(endTime, startTime)

                            var hours: Long

                            if(startTimeState.hour > endTimeState.hour) {
                                hours = 24 - sleepDuration.toHours()
                            } else {
                                hours = sleepDuration.toHours()
                            }
                            var minutes = sleepDuration.toMinutes() % 60
                            if (minutes < 0) {
                                minutes*=-1
                            }

                            if(startTimeState.hour > endTimeState.hour) {
                                hours = 24 - sleepDuration.toHours()
                                sleepToDouble.value = hours.toDouble() + "0.$minutes".toDouble()
                                result.value = "$hours часов\n$minutes минут"
                            } else {
                                hours = sleepDuration.toHours()
                                if(hours < 0) {
                                    hours*=-1
                                }
                                sleepToDouble.value = hours.toDouble() + "0.$minutes".toDouble()
                                result.value = "$hours часов\n$minutes минут"
                            }
                            buttonEnabled.value = true
                        }
                        openDialogStart.value = false
                    }
                ) {
                    Text(text = "Сохранить")
                }
            }
        )
    }
    if (openDialogEnd.value) {
        AlertDialog(
            onDismissRequest = { openDialogEnd.value = false },
            title = { Text(text = "Во сколько вы проснулись?") },
            text = {
                Column {
                    TimePicker(state = endTimeState, layoutType = TimePickerLayoutType.Vertical)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        result.value =""
                        endSleeping = "${endTimeState.hour.toString().padStart(2, '0')}:${endTimeState.minute.toString().padStart(2, '0')}"
                        if(startSleeping != "") {
                            val formatter = DateTimeFormatter.ofPattern("H:mm")
                            val startTime = LocalTime.parse(startSleeping, formatter)
                            val endTime = LocalTime.parse(endSleeping, formatter)

                            val sleepDuration = Duration.between(endTime, startTime)
                            var hours: Long
                            var minutes = sleepDuration.toMinutes() % 60
                            if (minutes < 0) {
                                minutes*=-1
                            }

                            if(startTimeState.hour > endTimeState.hour) {
                                hours = 24 - sleepDuration.toHours()
                                sleepToDouble.value = hours.toDouble() + "0.$minutes".toDouble()
                                result.value = "$hours часов\n$minutes минут"
                            } else {
                                hours = sleepDuration.toHours()
                                if(hours < 0) {
                                    hours*=-1
                                }
                                sleepToDouble.value = hours.toDouble() + "0.$minutes".toDouble()
                                result.value = "$hours часов\n$minutes минут"
                            }
                            buttonEnabled.value = true
                        }
                        openDialogEnd.value = false
                    }
                ) {
                    Text(text = "Сохранить")
                }
            }
        )
    }
}
