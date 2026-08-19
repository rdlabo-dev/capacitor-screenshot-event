package jp.rdlabo.capacitor.plugin.screenshotevent;

import android.app.Activity;
import android.os.Build;
import android.os.FileObserver;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.google.android.gms.common.util.BiConsumer;

public class ScreenshotEvent {

    protected BiConsumer<String, JSObject> notifyListenersFunction;
    private final JSObject emptyObject = new JSObject();

    private final String filepath;
    private FileObserver fileObserver;

    private boolean watching = false;
    private final Activity.ScreenCaptureCallback screenCaptureCallback;

    public ScreenshotEvent(String filepath, BiConsumer<String, JSObject> notifyListenersFunction) {
        this.filepath = filepath;
        this.notifyListenersFunction = notifyListenersFunction;
        this.screenCaptureCallback =
            new Activity.ScreenCaptureCallback() {
                @Override
                public void onScreenCaptured() {
                    notifyListeners("userDidTakeScreenshot", emptyObject);
                    Log.i("ScreenshotEvent", "onScreenCaptured()");
                }
            };
    }

    protected void notifyListeners(String eventName, JSObject data) {
        notifyListenersFunction.accept(eventName, data);
    }

    public void startWatching(Activity activity) {
        if (watching) return;
        watching = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            if (activity == null) return;
            // Android 14+ provides a privacy-preserving, per-Activity callback.
            activity.registerScreenCaptureCallback(activity.getMainExecutor(), screenCaptureCallback);
            return;
        }

        // Older Android: best-effort via fixed directory observer.
        fileObserver =
            new FileObserver(filepath) {
                @Override
                public void onEvent(int event, String path) {
                    if (event == FileObserver.CREATE) {
                        notifyListeners("userDidTakeScreenshot", emptyObject);
                        Log.i("ScreenshotEvent", "FileObserver CREATE: " + path);
                    }
                }
            };
        fileObserver.startWatching();
    }

    public void stopWatching(Activity activity) {
        if (!watching) return;
        watching = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            if (activity == null) return;
            activity.unregisterScreenCaptureCallback(screenCaptureCallback);
            return;
        }

        if (fileObserver != null) {
            fileObserver.stopWatching();
            fileObserver = null;
        }
    }
}
