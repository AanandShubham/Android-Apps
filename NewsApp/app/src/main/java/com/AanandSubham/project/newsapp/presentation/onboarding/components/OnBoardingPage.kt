package com.AanandSubham.project.newsapp.presentation.onboarding.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.AanandSubham.project.newsapp.R
import com.AanandSubham.project.newsapp.presentation.Dimens.MediumPadding1
import com.AanandSubham.project.newsapp.presentation.Dimens.MediumPadding2
import com.AanandSubham.project.newsapp.presentation.onboarding.Page
import com.AanandSubham.project.newsapp.presentation.onboarding.pages
import com.AanandSubham.project.newsapp.ui.theme.NewsAppTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        Text(
            text = page.title,
            modifier = Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(R.color.display_small)
        )

        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
            color = colorResource(R.color.body_medium),
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun OnBordPreview() {
    NewsAppTheme {
        OnBoardingPage(
            page = pages[0]
        )
    }
}