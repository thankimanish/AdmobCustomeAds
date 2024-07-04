package com.theappcoderz.sampleads

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

object ConnectionDialog {

    fun showConnectionDialog(activity: AppCompatActivity) {
        val alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(activity.getString(R.string.internet_connection))
        alertBuilder.setMessage(activity.getString(R.string.internet_connection_not_established_no))
        alertBuilder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            activity.finish()
            dialog.dismiss()
        }
        val alert = alertBuilder.create()
        alert.show()
    }
}
