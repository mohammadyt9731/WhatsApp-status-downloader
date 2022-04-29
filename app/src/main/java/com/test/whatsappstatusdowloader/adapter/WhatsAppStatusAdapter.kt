package com.test.whatsappstatusdowloader.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.activity.ShowMediaActivity
import com.test.whatsappstatusdowloader.utils.Constants
import com.test.whatsappstatusdowloader.utils.MyIntent
import com.test.whatsappstatusdowloader.utils.Utils
import java.io.File


class WhatsAppStatusAdapter(
    activity: Activity,
    statusFileList: ArrayList<File>,
    directoryAddress: String
) :
    RecyclerView.Adapter<WhatsAppStatusAdapter.ViewHolder>() {


    var activity: Activity
    var statusFileList: ArrayList<File>
    var directoryAddress: String
    var itemWidth: Int

    init {
        this.statusFileList = statusFileList
        this.activity = activity
        this.directoryAddress = directoryAddress
        itemWidth = Utils.getScreenWidth(activity) * 44 / 100

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_image_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.setUpViews(position)
        holder.setStatusImage(position)
        holder.setOnClickListener(position)


    }


    override fun getItemCount(): Int {

        return statusFileList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var ivStatus: ImageView
        var ivShareStatus: ImageView
        var ivActionOnStatus: ImageView
        var ivVideo: ImageView
        var clRoot: ConstraintLayout
        var lottieDownload: LottieAnimationView


        init {
            ivStatus = view.findViewById(R.id.iv_status)
            ivShareStatus = view.findViewById(R.id.iv_share_status)
            ivActionOnStatus = view.findViewById(R.id.iv_action_on_status)
            ivVideo = view.findViewById(R.id.iv_video)
            clRoot = view.findViewById(R.id.cl_root)
            lottieDownload = view.findViewById(R.id.lottie_download)

        }

        fun setUpViews(position: Int) {

            clRoot.layoutParams.width = itemWidth


            if (Utils.isVideoFile(statusFileList[position].path))
                ivVideo.visibility = View.VISIBLE
            else
                ivVideo.visibility = View.GONE



            if (isInSavedStatusPage()) {
                ivActionOnStatus.setImageResource(R.drawable.ic_delete)
                ivActionOnStatus.visibility = View.VISIBLE
                lottieDownload.visibility = View.INVISIBLE

            } else {
                ivActionOnStatus.visibility = View.INVISIBLE
                lottieDownload.visibility = View.VISIBLE
                if (File(Constants.SAVED_DIRECTORY + "/" + statusFileList[position].name).exists()) {
                    lottieDownload.frame = Constants.LOTTIE_END_FRAME

                    //    ivActionOnStatus.setImageResource(R.drawable.ic_download_3)
                } else
                    lottieDownload.frame = Constants.LOTTIE_START_FRAME
                //   ivActionOnStatus.setImageResource(R.drawable.ic_download_2)

            }
        }

        fun setStatusImage(position: Int) {

            if (Utils.isVideoFile(statusFileList[position].path)) {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(statusFileList[position].path)
                    val bitmap = retriever.getFrameAtTime(1)

                    Glide.with(activity).load(bitmap)
                        .placeholder(ColorDrawable(Color.WHITE))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(ivStatus)
                } catch (e: Exception) {

                }


            } else {

                Glide.with(activity).load(statusFileList[position])
                    .placeholder(ColorDrawable(Color.WHITE))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivStatus)


            }
        }

        fun setOnClickListener(position: Int) {

            ivShareStatus.setOnClickListener() {
                MyIntent.sharePhoto(activity, statusFileList[position].absolutePath)
            }

            ivActionOnStatus.setOnClickListener() {

                if (isInSavedStatusPage()) {

                    showDeleteDialog(position)

                } else {

                    val sourceFile = File(statusFileList[position].path)
                    val destinationFile =
                        File(Constants.SAVED_DIRECTORY + "/" + statusFileList[position].name)

                    if (destinationFile.exists()) {
                        Toast.makeText(activity, "قبلا ذخیره شده است.", Toast.LENGTH_SHORT).show()
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
                        ivActionOnStatus.setImageResource(R.drawable.ic_download_3)
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

            ivStatus.setOnClickListener() {

                val intent = Intent(activity, ShowMediaActivity::class.java)
                intent.putExtra(Constants.MEDIA_PATH_KEY, statusFileList[position])
                activity.startActivity(intent)

            }

            lottieDownload.setOnClickListener() {

                if (isInSavedStatusPage()) {

                    showDeleteDialog(position)

                } else {

                    val sourceFile = File(statusFileList[position].path)
                    val destinationFile =
                        File(Constants.SAVED_DIRECTORY + "/" + statusFileList[position].name)

                    if (destinationFile.exists()) {
                        Toast.makeText(activity, "قبلا ذخیره شده است.", Toast.LENGTH_SHORT).show()
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


    fun isInSavedStatusPage(): Boolean {
        return directoryAddress.equals(Constants.SAVED_DIRECTORY)
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
        try {
            statusFileList[position].delete()
            statusFileList.removeAt(position)
            notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(activity, e.message.toString(), Toast.LENGTH_LONG).show()
        }

    }


}