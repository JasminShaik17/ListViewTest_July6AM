package cubex.mahesh.listviewtest

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indiview.view.*
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var status = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        if(status==PackageManager.PERMISSION_GRANTED) {
            readFiles()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    0)
        }
    }

   var stack = Stack<String>()

    fun readFiles( )
    {
        var path = "/storage/sdcard0/"
        var f = File(path)
        if(!f.exists())
        {
            path = "/storage/emulated/0/"
            f = File(path)
        }
       stack.add(path)

        var files = f.list()
       /* var myadapter = ArrayAdapter<String>(this@MainActivity,
             android.R.layout.simple_list_item_single_choice, files )*/
        var myadapter = ArrayAdapter<String>(this@MainActivity,
                R.layout.indiview, files )
        lview.adapter = myadapter
       lview.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this@MainActivity,
                   view.tv1.text.toString(),
                    Toast.LENGTH_LONG).show()
           path = path+view.tv1.text.toString()
           var f_new = File(path)
           if(f_new.isDirectory){
               path = path+"/"
               stack.add(path)
               var files = f_new.list()
               var myadapter = ArrayAdapter<String>(this@MainActivity,
                       R.layout.indiview, files )
               lview.adapter = myadapter
           }
       }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
            permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            readFiles()
        }else{
            Toast.makeText(this,
                    "U can't read storage info, if u dont enable permission",
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if(!stack.empty()) {
            var path = stack.pop()
            var f_new = File(path)
            var files = f_new.list()
            var myadapter = ArrayAdapter<String>(this@MainActivity,
                    R.layout.indiview, files)
            lview.adapter = myadapter
        }else{
            finish()
        }
    }



}   // MainActivity
