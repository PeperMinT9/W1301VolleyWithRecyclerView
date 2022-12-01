package kr.ac.kumoh.s20170392.example.w1301volleywithrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20170392.example.w1301volleywithrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private val songAdapter = SongAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SongViewModel::class.java]

        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = songAdapter

        model.list.observe(this) {
            songAdapter.notifyItemRangeInserted(0, songAdapter.itemCount)
        }
        model.requestSong()
    }

    inner class SongAdapter : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txTitle = itemView.findViewById<TextView>(android.R.id.text1)
            val txSinger = itemView.findViewById<TextView>(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.txTitle.text = model.list.value?.get(position)?.title
            holder.txSinger.text = model.list.value?.get(position)?.singer
        }

        override fun getItemCount() = model.list.value?.size ?: 0
    }
}