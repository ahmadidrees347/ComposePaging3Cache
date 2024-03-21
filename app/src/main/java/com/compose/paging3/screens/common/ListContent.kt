package com.compose.paging3.screens.common

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.compose.paging3.R
import com.compose.paging3.model.UnsplashImage
import com.compose.paging3.model.Urls
import com.compose.paging3.model.User
import com.compose.paging3.model.UserLinks
import com.compose.paging3.ui.theme.Shapes

@ExperimentalCoilApi
@Composable
fun ListContent(items: LazyPagingItems<UnsplashImage>) {
    Log.d("Error", items.loadState.toString())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(count = items.itemCount) { index ->
            val unsplashImage = items[index]
            unsplashImage?.let { UnsplashItem(unsplashImage = it) }
        }
    }
}


@ExperimentalCoilApi
@Composable
@Preview
fun UnsplashImagePreview() {
    UnsplashItem(
        unsplashImage = UnsplashImage(
            id = "1",
            urls = Urls(regular = ""),
            description = "ABC",
            likes = 100,
            user = User(username = "Stevdza-San", userLinks = UserLinks(html = ""))
        )
    )
}


@Composable
fun UnsplashItem(unsplashImage: UnsplashImage) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .clip(Shapes.large)
            .padding(8.dp),
        border = BorderStroke(0.dp, Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(unsplashImage.urls.regular)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .clip(Shapes.large)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                onError = {
                    Log.e("tag**", "onError - ${it.result.throwable}")
                }
            )
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = unsplashImage.description ?: unsplashImage.user.username.uppercase(), style = TextStyle(
                        fontWeight = FontWeight.SemiBold, fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f)
                )

                ClickableText(text = AnnotatedString(stringResource(R.string.click_me)),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                        .drawBehind {
                            val strokeWidthPx = 1.dp.toPx()
                            val verticalOffset = size.height - 2.sp.toPx()
                            drawLine(
                                color = Color.Blue,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }, style = TextStyle(
                        color = Color.Blue,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold, fontSize = 12.sp,
                    ),
                    onClick = {
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://unsplash.com/@${unsplashImage.user.username}?utm_source=DemoApp&utm_medium=referral")
                        )
                        startActivity(context, browserIntent, null)
                    })
            }
//            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}