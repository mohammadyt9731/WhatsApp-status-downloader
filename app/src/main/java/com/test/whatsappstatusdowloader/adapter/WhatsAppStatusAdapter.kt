package com.test.whatsappstatusdowloader.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.utility.Constants
import com.test.whatsappstatusdowloader.utility.MyIntent
import com.test.whatsappstatusdowloader.utility.Utility
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
        itemWidth = Utility().getScreenWidth(activity) * 44 / 100

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
        var clRoot: ConstraintLayout


        init {
            ivStatus = view.findViewById(R.id.iv_status)
            ivShareStatus = view.findViewById(R.id.iv_share_status)
            ivActionOnStatus = view.findViewById(R.id.iv_action_on_status)
            clRoot = view.findViewById(R.id.cl_root)

        }

        fun setUpViews(position: Int) {

            clRoot.layoutParams.width = itemWidth

            if (isInSavedStatusPage()) {
                ivActionOnStatus.setImageResource(R.drawable.ic_delete)

            } else {

                if (File(Constants.SAVED_DIRECTORY + "/" + statusFileList[position].name).exists()) {
                    ivActionOnStatus.setImageResource(R.drawable.ic_download_3)
                }else
                    ivActionOnStatus.setImageResource(R.drawable.ic_download_2)

            }
        }

        fun setStatusImage(position: Int) {

            ivStatus.load(statusFileList[position]) {
                crossfade(true)
                placeholder(ColorDrawable(Color.WHITE))
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
                        Toast.makeText(activity,e.message.toString(),Toast.LENGTH_LONG).show()
                    }


                }
            }
        }

    }

    fun isInSavedStatusPage():Boolean{
        return directoryAddress.equals(Constants.SAVED_DIRECTORY)
    }

    fun showDeleteDialog(position: Int){

        val deleteDialog=AlertDialog.Builder(activity)
            .setTitle("حذف پست")
            .setMessage("آیا میخواید این پست را حذف کنید؟")
            .setCancelable(true)
            .setPositiveButton("بله") { _, _ -> deleteStatus(position) }
            .setNegativeButton("خیر") { dialog,_  -> dialog.cancel() }
            .create()
            .show()

    }

    private fun deleteStatus(position: Int){
        try {
            statusFileList[position].delete()
            statusFileList.removeAt(position)
            notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(activity,e.message.toString(),Toast.LENGTH_LONG).show()
        }

    }
}