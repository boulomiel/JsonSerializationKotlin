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

    var viewModel : VM?  = null

    private var adapter  : AdapterRepo? = null


    companion object{
        const val  URL = "https://api.github.com/repositories?since=364"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =  ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.application).create(VM::class.java)

        recycler.layoutManager =  LinearLayoutManager(this)


        viewModel?.listOfRepo?.observe(this, { list ->
            adapter = AdapterRepo(this, list )
            recycler.adapter = adapter
        })


        viewModel?.fetchListOfData()



    }



}

class AdapterRepo(private val context : Context, private val list : List<Repo>) : RecyclerView.Adapter<AdapterRepo.VH>() {



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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v =  LayoutInflater.from(context).inflate(R.layout.repo_item,parent,false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val repo =  list[position]

        holder.bind(repo.name, repo.description)
    }

    override fun getItemCount(): Int = list.size
}