package pifloor.processing

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import pifloor.R
import kotlin.reflect.KClass

class ListAdapter (private val activity: Activity, items: ArrayList<String>, assignmentActivity: AssignmentActivity): BaseAdapter(){

    private var items = ArrayList<String>()
    private var passedActivity = assignmentActivity
    init {
        this.items = items as ArrayList<String>
    }

    interface clicked {
        fun onClick()
    }

    var cli : clicked? = null

    override fun getItemId(i: Int): Long {
        return i.toLong();
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(i: Int): Any {
        return items[i]
    }

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(i: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        if(convertView == null){
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.index_element, parent, false)
            val title = view.findViewById<TextView>(R.id.wordID)
            val btnUP = view.findViewById<TextView>(R.id.btnUPID)
            val btnDN = view.findViewById<TextView>(R.id.btnDNID)
            title.text = items[i]
            btnUP.setOnClickListener{
                swap(i, i-1)
            }
            btnDN.setOnClickListener{
                swap(i, i+1)
            }
        } else{
            view = convertView
        }
        Log.d("elements", items[0]+","+items[1]+","+items[2]+","+items[3])
        return view
    }

    fun swap(x: Int, y: Int){
        if(y < 0 || y >= items.size){
            return
        }
        var tmp: String = items[x]
        items[x] = items[y]
        items[y] = tmp
        cli!!.onClick()
    }
}