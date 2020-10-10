package com.moondev.rxjson

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var viewModel : VM?  = null

    private var adapter  : AdapterRepo? = null


    companion object{
        const val  URL = "https://api.github.com/repositories?since=364"
    }

    /**
     *
     * UI update
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =  ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.application).create(VM::class.java)

        adapter = AdapterRepo(this)
        recycler.layoutManager =  LinearLayoutManager(this)


        viewModel?.listOfRepo?.observe(this, { list ->
            adapter?.update(list)
            recycler.adapter = adapter
        })


        viewModel?.fetchListOfData()

    }


}

/**
 * Item for RecyclerView setup
 */

class AdapterRepo(private val context : Context) : RecyclerView.Adapter<AdapterRepo.VH>() {


    private var listOfRepo  = listOf<Repo>()


    class VH(itemView : View)  : RecyclerView.ViewHolder(itemView){
        var title  : TextView?  =  null
        var description : TextView? = null

        init {
            title =  itemView.findViewById(R.id.title)
            description =  itemView.findViewById(R.id.description)
        }

        fun bind(titleValue : String? ,  descriptionValue : String?){
            title?.text =  titleValue
            description?.text =  descriptionValue

        }

    }

    fun update(list: List<Repo>){
        listOfRepo = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v =  LayoutInflater.from(context).inflate(R.layout.repo_item,parent,false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val repo =  listOfRepo[position]

        holder.bind(repo.name, repo.description)
    }



    override fun getItemCount(): Int = listOfRepo.size
}