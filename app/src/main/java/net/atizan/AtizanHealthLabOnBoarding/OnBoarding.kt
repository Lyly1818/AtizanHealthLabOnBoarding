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
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
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

    Column(
        modifier = Modifier.fillMaxWidth()) {
        TopSection(
//    TODO   Need to change my approach use back button a reusable component
//            TODO need to make skip disappearch on the last slide
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
        
//        TODO Implement Indicator
         Row (
             Modifier
                 .height(20.dp)
                 .fillMaxWidth()
                 .align(Alignment.CenterHorizontally),
             horizontalArrangement = Arrangement.Center
         ){
             repeat(3){ iteration ->
                 val color = if(pageState.currentPage == iteration){
                      Color.DarkGray
                 }else{
                     Color.LightGray
                 }
                 Box(
                     modifier = Modifier
                         .padding(1.dp)
                         .clip(CircleShape)
                         .background(color)
                         .size(10.dp)
                        )
             }
             
         }

        NewBottomSection(pageState.currentPage,
            onBackClick = {
                if (pageState.currentPage + 1 > 1) scope.launch {
                    pageState.scrollToPage(pageState.currentPage - 1)
                }
            },
            onNextClick = {

            })

        Spacer(modifier = Modifier.height(15.dp))
    }
}


@Composable
fun TopSection(onBackClick: () -> Unit = {}, onSkipClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement =  Arrangement.SpaceBetween
    ) {
        // Back button
        IconButton(onClick = onBackClick, modifier = Modifier
            .align(Alignment.CenterVertically)) {
            Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "arrow back")
        }

        CustomTextButton(text = "Skip",
            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
            onButtonClick = { /*TODO*/ } )


    }
}


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
//        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = "Image1",
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))

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
            modifier = Modifier.padding(5.dp),
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

@Composable
fun NewBottomSection(currentPager: Int, onBackClick: () -> Unit, onNextClick: () -> Unit){

    Row(
        modifier = Modifier
//            .padding(bottom = 20.dp)
            .fillMaxWidth()
        ,
        horizontalArrangement = if(currentPager != 2) Arrangement.SpaceBetween else Arrangement.Center
    ){

        if (currentPager == 2){
            Button(
//                    TODO create call back to nagivate to
                onClick = { },
                shape = RoundedCornerShape(50), // = 40% percent
            ) {
                Text(
                    text = "Get Started",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }else{
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                .align(Alignment.CenterVertically)) {
                Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "arrow back")
            }


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