package com.techpig.rogys

import android.app.Dialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlin.system.exitProcess

open class BaseActivity : AppCompatActivity() {

    lateinit var mProgressDialog: Dialog

    fun showAlerter(title: String, message: String, isErrorAlert: Boolean, duration: Long) {
        val alert = Alerter.create(this)
        alert.setTitle(title)
        alert.setText(message)
        if (isErrorAlert) {
            alert.setIcon(R.drawable.ic_error)
            alert.setBackgroundColorRes(R.color.crimson)
        } else {
            alert.setBackgroundColorRes(R.color.green_hard)
            alert.setIcon(R.drawable.alerter_ic_face)
        }
        alert.setDuration(duration)
        alert.enableSwipeToDismiss()
        alert.show()
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.tv_progress_text.text = text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}