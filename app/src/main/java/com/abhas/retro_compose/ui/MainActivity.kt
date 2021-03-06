package com.abhas.retro_compose.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.abhas.retro_compose.model.Memes
import com.abhas.retro_compose.model.MemesResponse
import com.abhas.retro_compose.repository.RetrofitBuilder
import com.abhas.retro_compose.ui.theme.RetroComposeTheme
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    val list = mutableStateListOf<Memes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitBuilder.getInstance()
        setContent {
            RetroComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxWidth(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Greeting(this@MainActivity::fetchMemes, this@MainActivity::fetchMemesWithCoroutines)
                        MemesList(list = list)
                    }
                }
            }
        }
    }

    private fun fetchMemesWithCoroutines() {
        list.clear()
        lifecycleScope.launch(Dispatchers.IO) {
            val memesResponse = RetrofitBuilder.getMemesResponse()
            if (memesResponse.isSuccessful) {
                list.addAll(memesResponse.body()?.data!!.memes)
            }
        }
    }

    private fun fetchMemes() {
        list.clear()
        val memes = RetrofitBuilder.getMemes()
        memes.enqueue(object : Callback<MemesResponse> {
            override fun onResponse(call: Call<MemesResponse>, response: Response<MemesResponse>) {
                response.let { response ->
                    response.body().let { body ->
                        list.addAll(body!!.let {
                            it?.data.memes
                        })
                    }
                }
            }

            override fun onFailure(call: Call<MemesResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MainActivity, "Failed: " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}

@Composable
fun MemesList(list: List<Memes>) {
    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(items = list) { meme ->
            Meme(meme)
        }
    }
}

@Composable
fun Meme(meme: Memes) {
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()) {
        Text(text = meme.name)
        CoilImage(
            imageModel = meme.url,
            modifier = Modifier.fillMaxWidth(),
            // shows a shimmering effect when loading an image.
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.Cyan,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),
            // shows an error text message when request failed.
            failure = {
                Text(text = "image request failed.")
            })
    }
}

@Composable
fun Greeting(fetchMemes:() -> Unit, fetchMemesWithCoroutines:() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
        Button(onClick = {
            fetchMemes()
                         }, modifier = Modifier.fillMaxWidth(1f)) {
            Text("Load Memes w/o Coroutines")
        }

        Button(onClick = {
            fetchMemesWithCoroutines()
                         }, modifier = Modifier.fillMaxWidth(1f)) {
            Text("Load Memes with Coroutines")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetroComposeTheme {
//        Greeting("Android")
    }
}