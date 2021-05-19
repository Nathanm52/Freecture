package com.area.freecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.area.freecture.api.ApiRequests
import com.area.freecture.api.JsonParser
import com.area.freecture.listeners.ListItemClickListener
import com.area.freecture.listeners.ResponseListener
import com.area.freecture.model.ImageModel
import java.util.ArrayList

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    ResponseListener, ListItemClickListener {
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
        setContentView(R.layout.activity_search)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        val search = menu?.findItem(R.id.nav_search)
        val searchView = search?.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchData(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }

    private fun searchData(query: String) {
        Log.d("TAG", "test")
        println(query)
        initView()
        ApiRequests(this@SearchActivity, this).getPhotosByQuerryMethod(1, query)
    }

    private fun initView() {

        imageRecyclerView = findViewById(R.id.rv_image)
        nestedSV = findViewById(R.id.scrollView)
        loadBar = findViewById(R.id.loadBar)
        swipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout)

        layoutRecyclerView = GridLayoutManager(this@SearchActivity, 2)
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
        ApiRequests(this@SearchActivity, this).getApiRequestMethodArray(page)
    }

    override fun onSuccess(`object`: String) {
        loadMore = false
        imagesUrlModelList.addAll(JsonParser.json2ImageList(`object`))

        if (imagesUrlModelList.size > 0) {
            photoListAdapter = PhotoListAdapter(this@SearchActivity, imagesUrlModelList, this)
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