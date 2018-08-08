package com.zavijavasoft.yafina.ui.settings.account.edit

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ArrayAdapter
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.core.AccountEditPresenterImpl
import com.zavijavasoft.yafina.model.AccountEntity
import kotlinx.android.synthetic.main.fragment_edit_account.*
import javax.inject.Inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ACCOUNT_ID = "account-id"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditAccountFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditAccountFragment : MvpAppCompatFragment(), AccountEditView {

    @Inject
    @InjectPresenter
    lateinit var presenter: AccountEditPresenterImpl

    @ProvidePresenter
    fun providePresenter(): AccountEditPresenterImpl {
        return presenter
    }

    @Inject
    lateinit var appContext: Context

    private var accountId: Long? = null
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinnerCurrenciesAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        parseArgs()
    }

    private fun parseArgs() {
        arguments?.let {
            accountId = it.getLong(ARG_ACCOUNT_ID)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_account_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_action_done -> {
                presenter.save(getAccount())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getAccount(): AccountEntity {
        val id = accountId ?: 0L
        val name = etAccountName.text.toString()
        val currency = spinnerCurrencies.selectedItem as String
        val description = etAccountDescription.text.toString()

        return AccountEntity(id, currency, name, description)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (accountId != null) {
            presenter.update(accountId!!)
        } else {
            presenter.update()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        YaFinaApplication.component.inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun update(account: AccountEntity) {
        tilAccountName.isHintAnimationEnabled = false
        tilAccountDescription.isHintAnimationEnabled = false

        etAccountName.setText(account.name)
        etAccountDescription.setText(account.description)
        val position = spinnerCurrenciesAdapter.getPosition(account.currency)
        spinnerCurrencies.setSelection(position)

        tilAccountName.isHintAnimationEnabled = true
        tilAccountDescription.isHintAnimationEnabled = true
    }

    override fun updateCurrencies(currencies: List<String>) {
        spinnerCurrenciesAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item,
                currencies)
        spinnerCurrencies.adapter = spinnerCurrenciesAdapter
    }

    override fun close() {
        listener?.close()
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
        fun close()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EditAccountFragment.
         */
        fun newInstance() = EditAccountFragment()

        fun newInstance(accountId: Long) = EditAccountFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_ACCOUNT_ID, accountId)
            }
        }
    }
}
