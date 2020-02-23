package com.android.bestbetvisual.SharedPreference

import android.content.Context
import java.util.HashSet

class  ClassSharedPreferences(val context: Context?){

    private val PREFERENCE_NAME = "bst_bet_v_preference"
    private val PREFERENCE_CURRENT_NEWS_ID = "current_news_id"
    private val PREFERENCE_CURRENT_NEWS_LIST_ID = "current_news_list_id"
//
//    private val preference = context?.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)!!
//
//    //set current news id
//    fun setCurrentNewsId(news_id:Int){
//        val editor = preference.edit()
//        editor.putInt(PREFERENCE_CURRENT_NEWS_ID,news_id)
//        editor.apply()
//    }
//    //get current news id
//    fun getCurrentNewsId():Int{
//        return  preference.getInt(PREFERENCE_CURRENT_NEWS_ID,1)
//    }

}