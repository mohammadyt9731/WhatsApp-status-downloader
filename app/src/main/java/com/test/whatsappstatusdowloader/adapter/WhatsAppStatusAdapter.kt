package com.test.whatsappstatusdowloader.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Environment
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
import java.lang.Exception


class WhatsAppStatusAdapter(activity: Activity, statusFileList: ArrayList<File>) :
    RecyclerView.Adapter<WhatsAppStatusAdapter.ViewHolder>() {


    var activity:Activity
    var statusFileList:ArrayList<File>
    var itemWidth:Int

    init {
        this.statusFileList=statusFileList
        this.activity=activity
        itemWidth=Utility().getScreenWidth(activity)*44/100

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_image_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.clRoot.layoutParams.width=itemWidth

        if (File(Constants.SAVE_DIRECTORY+statusFileList[position].name).exists()) {
            holder.ivSaveStatus.setImageResource(R.drawable.ic_download_3)
        }


        holder.ivStatus.load(statusFileList.get(position)) {
            crossfade(true)
            placeholder(ColorDrawable(Color.WHITE))

        }

        holder.ivShareStatus.setOnClickListener(){
            MyIntent.sharePhoto(activity, statusFileList[position].absolutePath)
        }

        holder.ivSaveStatus.setOnClickListener(){

            val sourceFile=File(statusFileList[position].path)
            val destinationFile=File(Constants.SAVE_DIRECTORY+statusFileList[position].name)

            if (destinationFile.exists()){
                Toast.makeText(activity,"قبلا ذخیره شده است.",Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }



            try {
                sourceFile.copyTo(destinationFile)
                Toast.makeText(activity,"در پوشه saveDirectory ذخیره شد",Toast.LENGTH_SHORT).show()
                holder.ivSaveStatus.setImageResource(R.drawable.ic_download_3)
            }catch (e:Exception){
                Toast.makeText(activity,activity.getString(R.string.unknown_error),Toast.LENGTH_SHORT).show()
                Log.i("123321",e.message.toString())
            }



        }
    }



    override fun getItemCount(): Int {

        return statusFileList.size
    }

     class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var ivStatus:ImageView
        var ivShareStatus:ImageView
        var ivSaveStatus:ImageView
        var clRoot:ConstraintLayout


        init {
            ivStatus= view.findViewById(R.id.iv_status)
            ivShareStatus= view.findViewById(R.id.iv_share_status)
            ivSaveStatus= view.findViewById(R.id.iv_save_status)
            clRoot=view.findViewById(R.id.cl_root)

     }

    }
}