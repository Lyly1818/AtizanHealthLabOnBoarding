package net.atizan.AtizanHealthLabOnBoarding
data class OnBoardingData(
    val image: Int,
    val title: Int,
    val desc: Int
){
    companion object {
        fun getData(): List<OnBoardingData>{
            return listOf(
                OnBoardingData(R.drawable.slidesmartphone, R.string.slide1_title, R.string.slide1_paragraphe),
                OnBoardingData(R.drawable.slidesmartphone, R.string.slide2_title, R.string.slide2_title),
                OnBoardingData(R.drawable.slidekournaling, R.string.slide3_title, R.string.slide3_paragraph))

//            )
        }
    }
}
