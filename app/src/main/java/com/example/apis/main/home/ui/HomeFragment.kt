package com.example.apis.main.home.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apis.R
import com.example.apis.main.home.business.HomeViewModel
import com.example.apis.main.home.ui.adapter.CategoriesAdapter
import com.example.apis.misc.livedata.ChangedItemType
import com.example.apis.misc.livedata.ListHolderType
import com.example.apis.misc.models.EnumCategory
import com.example.apis.preferences.PreferencesActivity
import java.lang.UnsupportedOperationException


private const val TAG = "HomeFragment"
private const val TAG_HOME_FRAGMENT = "HOME_FRAGMENT"
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var rvCategories: RecyclerView
    private val adapterCategories = CategoriesAdapter { viewModel.updateSelectedCategory(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvPreferences = view.findViewById<TextView>(R.id.frg_mainhome_lb_preferences)
        tvPreferences.setOnClickListener { openPreferences() }

        rvCategories = view.findViewById(R.id.lyt_mainHome_rv_categories)
        rvCategories.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvCategories.adapter = adapterCategories

        Log.d(TAG, "viewModel $viewModel")
        viewModel.init()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterObservers()
    }

    private fun openPreferences() {
        startActivity(Intent(activity, PreferencesActivity::class.java))
    }

    private fun showWeatherCategory() {
        val fragment = HomeWeatherFragment()

        childFragmentManager
            .beginTransaction()
            .addToBackStack(TAG_HOME_FRAGMENT)
            .add(R.id.frg_mainHome_fl_details, fragment, "WEATHER_FRAGMENT")
            .commit()
    }

    private fun registerObservers() {
        viewModel.getCategory().observe(viewLifecycleOwner, Observer {
            if (it.type == ListHolderType.FULL) {
                adapterCategories.updateItems(it.list)
                return@Observer
            }

            it.changedItems.forEach { item ->
                if (item.type == ChangedItemType.UPDATE) {
                    adapterCategories.notifyItemChanged(item.index)
                }
            }
        })

        viewModel.getCategorySelected().observe(viewLifecycleOwner, Observer {

            when (it) {
                EnumCategory.WEATHER -> showWeatherCategory()
                else -> throw UnsupportedOperationException("Failed to identify category $it.")
            }
        })
    }

    private fun unregisterObservers() {
        viewModel.getCategory().removeObservers(viewLifecycleOwner)
    }
}
