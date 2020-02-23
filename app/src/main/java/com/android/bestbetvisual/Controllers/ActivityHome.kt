package com.android.bestbetvisual.Controllers

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.bestbetvisual.Fragment.FragmentBet
import com.android.bestbetvisual.MiscClass.ClassDateAndTime
import com.android.bestbetvisual.MiscClass.ClassShareApp
import com.android.bestbetvisual.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_home.*


class ActivityHome : AppCompatActivity() {
    lateinit var viewpagerAdapter: ViewPaggerToggler
    lateinit var thisContext: Context
    var TAB_POSITION = "POSITION"
    var currentTabPosition = 1
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        toolbar.title = ""
        thisContext = this

        //LOAD DATA...
        addFragmentsAndViewpager()



        //ads(BANNER) -> SMALL
        //loading ads starts
        MobileAds.initialize(this,  getString(R.string.ads_app_id))
        val adRequest = AdRequest.Builder().build()
//        adsView.adUnitId = getString(R.string.banner_ads1)
        adsView.loadAd(adRequest)



//        for testing
//        val request = AdRequest.Builder()
//                .addTestDevice("33BE2250B43518CCDA7DE426D04EE231")  // An example device ID
//                .build()
////        adsView.adUnitId = getString(R.string.banner_test_ad_unit_id)
//        adsView.loadAd(request);
//        loading ads stops







        //ADS(INTERSTITIAL) -> BIG

        //loading full page ads
        MobileAds.initialize(this,  getString(R.string.ads_app_id))
        mInterstitialAd = InterstitialAd(this)
//        //real values starts
        mInterstitialAd.adUnitId = getString(R.string.interstitial_ads1)//real ID
        mInterstitialAd.loadAd(AdRequest.Builder().build())//real One

        //testing starts
//        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"//testing one
//        mInterstitialAd.loadAd(AdRequest.Builder().addTestDevice("33BE2250B43518CCDA7DE426D04EE231").build())//testing one

    }

    override fun onResume() {
        super.onResume()
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }

    }

    override fun onPause() {
        super.onPause()
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }


    private fun addFragmentsAndViewpager(){
        val timeToday = (System.currentTimeMillis()/1000)
        val timeYesterday = timeToday-(24*3600)
        val timeTomorrow = timeToday+(24*3600)


        viewpagerAdapter = ViewPaggerToggler(supportFragmentManager)
        viewpagerAdapter.addFragment(FragmentBet(), "Yesterday", ClassDateAndTime().getDateTime(timeYesterday))
        viewpagerAdapter.addFragment(FragmentBet(), "Today", ClassDateAndTime().getDateTime(timeToday))
        viewpagerAdapter.addFragment(FragmentBet(), "Tomorrow", ClassDateAndTime().getDateTime(timeTomorrow))

        // Finally, data bind the view pager widget with pager viewpagerAdapter
        game_view_pager.adapter = viewpagerAdapter
        game_view_pager.offscreenPageLimit = viewpagerAdapter.count-1
        game_tabs.setupWithViewPager(game_view_pager)
        game_view_pager.currentItem = currentTabPosition


        game_view_pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                currentTabPosition = position
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })

    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAB_POSITION, currentTabPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentTabPosition = savedInstanceState.getInt(TAB_POSITION)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_home, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu_share_app -> {//for news list
                ClassShareApp(this).shareApp()
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        if (game_view_pager.currentItem == 1) {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            }

            super.onBackPressed()

        } else {
            game_view_pager.currentItem = 1
        }
    }

//    class ViewPaggerToggler(manager: FragmentManager,val ctx:Context) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    class ViewPaggerToggler(manager: FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragments: MutableList<Fragment> = ArrayList()
        private val titles: MutableList<String> = ArrayList()
        private val bet_dates: MutableList<String> = ArrayList()


        override fun getItem(position: Int): Fragment {
            return FragmentBet.newInstance(titles[position], bet_dates[position], position)
        }
        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }


        fun addFragment(fragment: Fragment, title: String, date_val: String) {
            fragments.add(fragment)
            titles.add(title)
            bet_dates.add(date_val)
        }
    }


}