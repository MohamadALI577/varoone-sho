package com.gabasstudio.varoone;

import android.app.Activity;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;

@CapacitorPlugin(name = "Tapsell")
public class TapsellPlugin extends Plugin {

    private static final String APP_KEY = "gdhscnltdkcpogoiaordilgklofikahpdibdjkceqfitkrqdnndlntdpsogmolcobsqitp";
    private static final String ZONE_ID = "6a416d364d35e6311e8779a2";
    private static final String REWARD_ZONE_ID = "6a42ffff90ea0065a20547e7";
    private String cachedAdId = null;
    private String cachedRewardAdId = null;

    @Override
    public void load() {
        Activity activity = getActivity();
        Tapsell.initialize(activity.getApplication(), APP_KEY);
        preloadAd();
        preloadRewardAd();
    }

    private void preloadAd() {
        Activity activity = getActivity();
        Tapsell.requestAd(activity, ZONE_ID,
            new TapsellAdRequestOptions(),
            new TapsellAdRequestListener() {
                @Override
                public void onAdAvailable(String adId) {
                    cachedAdId = adId;
                }
                @Override
                public void onError(String message) {
                    cachedAdId = null;
                }
            });
    }

    private void preloadRewardAd() {
        Activity activity = getActivity();
        Tapsell.requestAd(activity, REWARD_ZONE_ID,
            new TapsellAdRequestOptions(),
            new TapsellAdRequestListener() {
                @Override
                public void onAdAvailable(String adId) {
                    cachedRewardAdId = adId;
                }
                @Override
                public void onError(String message) {
                    cachedRewardAdId = null;
                }
            });
    }

    @PluginMethod
    public void showInterstitial(PluginCall call) {
        Activity activity = getActivity();
        if (cachedAdId == null) {
            call.resolve();
            preloadAd();
            return;
        }
        String adId = cachedAdId;
        cachedAdId = null;
        activity.runOnUiThread(() ->
            Tapsell.showAd(activity, ZONE_ID, adId,
                new TapsellAdShowListener() {
                    @Override public void onOpened() {}
                    @Override
                    public void onClosed() {
                        call.resolve();
                        preloadAd();
                    }
                    @Override
                    public void onError(String message) {
                        call.resolve();
                        preloadAd();
                    }
                    @Override
                    public void onRewarded(boolean completed) {}
                })
        );
    }

    @PluginMethod
    public void showRewardAd(PluginCall call) {
        Activity activity = getActivity();
        if (cachedRewardAdId == null) {
            call.resolve();
            preloadRewardAd();
            return;
        }
        String adId = cachedRewardAdId;
        cachedRewardAdId = null;
        activity.runOnUiThread(() ->
            Tapsell.showAd(activity, REWARD_ZONE_ID, adId,
                new TapsellAdShowListener() {
                    @Override public void onOpened() {}
                    @Override
                    public void onClosed() {
                        call.resolve();
                        preloadRewardAd();
                    }
                    @Override
                    public void onError(String message) {
                        call.resolve();
                        preloadRewardAd();
                    }
                    @Override
                    public void onRewarded(boolean completed) {
                        // تبلیغ جایزه‌دار کامل تماشا شد
                    }
                })
        );
    }
}
