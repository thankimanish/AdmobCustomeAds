package com.theappcoderz.admobcustomeads.ads

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class DelayedProgressDialog : DialogFragment() {
    private var fragmentManager: FragmentManager? = null
    companion object {
        private const val PROGRESS_CONTENT_SIZE_DP = 80
    }
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = requireActivity().layoutInflater
        builder.setView(inflater.inflate(com.theappcoderz.admobcustomeads.R.layout.dialog_progress, null))
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        if (dialog!!.window != null) {
            val px = (com.theappcoderz.admobcustomeads.ads.DelayedProgressDialog.Companion.PROGRESS_CONTENT_SIZE_DP * resources.displayMetrics.density).toInt()
            dialog!!.window!!.setLayout(px, px)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog!!.setCanceledOnTouchOutside(false)
    }

    override fun show(fm: FragmentManager, tag: String?) {
        if (isAdded) return
        fragmentManager = fm
        showDialogAfterDelay()
    }

    private fun showDialogAfterDelay() {
        val dialogFragment = fragmentManager?.findFragmentByTag(tag) as DialogFragment?
        if (dialogFragment != null) {
            fragmentManager?.beginTransaction()?.show(dialogFragment)
                ?.commitAllowingStateLoss()
        } else {
            val ft = fragmentManager?.beginTransaction()
            if (ft != null) {
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
            }
        }
    }

    fun cancel() {
        dismiss()
    }

    override fun dismiss() {
        val ft = fragmentManager?.beginTransaction()
        if (ft != null) {
            ft.remove(this)
            ft.commitAllowingStateLoss()
        }
    }
}
