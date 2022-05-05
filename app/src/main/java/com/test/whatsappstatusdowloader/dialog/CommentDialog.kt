package com.test.whatsappstatusdowloader.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.AbsListView
import android.widget.RatingBar
import android.widget.Toast
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.databinding.DialogCommentBinding
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.MyIntent
import com.test.whatsappstatusdowloader.utils.UtilsMethod

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
            UtilsMethod.getScreenWidth(context,Constants.DIALOG_WIDTH_PERCENTAGE),
            AbsListView.LayoutParams.WRAP_CONTENT
        )
    }


    private fun setOnClick() {

        binding.apply {

            ratingBar.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, v, _ ->
                    if (v == 0f) {
                        tvCommentCaffeBazzar.visibility = View.GONE
                        btnComment.text=context.getString(R.string.comment)
                        btnComment.alpha=0.5f
                    } else {
                        tvCommentCaffeBazzar.visibility = View.VISIBLE
                        btnComment.alpha=1f

                        if (v < 4) {
                            btnComment.text = context.getString(R.string.send_email)
                            tvCommentCaffeBazzar.text = context.getString(R.string.please_send_email)
                        } else {
                            tvCommentCaffeBazzar.text = context.getString(R.string.please_register_your_comment)
                            btnComment.text = context.getString(R.string.comment)
                        }
                    }

                }
            ivClose.setOnClickListener(View.OnClickListener { cancel() })
            btnComment.setOnClickListener(View.OnClickListener {

                when(ratingBar.rating){
                    0f-> {
                        Toast.makeText(context,context.getString(R.string.submitt_comment),Toast.LENGTH_SHORT).show()
                        return@OnClickListener
                    }
                    in 1f..3f->MyIntent.emailIntent(context)
                    else->MyIntent.commentIntent(context)
                }

                cancel()
            })
        }
    }

}