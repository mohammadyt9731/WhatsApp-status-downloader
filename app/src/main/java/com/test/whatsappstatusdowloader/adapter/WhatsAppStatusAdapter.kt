package com.test.whatsappstatusdowloader.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.activity.ShowMediaActivity
import com.test.whatsappstatusdowloader.databinding.WhatsappStatusBinding
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.FileOperation
import com.test.whatsappstatusdowloader.utils.UtilsMethod
import java.io.File

private lateinit var binding: WhatsappStatusBinding

class WhatsAppStatusAdapter(
    activity: Activity,
) :
    RecyclerView.Adapter<WhatsAppStatusAdapter.ViewHolder>() {

    var activity: Activity
    var itemWidth: Int

    init {

        this.activity = activity
        itemWidth = UtilsMethod.getScreenWidth(activity, 44)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = WhatsappStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setUpViews(position)
        holder.setStatusImage(position)
        holder.setOnClickListener(position)
    }


    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {


        fun setUpViews(position: Int) {

            binding.apply {
                clRoot.layoutParams.width = itemWidth

                if (UtilsMethod.isVideoFile(differ.currentList[position].path))
                    ivVideo.visibility = View.VISIBLE
                else
                    ivVideo.visibility = View.GONE


                if (File(Constants.SAVED_DIRECTORY + "/" + differ.currentList[position].name).exists()) {
                    lottieDownload.frame = Constants.LOTTIE_END_FRAME
                } else
                    lottieDownload.frame = Constants.LOTTIE_START_FRAME

            }
        }

        fun setStatusImage(position: Int) {

            if (UtilsMethod.isVideoFile(differ.currentList[position].path)) {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(differ.currentList[position].path)
                    val bitmap = retriever.getFrameAtTime(1)

                    Glide.with(activity).load(bitmap)
                        .placeholder(ColorDrawable(Color.WHITE))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivStatus)
                } catch (e: Exception) {

                }


            } else {

                Glide.with(activity).load(differ.currentList[position])
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivStatus)


            }
        }

        fun setOnClickListener(position: Int) {
            binding.apply {

                ivShareStatus.setOnClickListener() {
                    FileOperation.shareFile(activity, differ.currentList[position])
                }

                ivStatus.setOnClickListener() {

                    val intent = Intent(activity, ShowMediaActivity::class.java)
                    intent.putExtra(Constants.MEDIA_PATH_KEY, differ.currentList[position])
                    activity.startActivity(intent)

                }

                lottieDownload.setOnClickListener() {
                    val sourceFile = File(differ.currentList[position].path)
                    val destinationFile =
                        File(Constants.SAVED_DIRECTORY + "/" + differ.currentList[position].name)

                    if (destinationFile.exists()) {
                        Toast.makeText(activity, "قبلا ذخیره شده است.", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }



                    try {
                        sourceFile.copyTo(destinationFile)
                        Toast.makeText(
                            activity,
                            "در پوشه saveDirectory ذخیره شد",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                       lottieDownload.playAnimation()
                        Log.i("asff",position.toString())
                    } catch (e: Exception) {
                        Toast.makeText(
                            activity,
                            activity.getString(R.string.unknown_error),
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(activity, e.message.toString(), Toast.LENGTH_LONG).show()
                    }

                }
            }

        }
    }



    private val differCallBack = object : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}