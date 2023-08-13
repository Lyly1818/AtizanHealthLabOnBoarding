package net.atizan.AtizanHealthLabOnBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  OnBoarding(
){
    val slides = OnBoardingData.getData()
    val scope = rememberCoroutineScope()
    val  pageState =  rememberPagerState()



}

