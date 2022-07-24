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
import com.ddt.whatsappStatusDownloader.utils.*
import java.io.File

class SavedStatusAdapter(var activity: Activity, private var statusList: MutableList<File>) :
    RecyclerView.Adapter<SavedStatusAdapter.ViewHolder>() {

    private lateinit var binding: SavedStatusBinding
    var itemWidth: Int = UtilsMethod.getScreenWidth(activity, Constants.PERCENTAGE_OF_WIDTH)

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
                //root width
                clRoot.layoutParams.width = itemWidth

                //check video file
                if (UtilsMethod.isVideoFile(statusList[position].path))
                    ivVideo.visible()
                else
                    ivVideo.gone()

            }
        }

        fun setStatusImage(position: Int) {
            //video
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

            //image
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
                    activity.findNavController(R.id.nav_host)
                        .navigate(R.id.show_media_fragment, bundle)
                }
            }
        }

    }


    fun showDeleteDialog(position: Int) {

        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.delete_status))
            .setMessage(activity.getString(R.string.do_you_want_to_delete))
            .setCancelable(true)
            .setPositiveButton(activity.getString(R.string.yes)) { _, _ -> deleteStatus(position) }
            .setNegativeButton(activity.getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            .create()
            .show()

    }

    private fun deleteStatus(position: Int) {

        FileOperation.deleteFile(activity, statusList[position])
        statusList.removeAt(position)
        notifyItemRemoved(position)

    }

}