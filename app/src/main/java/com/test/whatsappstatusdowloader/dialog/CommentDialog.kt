package com.test.whatsappstatusdowloader.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.AbsListView
import android.widget.RatingBar
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.DialogCommentBinding
import com.test.whatsappstatusdowloader.utils.MyIntent

class CommentDialog(context: Context) : Dialog(context) {

    private var binding: DialogCommentBinding = DialogCommentBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        config()
        setOnClick()
    }

    private fun config() {
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.attributes.windowAnimations = R.style.scale_anim_style
        window!!.setLayout(
            AbsListView.LayoutParams.MATCH_PARENT,
            AbsListView.LayoutParams.WRAP_CONTENT
        )
    }


    private fun setOnClick() {

        binding.apply {

            ratingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, v, _ ->
                    if (v == 0f) {
                        btnComment.setBackgroundColor(context.getColor(R.color.dark_green))
                        tvCommentCaffeBazzar.visibility = View.GONE
                        btnComment.isEnabled = false
                    } else {
                        btnComment.setBackgroundColor(context.getColor(R.color.green))
                        tvCommentCaffeBazzar.visibility = View.VISIBLE
                        btnComment.isEnabled = true
                    }
                    if (v < 4) {
                        btnComment.text = context.getString(R.string.send_email)
                        tvCommentCaffeBazzar.text = context.getString(R.string.please_send_email)
                    } else {
                        tvCommentCaffeBazzar.text = context.getString(R.string.please_register_your_comment)
                        btnComment.text = context.getString(R.string.comment)
                    }
                }
            btnClose.setOnClickListener(View.OnClickListener { cancel() })
            btnComment.setOnClickListener(View.OnClickListener {
                if (ratingBar.getRating() < 4) {
                    MyIntent.emailIntent(context)
                } else {
                    MyIntent.commentIntent(context)
                }
                cancel()
            })
        }
    }

}