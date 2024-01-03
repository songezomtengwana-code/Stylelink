package com.ekasi.stylelink.ui.components

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.ekasi.stylelink.R

class CustomProgressDialog(context: Context) : Dialog(context) {

//        private val messageTextView: TextView by lazy { findViewById(R.id.message_text) }

        init {
            setContentView(R.layout.activity_custom_progress_dialogue)
            setCancelable(false)
        }

//        fun setMessage(message: String) {
//            messageTextView.text = message
//        }
}