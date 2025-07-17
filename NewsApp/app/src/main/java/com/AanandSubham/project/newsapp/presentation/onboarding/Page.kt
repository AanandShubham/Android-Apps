package com.AanandSubham.project.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.AanandSubham.project.newsapp.R

data class Page(
    val title:String,
    val description:String,
    @DrawableRes val image:Int
)


val pages = listOf(

    Page(
        title = "Stay Updated Always",
        description = "Get the latest breaking news, trending stories, and updates — all in one place. Never miss what matters most. Choose your interests and let us deliver news that matches your taste — from politics to tech, sports, entertainment, and more.",
        image = R.drawable.news_icon
    ),
    Page(
        title = "Local & Global Coverage",
        description = "Read stories from your city and across the globe. Stay informed about what's happening near you and worldwide. Enjoy a clean, distraction-free interface with night mode, offline reading, and adjustable fonts for your comfort.",
        image =R.drawable.news_icon2
    ),
    Page(
        title = "Your Daily Briefing",
        description = "Start your day with a smart summary of top headlines tailored for your routine. Quick, sharp, and easy to digest. Get notified instantly when big news breaks. Be the first to know, always.",
        image =R.drawable.deep_message
    )
)