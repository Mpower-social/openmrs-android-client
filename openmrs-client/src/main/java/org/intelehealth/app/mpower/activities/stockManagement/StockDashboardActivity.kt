package org.intelehealth.app.mpower.activities.stockManagement

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.openmrs.android_sdk.utilities.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import org.intelehealth.app.mpower.R
import org.intelehealth.app.mpower.activities.ACBaseActivity
import org.intelehealth.app.mpower.databinding.ActivityStockDashboardBinding
import org.intelehealth.app.mpower.databinding.ActivityStockListBinding


@AndroidEntryPoint
class StockDashboardActivity : ACBaseActivity(), View.OnClickListener {
    private var query: String? = null
    private lateinit var mBinding: ActivityStockDashboardBinding
    private val mViewModel: StockInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_dashboard)
        mBinding.viewModel = mViewModel

        supportActionBar?.let {
            it.elevation = 0f
            it.setDisplayHomeAsUpEnabled(true)
            it.setTitle(R.string.drawer_stock_dashboard)
        }
        // Create fragment
        var memberListFragment = supportFragmentManager.findFragmentById(R.id.referedMembersContentFrame) as StockDashboardFragment?
        if (memberListFragment == null) {
            memberListFragment = StockDashboardFragment.newInstance()
        }
        if (!memberListFragment.isActive) {
            addFragmentToActivity(supportFragmentManager,
                memberListFragment, R.id.referedMembersContentFrame)
        }

        observeData()
    }

    private fun observeData() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.actionAddPatients -> {

            }
            android.R.id.home -> onBackPressed()
            else -> {
            }
        }
        return true
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        super.onCreateOptionsMenu(menu)
//        menuInflater.inflate(R.menu.find_locally_and_add_patients_menu, menu)
//
//
//        val searchMenuItem = menu.findItem(R.id.actionSearchLocal)
//        val searchView = menu.findItem(R.id.actionSearchLocal).actionView as SearchView
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                searchView.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(query: String): Boolean {
//                val memberListFragment = supportFragmentManager.findFragmentById(R.id.referedMembersContentFrame) as StockInFragment?
//                if(NetworkUtils.isOnline()){
//                   // memberListFragment?.fetchReferredMembersOnRefresh(query)
//                } else {
//                   // memberListFragment?.fetchMembers(query)
//                }
//                return true
//            }
//        })
//        return true
//    }

    override fun onClick(v: View?) {
        val contentDashboard = mBinding.referedMembersContentFrame
        when (v?.id) {
        }
    }
}