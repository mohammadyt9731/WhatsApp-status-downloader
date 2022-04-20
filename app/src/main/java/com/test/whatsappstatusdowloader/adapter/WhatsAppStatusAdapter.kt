package com.test.whatsappstatusdowloader.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.utility.Constants
import com.test.whatsappstatusdowloader.utility.MyIntent
import com.test.whatsappstatusdowloader.utility.Utility
import java.io.*


class WhatsAppStatusAdapter(activity: Activity, statusFileList: ArrayList<File>) :
    RecyclerView.Adapter<WhatsAppStatusAdapter.ViewHolder>() {


    var activity:Activity
    var statusFileList:ArrayList<File>
    var itemWidth:Int

    init {
        this.statusFileList=statusFileList
        this.activity=activity
        itemWidth=Utility().getScreenWidth(activity)*40/100

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_image_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.clRoot.layoutParams.width=itemWidth



        holder.ivStatus.load(statusFileList.get(position)) {
            crossfade(true)
            placeholder(ColorDrawable(Color.WHITE))

        }

        holder.ivShareStatus.setOnClickListener(){
            MyIntent.sharePhoto(activity, statusFileList[position].absolutePath)
        }

        holder.ivSaveStatus.setOnClickListener(){


            val file=statusFileList.get(position)
            val destinationFile=File(Constants.SAVE_DIRECTORY+file.name)
            if(!destinationFile.exists())
                destinationFile.mkdirs()



//            Log.i("123321",destinationFile.toString())
//            Log.i("123321",file.toString())


            val source = statusFileList.get(position)

            val destinationPath =
               Constants.SAVE_DIRECTORY+statusFileList.get(position).name
            val destination = File(destinationPath)

            Log.i("123321",source.toString())
            Log.i("123321",destinationPath.toString())
            try {

           source.renameTo(destination)
               // FileUtils.copy(FileInputStream(source),  FileOutputStream(destination))
            } catch (e: IOException) {
                e.printStackTrace()
            }


        }



      //  holder.ivStatus.setImageResource(R.drawable.ic_sample_status)


//        Glide.with(activity)
//            .load(pathList.get(position))
//            .placeholder(R.drawable.ic_whats_app)
//            .into(holder.ivStatus);

    //  Glide.with(activity).load(R.drawable.ic_sample_status).into(holder.ivStatus)

       // holder.ivStatus.setImageResource(R.drawable.ic_sample_status)
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