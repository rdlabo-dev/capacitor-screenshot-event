package jp.rdlabo.capacitor.plugin.screenshotevent;

import android.app.Activity;
import android.os.Build;
import android.os.FileObserver;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.google.android.gms.common.util.BiConsumer;

public class ScreenshotEvent {

    private final BiConsumer<String, JSObject> notifyListenersFunction;
    private final JSObject emptyObject = new JSObject();

    private final String filePath;
    private FileObserver fileObserver;

    private boolean watching = false;
    private Activity registeredActivity;
    private Object screenCaptureCallback;

    public ScreenshotEvent(String filePath, BiConsumer<String, JSObject> notifyListenersFunction) {
        this.filePath = filePath;
        this.notifyListenersFunction = notifyListenersFunction;
    }

    protected void notifyListeners(String eventName, JSObject data) {
        notifyListenersFunction.accept(eventName, data);
    }

    public void startWatching(Activity activity) {
        if (watching) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            if (activity == null) return;
            // Android 14+ provides a privacy-preserving, per-Activity callback.
            screenCaptureCallback = Api34ScreenCapture.register(activity, this::notifyScreenshotTaken);
            registeredActivity = activity;
            watching = true;
            return;
        }

        // Older Android: best-effort via fixed directory observer.
        fileObserver = new FileObserver(filePath) {
            @Override
            public void onEvent(int event, String path) {
                if (event == FileObserver.CREATE) {
                    notifyListeners("userDidTakeScreenshot", emptyObject);
                    Log.i("ScreenshotEvent", "FileObserver CREATE: " + path);
                }
            }
        };
        fileObserver.startWatching();
        watching = true;
    }

    public void stopWatching() {
        if (!watching) return;
        watching = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            if (registeredActivity != null && screenCaptureCallback != null) {
                Api34ScreenCapture.unregister(registeredActivity, screenCaptureCallback);
                registeredActivity = null;
                screenCaptureCallback = null;
            }
            return;
        }

        if (fileObserver != null) {
            fileObserver.stopWatching();
            fileObserver = null;
        }
    }

    private void notifyScreenshotTaken() {
        notifyListeners("userDidTakeScreenshot", emptyObject);
        Log.i("ScreenshotEvent", "onScreenCaptured()");
    }

    @android.annotation.TargetApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private static final class Api34ScreenCapture {

        private static Object register(Activity activity, Runnable onScreenCaptured) {
            Activity.ScreenCaptureCallback callback = onScreenCaptured::run;
            activity.registerScreenCaptureCallback(activity.getMainExecutor(), callback);
            return callback;
        }

        private static void unregister(Activity activity, Object callback) {
            activity.unregisterScreenCaptureCallback((Activity.ScreenCaptureCallback) callback);
        }
    }
}
