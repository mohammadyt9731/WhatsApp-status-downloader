package com.test.whatsappstatusdowloader.adapter

import android.app.Activity
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.test.whatsappstatusdowloader.R
import com.test.whatsappstatusdowloader.utility.Utility


class WhatsAppStatusAdapter(activity: Activity) :
    RecyclerView.Adapter<WhatsAppStatusAdapter.ViewHolder>() {


    var activity:Activity
    var itemWidth:Int

    init {
        this.activity=activity
        itemWidth=Utility().getScreenWidth(activity)*40/100

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_image_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.clRoot.layoutParams.width=itemWidth

      //  holder.ivStatus.setImageResource(R.drawable.ic_whats_app)
    }

    override fun getItemCount(): Int {

        return 200
    }

     class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var ivStatus:ImageView
        var clRoot:ConstraintLayout


        init {
            ivStatus= view.findViewById(R.id.iv_status)
            clRoot=view.findViewById(R.id.cl_root)

     }

    }
}