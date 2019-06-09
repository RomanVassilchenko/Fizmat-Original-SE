package com.example.newsapp

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.newsapp.com.example.newsapp.createRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.Exception
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putString("param", "value")
            val f = MainFragment()
            f.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.fragment_place, f).commitAllowingStateLoss()

        }
    }

    fun showArticle(url: String) {

        val bundle = Bundle()
        bundle.putString("url", url)
        val f = SecondFragment()
        f.arguments = bundle

        val frame2 = findViewById<View>(R.id.fragment_place2)
        if (frame2 != null) {
            frame2.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.fragment_place2, f).commitAllowingStateLoss()
        } else
            supportFragmentManager.beginTransaction().add(R.id.fragment_place, f).addToBackStack("main")
                .commitAllowingStateLoss()
    }
}


class FeedAPI(
    val items: ArrayList<FeedItemAPI>
)

class FeedItemAPI(
    val title: String,
    val link: String,
    val thumbnail: String,
    val description: String
)

open class Feed(
    var items: RealmList<FeedItem> = RealmList<FeedItem>()
) : RealmObject()

open class FeedItem(
    var title: String = "",
    var link: String = "",
    var thumbnail: String = "",
    var description: String = ""
) : RealmObject()


class RecAdapter(val items: RealmList<FeedItem>) : RecyclerView.Adapter<RecHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.list_item, parent, false)
        return RecHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item = items[position]!!
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}

class RecHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: FeedItem) {
        val vTitle = itemView.findViewById<TextView>(R.id.item_title)
        val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
        val vThumb = itemView.findViewById<ImageView>(R.id.item_thumb)
        vTitle.text = item.title

        //<div>
        //          <img src="https://teletype.in/files/d4/d4692bd6-75e8-4189-ab10-1cab1e89ba77.png"><div>14 января 2019 года во дворце студентов Жолдасбекова города Алматы прошла торжественная церемония закрытия XV Международной Жаутыковской...</div>
        //        </div>

        vDesc.text = Html.fromHtml((item.description).removeRange(16, 96))

        try {
            Picasso.with(vThumb.context).load(item.thumbnail).into(vThumb)
            Log.e("thumbnail", "success")
        } catch (e: Exception) {
            Log.e("thumbnail", e.toString())
        }

        itemView.setOnClickListener {
            (vThumb.context as MainActivity).showArticle(item.link)
        }
    }
}
