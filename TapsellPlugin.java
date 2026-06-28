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
    private String cachedAdId = null;

    @Override
    public void load() {
        Activity activity = getActivity();
        Tapsell.initialize(activity.getApplication(), APP_KEY);
        preloadAd();
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
}
