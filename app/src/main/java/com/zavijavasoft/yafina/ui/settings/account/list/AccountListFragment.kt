package com.zavijavasoft.yafina.ui.settings.account.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.core.AccountListPresenterImpl
import com.zavijavasoft.yafina.model.AccountEntity
import kotlinx.android.synthetic.main.fragment_account_list.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AccountListFragment : MvpAppCompatFragment(), AccountListView {

    @Inject
    @InjectPresenter
    lateinit var presenter: AccountListPresenterImpl

    @ProvidePresenter
    fun providePresenter(): AccountListPresenterImpl {
        return presenter
    }

    @Inject
    lateinit var appContext: Context

    private lateinit var listAdapter: AccountListRecyclerViewAdapter
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_account_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_action_new -> {
                presenter.newAccount()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AccountListRecyclerViewAdapter(mutableListOf()) { aId -> presenter.edit(aId) }
        rvAccounts.adapter = adapter
        listAdapter = adapter

        rvAccounts.addItemDecoration(DividerItemDecoration(activity, VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        presenter.needUpdate()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        YaFinaApplication.component.inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun update(accounts: List<AccountEntity>) {
        listAdapter.update(accounts)
    }

    override fun requireAccountEdit(accountId: Long) {
        listener?.onEditAccount(accountId)
    }

    override fun requireAccountCreate() {
        listener?.onCreateAccount()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onEditAccount(accountId: Long)
        fun onCreateAccount()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AccountListFragment.
         */
        fun newInstance() = AccountListFragment()
    }
}
