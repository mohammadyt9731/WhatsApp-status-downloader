package com.ddt.whatsappStatusDownloader.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.RatingBar
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.activity.MainActivity
import com.ddt.whatsappStatusDownloader.databinding.DialogCommentBinding
import com.ddt.whatsappStatusDownloader.utils.Constants
import com.ddt.whatsappStatusDownloader.utils.MyIntent
import com.ddt.whatsappStatusDownloader.utils.MySharedPreferences
import com.ddt.whatsappStatusDownloader.utils.UtilsMethod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CommentDialog(mainActivity: MainActivity) : Dialog(mainActivity) {

    private var binding: DialogCommentBinding = DialogCommentBinding.inflate(layoutInflater)
    private lateinit var mainActivity: MainActivity
    init {

        this.mainActivity=mainActivity

        setContentView(binding.root)
        config()
        setOnClick()
    }


    private fun config() {

        //background transparent
        window!!.setBackgroundDrawableResource(android.R.color.transparent)

        //dialog animation
        window!!.attributes.windowAnimations = R.style.scale_anim_style

        //set width and height
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
                        btnComment.alpha=Constants.DISABLE_BUTTON_ALPHA
                    } else {
                        tvCommentCaffeBazzar.visibility = View.VISIBLE
                        btnComment.alpha=Constants.ENABLE_BUTTON_ALPHA

                        if (v < 4) {
                            btnComment.text = context.getString(R.string.send_email)
                            tvCommentCaffeBazzar.text = context.getString(R.string.please_send_email)
                        } else {
                            btnComment.text = context.getString(R.string.comment)
                            tvCommentCaffeBazzar.text = context.getString(R.string.please_register_your_comment)
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
                    else->{


                        MySharedPreferences.getInstance(context).setIsCommentRegister(true)
                        MyIntent.commentIntent(context)


                    }
                }

                cancel()
            })
        }
    }

}