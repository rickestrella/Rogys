package com.techpig.rogys.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.techpig.rogys.R
import com.techpig.rogys.firestore.FirestoreClass
import com.techpig.rogys.models.Product
import com.techpig.rogys.ui.activities.AddProductActivity
import com.techpig.rogys.ui.adapters.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun successProductListFromFireStore(productList: ArrayList<Product>) {
        hideProgressDialog()

        if (productList.size > 0) {
            rv_product_items.visibility = View.VISIBLE
            tv_noProductsFound.visibility = View.GONE

            rv_product_items.layoutManager = LinearLayoutManager(activity)
            rv_product_items.setHasFixedSize(true)
            val adapterProducts = ProductListAdapter(requireActivity(), productList)
            rv_product_items.adapter = adapterProducts
        } else {
            rv_product_items.visibility = View.GONE
            tv_noProductsFound.visibility = View.VISIBLE
        }

    }

    private fun getProductListFromFireStore() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductsList(this)
    }

    override fun onResume() {
        super.onResume()
        getProductListFromFireStore()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_products, container, false)

        return v
    }

    //Complemento de setHasOptionsMenu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.action_add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}