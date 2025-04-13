package com.jelly.ai.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jelly.ai.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(
    paddingValues: PaddingValues,
) {
    val pictureList = remember {
        mutableStateListOf(
            PictureItem("Nezha", "34K users", R.drawable.placeholder_fruit),
            PictureItem("Illustration", "364K users", R.drawable.placeholder_apple_juice),
            PictureItem("Fashion Model", "202K users", R.drawable.placeholder_chips),
            PictureItem("Comic Style", "116K users", R.drawable.placeholder_desserts),
            PictureItem("Film/Tv Character", "28K users", R.drawable.placeholder_gingerbread),
            PictureItem("3D Scene", "99K users", R.drawable.placeholder_grapes),
            PictureItem(
                "Ancient-style Beauty",
                "130K users",
                R.drawable.placeholder_ice_cream_sandwich
            ),
            PictureItem("Funny Comic", "76K users", R.drawable.placeholder_nuts)
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(end = paddingValues.calculateBottomPadding()),
                onClick = { /* TODO */ },
            ) {
                Text(text = "Creation", color = Color.White)
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding()),
            contentPadding = PaddingValues(8.dp),
            state = rememberLazyGridState(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = pictureList,
                key = { item -> item.title }
            ) { item ->
                PictureGridItem(item = item)
            }
        }
    }
}

data class PictureItem(
    val title: String,
    val usageCount: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun PictureGridItem(item: PictureItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 2.dp,
            color = Color.LightGray
        ) {
            AsyncImage(
                model = item.imageRes,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Text(
            text = item.usageCount,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}