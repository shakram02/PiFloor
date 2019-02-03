package pifloor.processing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import butterknife.OnClick
import butterknife.Optional
import pifloor.R

class AssignmentActivity : AppCompatActivity() {

    var listView: ListView? = null
    var tiles: ArrayList<String> = arrayListOf()
    var adapter: ListAdapter? = null
    var flash: Boolean? = null
    var focus: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)
        var i = getIntent()
        tiles = i.getStringArrayListExtra("tiles")
        flash = i.getBooleanExtra("flash", false)
        focus = i.getBooleanExtra("focus", false)

//        Toast.makeText(this, "Hi" + tiles.size.toString(), Toast.LENGTH_SHORT).show()

        listView = findViewById(R.id.indexListID) as ListView
        adapter = ListAdapter(this, tiles, this)
        (listView as ListView).adapter = adapter;

//        prepareData()
        adapter!!.cli = object : ListAdapter.clicked{
            override fun onClick() {
                adapter!!.notifyDataSetChanged()
                Toast.makeText(applicationContext, "Move", Toast.LENGTH_SHORT).show()
            }

        }
    }

    @Optional
    @OnClick(R.id.btnDone)
    fun startGameActivity() {
        val intent = Intent(this, GameModeActivity::class.java)
        intent.putExtra("focus", focus)
        intent.putExtra("flash", flash)
        startActivity(intent)
    }

    private fun prepareData() {
        var i = getIntent()
        tiles = i.getStringArrayListExtra("tiles")
        adapter?.notifyDataSetChanged()
    }
}
