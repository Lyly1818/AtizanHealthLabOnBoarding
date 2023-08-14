package net.atizan.AtizanHealthLabOnBoarding


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import net.atizan.AtizanHealthLabOnBoarding.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding() {
    val items = OnBoardingData.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(
            onBackClick = {
                if (pageState.currentPage + 1 > 1) scope.launch {
                    pageState.scrollToPage(pageState.currentPage - 1)
                }
            },
            onSkipClick = {
                if (pageState.currentPage + 1 < items.size) scope.launch {
                    pageState.scrollToPage(items.size - 1)
                }
            }
        )
//Contain the sliders
        HorizontalPager(
            pageCount = items.size,
            state = pageState,
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) { page ->
            OnBoardingItem(items = items[page])
        }
//        BottomSection(size = items.size, index = pageState.currentPage) {
//            if (pageState.currentPage + 1 < items.size) scope.launch {
//                pageState.scrollToPage(pageState.currentPage + 1)
//            }
//        }


        NewBottomSection(pageState.currentPage)
    }
}


@Composable
fun TopSection(onBackClick: () -> Unit = {}, onSkipClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        // Back button
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "arrow back")
        }

//        // Skip Button
//        TextButton(
//            onClick = onSkipClick,
//            modifier = Modifier.align(Alignment.CenterEnd),
//            contentPadding = PaddingValues(0.dp)
//        ) {
//            Text(text = "Skip", color = MaterialTheme.colorScheme.onBackground)
//        }
    }
}

//@Composable
//fun BottomSection(size: Int, index: Int, onButtonClick: () -> Unit = {}) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp)
//    ) {
//        // Indicators
//        Indicators(size, index)
//
//
//        if (index == 2){
//
//        OutlinedButton(onClick = { /*TODO*/ }) {
//
//            Text(
//                text = "Commencons",
//                modifier = Modifier
//                    .padding(vertical = 8.dp, horizontal = 40.dp),
//                color = Color.Magenta
//            )
//
//        }
//        }else{
//
//            FloatingActionButton(
////            TODO Implement a callBack to move to next slide
//                onClick = { /* do something */ },
//                containerColor = androidx.compose.ui.graphics.Color.Black,
//                modifier = Modifier
//                    .align(Alignment.CenterEnd)
//                    .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
//            ) {
//                Icon(Icons.Outlined.KeyboardArrowRight,
//                    tint = androidx.compose.ui.graphics.Color.White,
//                    contentDescription = "Localized description")
//            }
//        }
//
//
//    }
//}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = (if (isSelected) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.White) as androidx.compose.ui.graphics.Color
            )
    ) {

    }
}

@Composable
fun OnBoardingItem(items: OnBoardingData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = "Image1",
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = stringResource(id = items.title),
            style = MaterialTheme.typography.headlineMedium,
            // fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = items.desc),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
            letterSpacing = 1.sp,
        )
    }
}


//Skip or Next
@Composable
fun CustomTextButton(text: String, modifier: Modifier, onButtonClick: () -> Unit, vararg image: Int){
    TextButton(
        onClick = {


    /*TODO*/ }
    ) {
        
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = Typography.bodyMedium,
            fontWeight = FontWeight.Medium)
        
    }
}

//@Preview
//@Composable
//fun SkipButtonPrev(){
//    SkipOrtNext(text = "Next", modifier = Modifier.background(Color.White), {}, R.drawable.logoverticaldark)
//}


@Composable
fun NewBottomSection(currentPager: Int){

    Row(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
        ,
        horizontalArrangement = if(currentPager != 2) Arrangement.SpaceBetween else Arrangement.Center
    ){

        if (currentPager == 2){
            OutlinedButton(
//                    TODO create call back to nagivate to
                onClick = { },
                shape = RoundedCornerShape(50), // = 40% percent
            ) {
                Text(
                    text = "Get Started",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }else{
//            SkipNextButton("Skip",Modifier.padding(start = 20.dp))
//            SkipNextButton("Next",Modifier.padding(end = 20.dp))

            CustomTextButton(text = "Skip",
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                onButtonClick = { /*TODO*/ } )


            CustomTextButton(text = "Next",
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                onButtonClick = { /*TODO*/ })
        }


    }


}

//CommonBackIconButton
@Composable
fun CommonBackIconButton(
    onButtonClick: () -> Unit
){
    IconButton(onClick = { /*TODO*/ }) {
        
    }
}