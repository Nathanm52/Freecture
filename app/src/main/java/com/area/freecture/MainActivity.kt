package com.area.freecture

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.area.freecture.api.ApiRequests
import com.area.freecture.api.JsonParser
import com.area.freecture.model.ImageModel
import com.area.freecture.listeners.ListItemClickListener
import com.area.freecture.listeners.ResponseListener
import java.util.*
import android.util.Log
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ResponseListener, ListItemClickListener {
    private var loadMore = false
    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var nestedSV: NestedScrollView
    private var loadBar: ProgressBar? = null
    private var layoutRecyclerView: GridLayoutManager? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    var pageNumber = 1
    private var imagesUrlModelList: MutableList<ImageModel> = ArrayList<ImageModel>()
    private var photoListAdapter: PhotoListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchButton = findViewById<FloatingActionButton>(R.id.search_button)

        searchButton.setOnClickListener {
            val searchIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchIntent)
        }
        initView()
        loadMoreData(1)
    }

    private fun initView() {

        imageRecyclerView = findViewById(R.id.rv_image)
        nestedSV = findViewById(R.id.scrollView)
        loadBar = findViewById(R.id.loadBar)
        swipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout)

        layoutRecyclerView = GridLayoutManager(this@MainActivity, 2)
        imageRecyclerView.setHasFixedSize(true)
        imageRecyclerView.isNestedScrollingEnabled = false
        imageRecyclerView.setItemViewCacheSize(10)
        imageRecyclerView.layoutManager = layoutRecyclerView

        swipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            loadBar!!.visibility = View.VISIBLE
            loadMoreData(pageNumber)

            swipeRefreshLayout!!.isRefreshing = false
        })

        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!loadMore) {
                    loadBar!!.visibility = View.VISIBLE
                    loadMore = true
                    pageNumber += 1
                    loadMoreData(pageNumber)
                }
            }
        })
    }

    private fun loadMoreData(page: Int) {
        ApiRequests(this@MainActivity, this).getApiRequestMethodArray(page)
    }

    override fun onSuccess(`object`: String) {
        loadMore = false
        imagesUrlModelList.addAll(JsonParser.json2ImageList(`object`))

        if (imagesUrlModelList.size > 0) {
            photoListAdapter = PhotoListAdapter(this@MainActivity, imagesUrlModelList, this)
            imageRecyclerView.adapter = photoListAdapter
            loadMore = false
        } else {
            loadBar!!.visibility = View.GONE
            loadMore = true
        }

        loadBar!!.visibility = View.GONE
    }

    override fun onError(message: String) {
        loadMore = false
        loadBar!!.visibility = View.GONE
    }

    override fun onListItemClick(position: Int, action: String) {
        val imagesUrlModel = imagesUrlModelList[position]
        // println("Test click =", imagesUrlModel)
        Log.d("TAG", "message")
        /* startActivity(
            Intent(this, ImageDetail_Activity::class.java)
                .putExtra("list", imagesUrlModel as Serializable)
                .putExtra("pos", position)
        ) */
    }
}