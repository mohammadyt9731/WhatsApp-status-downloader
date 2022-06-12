package com.ddt.whatsappStatusDownloader.utils

import android.app.Activity
import android.view.ViewGroup
import android.widget.RelativeLayout
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.TapsellPlusBannerType
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel

object MyTapsell {

    private const val MAX_REQUEST_NUMBER = 10
    private var standardAdCounter = 0


    fun showInterstitialAd(activity: Activity?, zoneId: String?) {
        TapsellPlus.requestRewardedVideoAd(
            activity,
            zoneId,
            object : AdRequestCallback() {
                override fun response(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.response(tapsellPlusAdModel)
                    val responseId = tapsellPlusAdModel.responseId
                    TapsellPlus.showRewardedVideoAd(activity, responseId,
                        object : AdShowListener() {
                            override fun onOpened(tapsellPlusAdModel: TapsellPlusAdModel) {
                                super.onOpened(tapsellPlusAdModel)
                            }

                            override fun onClosed(tapsellPlusAdModel: TapsellPlusAdModel) {
                                super.onClosed(tapsellPlusAdModel)
                            }

                            override fun onRewarded(tapsellPlusAdModel: TapsellPlusAdModel) {
                                super.onRewarded(tapsellPlusAdModel)
                            }

                            override fun onError(tapsellPlusErrorModel: TapsellPlusErrorModel) {
                                super.onError(tapsellPlusErrorModel)
                            }
                        })
                }

                override fun error(message: String) {}
            })
    }

    fun showStandardBanner(
        activity: Activity,
        zoneId: String?,
        relativeLayout: RelativeLayout,
        bannerType: TapsellPlusBannerType?
    ) {
        TapsellPlus.requestStandardBannerAd(
            activity, zoneId,
            bannerType,
            object : AdRequestCallback() {
                override fun response(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.response(tapsellPlusAdModel)
                    standardAdCounter = MAX_REQUEST_NUMBER
                    val standardBannerResponseId = tapsellPlusAdModel.responseId
                    requestStandardBanner(activity, standardBannerResponseId, relativeLayout)
                }

                override fun error(message: String) {
                    while (standardAdCounter++ < MAX_REQUEST_NUMBER) {
                        showStandardBanner(activity, zoneId, relativeLayout, bannerType)
                    }
                }
            })
    }

    private fun requestStandardBanner(
        activity: Activity,
        standardBannerResponseId: String,
        relativeLayout: ViewGroup
    ) {
        TapsellPlus.showStandardBannerAd(activity, standardBannerResponseId,
            relativeLayout,
            object : AdShowListener() {
                override fun onOpened(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.onOpened(tapsellPlusAdModel)
                }

                override fun onError(tapsellPlusErrorModel: TapsellPlusErrorModel) {
                    super.onError(tapsellPlusErrorModel)
                }
            })
    }

}