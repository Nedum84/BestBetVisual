package com.android.bestbetvisual.Controllers

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bestbetvisual.Adapters.BetRecAdapter
import com.android.bestbetvisual.Models.BetModel
import com.android.bestbetvisual.R
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list = ArrayList<BetModel>()


        val url = "http://divbet.herokuapp.com/get_bet/2020-2-16"
//
//        val rq: RequestQueue = Volley.newRequestQueue(this)
//        val jar = JsonArrayRequest(
//            Request.Method.GET, url, null, Response.Listener { response ->
//
//                for (x in 0..response.length() - 1){
//                    list.add(
//                        BetModel(
//                            response.getJSONObject(x).getString("teams"),
//                            response.getJSONObject(x).getString("league"),
//                            response.getJSONObject(x).getString("dated"),
//                            response.getJSONObject(x).getString("time"),
//                            response.getJSONObject(x).getString("odds"),
//                            response.getJSONObject(x).getString("predition"),
//                            response.getJSONObject(x).getString("score"),
//                            response.getJSONObject(x).getBoolean("success")
//                        )
//                    )
//                }
//                var adp = BetRecAdapter(this, list)
//                recyclerView1.layoutManager = LinearLayoutManager(this)
//                recyclerView1.adapter = adp
//
//            }, Response.ErrorListener { error ->
//                Log.i("INFO", error.message)
//                Toast.makeText(this, error.message , Toast.LENGTH_LONG).show()
//            })
//
//        rq.add(jar)
    }
}
