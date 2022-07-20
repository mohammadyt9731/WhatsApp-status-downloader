package com.ddt.whatsappStatusDownloader.adapter

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ddt.whatsappStatusDownloader.R
import com.ddt.whatsappStatusDownloader.databinding.SavedStatusBinding
import com.ddt.whatsappStatusDownloader.utils.Constants
import com.ddt.whatsappStatusDownloader.utils.FileOperation
import com.ddt.whatsappStatusDownloader.utils.UtilsMethod
import java.io.File

class SavedStatusAdapter(activity: Activity, statusList: MutableList<File>) :
    RecyclerView.Adapter<SavedStatusAdapter.ViewHolder>() {

    private lateinit var binding: SavedStatusBinding
    var activity: Activity
    var itemWidth: Int
    private  var statusList : MutableList<File>

    init {
        this.activity = activity
        this.statusList = statusList
        itemWidth = UtilsMethod.getScreenWidth(activity, 44)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = SavedStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setUpViews(position)
        holder.setStatusImage(position)
        holder.setOnClickListener(position)
    }


    override fun getItemCount(): Int {

        return statusList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {


        fun setUpViews(position: Int) {

            binding.apply {
                clRoot.layoutParams.width = itemWidth

                if (UtilsMethod.isVideoFile(statusList[position].path))
                    ivVideo.visibility = View.VISIBLE
                else
                    ivVideo.visibility = View.GONE

            }
        }

        fun setStatusImage(position: Int) {

            if (UtilsMethod.isVideoFile(statusList[position].path)) {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(statusList[position].path)
                    val bitmap = retriever.getFrameAtTime(1)

                    Glide.with(activity).load(bitmap)
                        .placeholder(ColorDrawable(Color.WHITE))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivStatus)

                } catch (e: Exception) {

                }


            } else {
                Glide.with(activity).load(statusList[position])
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivStatus)
            }
        }

        fun setOnClickListener(position: Int) {
            binding.apply {
                ivShareStatus.setOnClickListener() {
                    FileOperation.shareFile(activity, statusList[position])
                }

                ivDelete.setOnClickListener() {
                    showDeleteDialog(position)
                }

                ivStatus.setOnClickListener() {

                    val bundle = Bundle()
                    bundle.putSerializable(Constants.MEDIA_PATH_KEY, statusList[position])
                    activity.findNavController(R.id.nav_controller)
                        .navigate(R.id.show_media_fragment, bundle)


                }
            }
        }

    }


    fun showDeleteDialog(position: Int) {

        val deleteDialog = AlertDialog.Builder(activity)
            .setTitle("حذف پست")
            .setMessage("آیا میخواید این پست را حذف کنید؟")
            .setCancelable(true)
            .setPositiveButton("بله") { _, _ -> deleteStatus(position) }
            .setNegativeButton("خیر") { dialog, _ -> dialog.cancel() }
            .create()
            .show()

    }


    private fun deleteStatus(position: Int) {

        FileOperation.deleteFile(activity, statusList[position])
        statusList.removeAt(position)
        notifyItemRemoved(position)

    }


}