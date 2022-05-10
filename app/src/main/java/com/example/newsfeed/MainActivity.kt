package com.example.newsfeed

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        rv_main.adapter = mAdapter
    }

    private fun fetchData(){
        val apiKey = "YOUR_API_KEY HERE"
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=$apiKey"
        val jsonObjectRequest =object : JsonObjectRequest(
            Request.Method.GET, url,null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray =ArrayList<News>()
                for (i in 0 until newsJsonArray.length()){
                    val jsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        jsonObject.getString("title"),
                        jsonObject.getString("author"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener{
                Toast.makeText(this,"Sorry we are facing some problems here.",Toast.LENGTH_SHORT).show()
            }

        )
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClick(items: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(items.url));
    }
}