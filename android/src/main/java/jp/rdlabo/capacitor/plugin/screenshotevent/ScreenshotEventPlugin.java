package jp.rdlabo.capacitor.plugin.screenshotevent;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.view.WindowManager;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.function.Consumer;

@CapacitorPlugin(name = "ScreenshotEvent")
public class ScreenshotEventPlugin extends Plugin {

    private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/";
    private final ScreenshotEvent screenshotEvent = new ScreenshotEvent(PATH, this::notifyListeners);
    private Consumer<Integer> screenRecordingCallback;
    private WindowManager screenRecordingWindowManager;
    private boolean isWatching;

    @PluginMethod
    public void startWatchEvent(PluginCall call) {
        if (isWatching) {
            call.resolve();
            return;
        }

        Activity activity = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && activity == null) {
            call.reject("Activity is unavailable");
            return;
        }

        screenshotEvent.startWatching(activity);
        startWatchingScreenRecording(activity);
        isWatching = true;
        call.resolve();
    }

    @PluginMethod
    public void removeWatchEvent(PluginCall call) {
        stopWatching();
        call.resolve();
    }

    @Override
    protected void handleOnDestroy() {
        stopWatching();
        super.handleOnDestroy();
    }

    private void startWatchingScreenRecording(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            return;
        }

        screenRecordingWindowManager = activity.getWindowManager();
        screenRecordingCallback = (state) -> {
            String eventName = state == WindowManager.SCREEN_RECORDING_STATE_VISIBLE ? "screenCaptureStarted" : "screenCaptureStopped";
            notifyListeners(eventName, new JSObject());
        };
        screenRecordingWindowManager.addScreenRecordingCallback(getContext().getMainExecutor(), screenRecordingCallback);
    }

    private void stopWatching() {
        if (!isWatching) {
            return;
        }

        screenshotEvent.stopWatching();
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM &&
            screenRecordingWindowManager != null &&
            screenRecordingCallback != null
        ) {
            screenRecordingWindowManager.removeScreenRecordingCallback(screenRecordingCallback);
            screenRecordingCallback = null;
            screenRecordingWindowManager = null;
        }
        isWatching = false;
    }
}
