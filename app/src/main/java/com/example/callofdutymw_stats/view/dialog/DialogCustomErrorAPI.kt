package com.example.callofdutymw_stats.view.dialog

import android.annotation.SuppressLint
import android.app.Activity
import com.example.callofdutymw_stats.R

class DialogCustomErrorAPI(
    private val activity: Activity
) {
    private var onClickListener: OnClickListener? = null

    fun setOnClickListenerInImageView(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    @SuppressLint("InflateParams")
    private var alertDialogView =
        activity.layoutInflater.inflate(R.layout.alert_dialog_error_api, null).apply {
        }

    private val builder = androidx.appcompat.app.AlertDialog.Builder(activity).apply {
        setView(alertDialogView)
    }

    fun setSaveAndCancelButton() {
        builder.apply {
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }
    }

    private var currentDialog: androidx.appcompat.app.AlertDialog? = null

    private var onDialogClickListener: OnClickListener? = null

    fun showDialog() {
        if (currentDialog == null) currentDialog = builder.create()
        currentDialog?.show()
    }

    public interface OnClickListener {
        fun onClick()
    }
}