package com.example.fetchjsonplaceholder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}

private val apiService: ApiService by lazy {
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}

suspend fun fetchPosts(): List<Post> {
    return try {
        withContext(Dispatchers.IO) {
            apiService.getPosts()
        }
    } catch (e: Exception) {
        emptyList()
    }
}

@Composable
fun PostsScreen() {
    var posts by remember { mutableStateOf<List<Post>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            posts = fetchPosts()
        } catch (e: Exception) {
            isError = true
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Posts") })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                isLoading -> LoadingShimmer()
                isError -> ErrorView()
                posts.isNullOrEmpty() -> NoPostsView()
                else -> PostsList(posts!!)
            }
        }
    }
}

@Composable
fun LoadingShimmer() {
    LazyColumn {
        items(10) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Loading...")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Fetching data...")
                }
            }
        }
    }
}

@Composable
fun ErrorView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Failed to load posts. Please try again.", color = MaterialTheme.colors.error)
    }
}

@Composable
fun NoPostsView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("No posts available.")
    }
}

@Composable
fun PostsList(posts: List<Post>) {
    LazyColumn {
        items(posts) { post ->
            PostItem(post)
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.body, style = MaterialTheme.typography.body2)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PostsScreen()
            }
        }
    }
}