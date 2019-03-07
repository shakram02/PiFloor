package pifloor.processing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import butterknife.Optional
import pifloor.R
import java.util.*
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder
import kotlin.collections.ArrayList

class AssignmentActivity : Activity() {

    var tiles: ArrayList<String> = arrayListOf()
    var flash: Boolean? = null
    var focus: Boolean? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)
        var i = getIntent()
        recyclerView = findViewById(R.id.recycler)
        tiles = i.getStringArrayListExtra("tiles")
        flash = i.getBooleanExtra("UseFlash", false)
        focus = i.getBooleanExtra("AutoFocus", false)

        var dragMgr : RecyclerViewDragDropManager = RecyclerViewDragDropManager()
        dragMgr.setInitiateOnMove(false)
        dragMgr.setInitiateOnLongPress(true)

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = dragMgr.createWrappedAdapter(MyAdapter(tiles))

        dragMgr.attachRecyclerView(recyclerView!!)

        var ok  = findViewById<Button>(R.id.btnDone)
        ok.setOnClickListener {
            startGameActivity()
        }

        var cancel  = findViewById<Button>(R.id.btnCancel)
        cancel.setOnClickListener {
            backCalibActivity()
        }
    }


    fun startGameActivity() {
        val intent = Intent(this, GameModeActivity::class.java)
        intent.putExtra("AutoFocus", focus)
        intent.putExtra("UseFlash", flash)
        intent.putExtra("tiles", tiles)
        startActivity(intent)
    }

    fun backCalibActivity() {
        val intent = Intent(this, CalibrationModeActivity::class.java)
        startActivity(intent)
    }


    internal class MyItem(val id: Long, val text: String)

    internal class MyViewHolder(itemView: View) : AbstractDraggableItemViewHolder(itemView) {
        var textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)
        }
    }

    internal class MyAdapter(tiles : ArrayList<String>) : RecyclerView.Adapter<MyViewHolder>(), DraggableItemAdapter<MyViewHolder> {
        var mItems: MutableList<MyItem>
        private var ties : ArrayList<String>? = null

        init {
            setHasStableIds(true) // this is required for D&D feature.

            mItems = ArrayList()
            ties = tiles
            var counter = 0
            for (i in tiles) {
                mItems.add(MyItem(counter.toLong(), i))
                counter++
            }
        }

        override fun getItemId(position: Int): Long {
            return mItems[position].id // need to return stable (= not change even after reordered) value
        }

        @NonNull
        override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_minimal, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(@NonNull holder: MyViewHolder, position: Int) {
            val item = mItems[position]
            holder.textView.text = item.text
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        override fun onMoveItem(fromPosition: Int, toPosition: Int) {
            val movedItem = mItems.removeAt(fromPosition)
            mItems.add(toPosition, movedItem)
        }

        override fun onCheckCanStartDrag(@NonNull holder: MyViewHolder, position: Int, x: Int, y: Int): Boolean {
            return true
        }

        override fun onGetItemDraggableRange(@NonNull holder: MyViewHolder, position: Int): ItemDraggableRange? {
            return null
        }

        override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
            return true
        }

        override fun onItemDragStarted(position: Int) {}

        override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
            var temp = this!!.ties?.get(fromPosition)
            ties!!.removeAt(fromPosition)
            ties!!.add(toPosition,temp!!)
        }
    }
}
