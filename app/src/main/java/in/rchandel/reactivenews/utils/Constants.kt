package `in`.rchandel.reactivenews.utils

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"

    //should not be kept here
    //should be in build files or resources
    const val API_KEY = "dedce20e167b446bbc0dfb96d0b573b7"

    const val country = "in"

    val categoryList = arrayListOf<String>("business", "entertainment", "general","health", "science",
        "sports", "technology")
}