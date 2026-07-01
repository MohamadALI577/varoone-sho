package com.gabasstudio.varoone;

import android.app.Activity;
import com.getcapacitor.JSObject;
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

    private static final String APP_KEY =
        "gdhscnltdkcpogoiaordilgklofikahpdibdjkceqfitkrqdnndlntdpsogmolcobsqitp";
    private static final String ZONE_INTERSTITIAL = "6a416d364d35e6311e8779a2";
    private static final String ZONE_REWARD       = "6a42ffff90ea0065a20547e7";

    private String cachedInterstitialId = null;
    private String cachedRewardId       = null;

    @Override
    public void load() {
        Tapsell.initialize(getActivity().getApplication(), APP_KEY);
        preloadInterstitial();
        preloadReward();
    }

    private void preloadInterstitial() {
        Tapsell.requestAd(getActivity(), ZONE_INTERSTITIAL,
            new TapsellAdRequestOptions(),
            new TapsellAdRequestListener() {
                @Override public void onAdAvailable(String adId) {
                    cachedInterstitialId = adId;
                }
                @Override public void onError(String msg) {
                    cachedInterstitialId = null;
                }
            });
    }

    private void preloadReward() {
        Tapsell.requestAd(getActivity(), ZONE_REWARD,
            new TapsellAdRequestOptions(),
            new TapsellAdRequestListener() {
                @Override public void onAdAvailable(String adId) {
                    cachedRewardId = adId;
                }
                @Override public void onError(String msg) {
                    cachedRewardId = null;
                }
            });
    }

    @PluginMethod
    public void showInterstitial(PluginCall call) {
        if (cachedInterstitialId == null) {
            call.resolve();
            preloadInterstitial();
            return;
        }
        String adId = cachedInterstitialId;
        cachedInterstitialId = null;

        getActivity().runOnUiThread(() ->
            Tapsell.showAd(getActivity(), ZONE_INTERSTITIAL, adId, null,
                new TapsellAdShowListener() {
                    @Override public void onOpened() {}
                    @Override public void onClosed() {
                        call.resolve();
                        preloadInterstitial();
                    }
                    @Override public void onError(String msg) {
                        call.resolve();
                        preloadInterstitial();
                    }
                    @Override public void onRewarded(boolean c) {}
                })
        );
    }

    @PluginMethod
    public void showRewardAd(PluginCall call) {
        if (cachedRewardId == null) {
            JSObject r = new JSObject();
            r.put("completed", false);
            r.put("available", false);
            call.resolve(r);
            preloadReward();
            return;
        }
        String adId = cachedRewardId;
        cachedRewardId = null;

        final boolean[] rewarded = {false};

        getActivity().runOnUiThread(() ->
            Tapsell.showAd(getActivity(), ZONE_REWARD, adId, null,
                new TapsellAdShowListener() {
                    @Override public void onOpened() {}
                    @Override public void onClosed() {
                        JSObject r = new JSObject();
                        r.put("completed", rewarded[0]);
                        r.put("available", true);
                        call.resolve(r);
                        preloadReward();
                    }
                    @Override public void onError(String msg) {
                        JSObject r = new JSObject();
                        r.put("completed", false);
                        r.put("available", false);
                        call.resolve(r);
                        preloadReward();
                    }
                    @Override public void onRewarded(boolean c) {
                        if (c) rewarded[0] = true;
                    }
                })
        );
    }
}
