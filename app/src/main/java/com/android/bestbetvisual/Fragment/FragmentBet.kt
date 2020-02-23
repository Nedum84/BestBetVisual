package com.android.bestbetvisual.Fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bestbetvisual.Adapters.BetRecAdapter
import com.android.bestbetvisual.Models.BetModel
import com.android.bestbetvisual.R
import com.android.bestbetvisual.UrlHolder
import com.android.bestbetvisual.VolleySingleton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import kotlinx.android.synthetic.main.fragment_bet.*


class FragmentBet : Fragment() {
    private lateinit var thisContext: Context

    val linearLayoutManager = LinearLayoutManager(activity)
    private var betList: MutableList<BetModel>? = mutableListOf()

    lateinit var ADAPTER : BetRecAdapter
    private var FRAG_POS = 0
    private var BET_DATE = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        thisContext = activity!!

        ADAPTER = BetRecAdapter(betList!!, thisContext, FRAG_POS)
        bet_list_recycler?.layoutManager = linearLayoutManager
        bet_list_recycler?.itemAnimator = DefaultItemAnimator()
        bet_list_recycler?.adapter = ADAPTER

        //checking for already sent argument
        if(arguments != null){
            val bet_day = arguments?.getCharSequence("bet_day").toString()
            BET_DATE = arguments!!.getString("bet_date")!!
            FRAG_POS = arguments!!.getInt("frag_position")

//            Initialize Loading cat news from json or from server
//            if(savedInstanceState==null)
                loadBetFromServer()
        }

//        swipe to refresh news
        swipe_to_refresh?.setOnRefreshListener {
            swipe_to_refresh?.isRefreshing = false
            refreshList()
        }
        no_data_tag.setOnClickListener {
            swipe_to_refresh?.isRefreshing = false
            refreshList()
        }
        tapToRetry.setOnClickListener {
            refreshList()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retain this fragment when activity is re-initialized
//        retainInstance = true
    }

    private fun refreshList(){
        betList!!.clear()
        ADAPTER.addItems(betList!!)
        ADAPTER.notifyDataSetChanged()
        loadBetFromServer()
    }

    private  fun loadBetFromJSON(){
//        val newsDetails = ClassSharedPreferences(thisContext).getSavedServer()
//        if(newsDetails !=""){
//
//            val dataArray = Gson().fromJson(newsDetails, Array<NewsListClassBinder>::class.java).asList()
//
//            for (i in 0 until dataArray.size) {
//                val eachNews = dataArray[i]
//                if(eachNews.news_category != CAT_ID&&CAT_ID != UrlHolder.LATEST_CAT_ID) continue
//                if(eachNews in newsList!!)continue
//
//
//                newsList!!.add(eachNews)
//                if (newsList!!.size >=10)break//fetch only 10
//            }
//        }else no_data_tag.visibility = View.VISIBLE

    }


    private fun loadBetFromServer(){
        no_data_tag?.visibility = View.GONE
        no_network_tag?.visibility = View.GONE
        loadingProgressbar?.visibility = View.VISIBLE


//        val rq: RequestQueue = Volley.newRequestQueue(thisContext)
        val jar = JsonArrayRequest(Request.Method.GET, "${UrlHolder.URL_ROOT}$BET_DATE", null,//http://divbet.herokuapp.com/get_bet/2020-2-16
                Response.Listener{ response ->
                        loadingProgressbar?.visibility = View.GONE

            val responseArrayLength = response.length()

            if ((responseArrayLength!=0)) {
                val respList = mutableListOf<BetModel>()

                for (x in 0 until responseArrayLength){
                    val jsonObj = response.getJSONObject(x)

                    val row = BetModel(
                            jsonObj.getString("teams"),
                            jsonObj.getString("league"),
                            jsonObj.getString("dated"),
                            jsonObj.getString("time"),
                            jsonObj.getString("odds"),
                            jsonObj.getString("predition"),
                            jsonObj.getString("score"),
                            jsonObj.getString("success")
                    )


                    if (row !in betList!!)respList.add(row)
                }

                ADAPTER.addItems(respList)
            }else{
                loadingProgressbar?.visibility = View.GONE
                no_data_tag?.visibility = View.VISIBLE
                no_bet_tv?.text = if(FRAG_POS==0){
                    "Yesterday's BET visual is not available"
                }else if (FRAG_POS==1){
                    "No available Bet Today"
                }else{
                    "Tomorrow's BET visual is not yet available"
                }
            }


        }, Response.ErrorListener { error ->
            Log.i("INFO", error.message)
            no_network_tag?.visibility = View.VISIBLE

            loadBetFromJSON()
        })

//        rq.add(jar)
        VolleySingleton.instance?.addToRequestQueue(jar)//adding request to queue

    }









    //Object to bind the datas
    companion object {

        fun newInstance(name: CharSequence, value: String, frag_position: Int): FragmentBet {

            val args = Bundle().apply {
                putCharSequence("bet_day", name)
                putString("bet_date", value)
                putInt("frag_position", frag_position)
            }
            val fragment = FragmentBet()
            fragment.arguments = args
            return fragment
        }
    }




}

