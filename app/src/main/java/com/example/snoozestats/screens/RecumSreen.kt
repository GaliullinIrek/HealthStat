package com.example.snoozestats.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.snoozestats.R


@Composable
fun RecumScreen() {
    val recommendations = listOf<String>("Сохраняйте регулярный график сна", "Избегайте кофеин и алкоголь перед сном", "Отключите электронику за час до сна", "Скоро....")
    val recommendationImages = listOf<Painter>(
        painterResource(R.drawable.sleep_analysis_amico),
        painterResource(R.drawable.insomnia_bro),
        painterResource(R.drawable.no_connection_rafiki),
        painterResource(R.drawable.time_flies_rafiki)
    )
    val recommendationColor = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.secondaryContainer
    )
    val recommentationTextAlign = listOf(
        TextAlign.Start,
        TextAlign.Start,
        TextAlign.Start,
        TextAlign.Center
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Рекумендации",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(15.dp, 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Спите по \n7-10 часов",
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(R.drawable.sleeping_baby_rafiki),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            content = {
                itemsIndexed(recommendations) { key, text->
                    Box(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp)
                                .padding(5.dp)
                                .background(
                                    color = recommendationColor[key],
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(10.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = recommendations[key],
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = recommentationTextAlign[key],
                                modifier = Modifier.fillMaxWidth()
                            )
                            Image(painter = recommendationImages[key],
                                contentDescription = null,
                                modifier = Modifier.size(110.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}