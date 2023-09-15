package com.brokenscreen.prank.hdnaturewallpaper.ui
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import com.adsmodule.api.AdsModule.AdUtils
import com.adsmodule.api.AdsModule.Utils.Constants
import com.brokenscreen.prank.hdnaturewallpaper.R
import com.brokenscreen.prank.hdnaturewallpaper.SingletonClasses.AppOpenAds
import com.brokenscreen.prank.hdnaturewallpaper.databinding.ActivityMainBinding
import com.brokenscreen.prank.hdnaturewallpaper.fragment.CategoryFragment
import com.brokenscreen.prank.hdnaturewallpaper.fragment.DownloadFragment
import com.brokenscreen.prank.hdnaturewallpaper.fragment.FavouriteFragment
import com.brokenscreen.prank.hdnaturewallpaper.fragment.WallpaperFragment
import com.brokenscreen.prank.hdnaturewallpaper.utils.Utility

class MainActivity : BaseActivity() {
    companion object {
        private const val SELECTED_ITEM_ID = "SELECTED_ITEM_ID"
    }

    private val mainActivityBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityBinding.root)

        with(mainActivityBinding) {
            bottomNavigationView.itemIconTintList = null
            bottomNavigationView.setOnItemSelectedListener { menuItem ->
                val fragment: Fragment = when (menuItem.itemId) {
                    R.id.wallpaperNav -> {
                        txtMenu.text = "Wallpaper"
                        WallpaperFragment()
                    }
                    R.id.categoryNav -> {
                        txtMenu.text = "Categories"
                        CategoryFragment()
                    }
                    R.id.favouritesNav -> {
                        txtMenu.text = "Favourites"
                        FavouriteFragment()
                    }
                    R.id.downloadNav -> {
                        txtMenu.text = "Downloads"
                        DownloadFragment()
                    }
                    else -> throw IllegalArgumentException("Invalid menu item ID")
                }

                loadFragment(fragment)
                true
            }
        }

        mainActivityBinding.menuBtn.setOnClickListener {
            mainActivityBinding.drawer.openDrawer(Gravity.LEFT)
        }
        mainActivityBinding.backBtn.setOnClickListener {
            mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
        }
        mainActivityBinding.wallpaperLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, AppOpenAds.activity
            ) { isLoaded: Boolean ->
                mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(WallpaperFragment(), R.drawable.wallpaper_clear, "Wallpaper")
            }

        }

        mainActivityBinding.categoryLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, AppOpenAds.activity
            ) { isLoaded: Boolean ->
                mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(CategoryFragment(), R.drawable.category_clear, "Categories")
            }

        }

        mainActivityBinding.favouriteLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, AppOpenAds.activity
            ) { isLoaded: Boolean ->
                mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(FavouriteFragment(), R.drawable.favourites_clear, "Favourites")
            }

        }

        mainActivityBinding.downloadLl.setOnClickListener {
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, AppOpenAds.activity
            ) { isLoaded: Boolean ->
                mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                loadFragmentWithIcon(DownloadFragment(), R.drawable.download_clear, "Downloads")
            }

        }
        mainActivityBinding.privacyPolicyLl.setOnClickListener{
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, AppOpenAds.activity
            ) { isLoaded: Boolean ->
                mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                val intent = Intent(this@MainActivity, NewPrivacyPolicyActivity::class.java)
                startActivity(intent)
            }

        }
        mainActivityBinding.termsAndConditionLl.setOnClickListener{
            AdUtils.showInterstitialAd(
                Constants.adsResponseModel.interstitial_ads.adx, AppOpenAds.activity
            ) { isLoaded: Boolean ->
                mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                val intent = Intent(this@MainActivity, TermsConditionsActivity::class.java)
                startActivity(intent)
            }

        }

        mainActivityBinding.bottomNavigationView.selectedItemId =
            savedInstanceState?.getInt(SELECTED_ITEM_ID) ?: R.id.wallpaperNav

        // Apply gradient shader to the bottom navigation text items
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val lgBlueColor = getColor(R.color.lg_blue)
            val primaryColor = getColor(R.color.primary)

            Utility.setGradientShaderToTextView(
                mainActivityBinding.txtMenu,
                lgBlueColor,
                primaryColor
            )
        }

        mainActivityBinding.bottomNavigationView.setOnItemReselectedListener {
            if (it.itemId != mainActivityBinding.bottomNavigationView.selectedItemId) {
                mainActivityBinding.bottomNavigationView.selectedItemId = it.itemId
            }
        }

    }



    private fun loadFragmentWithIcon(fragment: Fragment, iconResourceId: Int, itemName: String) {
        val bundle = Bundle()
        bundle.putInt("iconResourceId", iconResourceId)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        // Update the selected item in the bottom navigation view
        mainActivityBinding.bottomNavigationView.selectedItemId = when (fragment) {
            is WallpaperFragment -> {
                mainActivityBinding.txtMenu.text = itemName
                R.id.wallpaperNav
            }
            is CategoryFragment -> {
                mainActivityBinding.txtMenu.text = itemName
                R.id.categoryNav
            }
            is FavouriteFragment -> {
                mainActivityBinding.txtMenu.text = itemName
                R.id.favouritesNav
            }
            is DownloadFragment -> {
                mainActivityBinding.txtMenu.text = itemName
                R.id.downloadNav
            }
            else -> -1
        }
    }
    override fun onBackPressed() {
        AdUtils.showBackPressAds(
            AppOpenAds.activity, Constants.adsResponseModel.app_open_ads.adx
        ) {
            val intent = Intent(this@MainActivity, DashBoardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ITEM_ID, mainActivityBinding.bottomNavigationView.selectedItemId)
    }
}
