package com.zavijavasoft.yafina.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.zavijavasoft.yafina.R
import com.zavijavasoft.yafina.ui.operation.TRANSACTION_REQUEST_TAG
import com.zavijavasoft.yafina.ui.operation.TransactionRequest
import com.zavijavasoft.yafina.utils.afterTextChanged
import com.zavijavasoft.yafina.utils.roundSum


class OpenTransactionActivity : AppCompatActivity() {

    companion object {
        const val TRANSACTION_ACCEPTED = 1
        const val TRANSACTION_CANCELED = 2
    }

    @BindView(R.id.enter_sum_editor)
    lateinit var sumEditor: EditText

    @BindView(R.id.entered_sum_hint)
    lateinit var sumHintText: TextView

    @BindView(R.id.enter_sum_title)
    lateinit var sumTitle: TextView

    @BindView(R.id.entered_sum_currency)
    lateinit var sumCurrency: TextView

    @BindView(R.id.button_confirm_transaction)
    lateinit var buttonOk: Button

    @BindView(R.id.button_cancel_transaction)
    lateinit var buttonCancel: Button

    lateinit var unbinder: Unbinder

    lateinit var request: TransactionRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_transaction)

        request = intent.getParcelableExtra(TRANSACTION_REQUEST_TAG)
        unbinder = ButterKnife.bind(this)

        sumCurrency.text = request.currency

        sumHintText.text = ""

        if (request.maxSum != Float.NaN)
            sumHintText.text = getString(R.string.max_sum_on_account_hint, request.maxSum, request.currency)


        sumEditor.afterTextChanged {
            buttonOk.isEnabled = true
            if (request.maxSum != Float.NaN) {
                val sum = sumEditor.text.toString().toFloat()
                if (sum > request.maxSum)
                    buttonOk.isEnabled = false
            }
        }

        buttonOk.isEnabled = false
        buttonOk.setOnClickListener {
            val intent = Intent()
            intent.putExtra(TRANSACTION_REQUEST_TAG, TransactionRequest(
                    type = request.type,
                    currency = request.currency,
                    accountFrom = request.accountFrom,
                    accountTo = request.accountTo,
                    articleFrom = request.articleFrom,
                    articleTo = request.articleTo,
                    maxSum = sumEditor.text.toString().toFloat().roundSum())

            )
            setResult(TRANSACTION_ACCEPTED, intent)
            finish()
        }

        buttonCancel.setOnClickListener {
            setResult(TRANSACTION_CANCELED, intent)
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
    }
}
