package jp.rdlabo.capacitor.plugin.screenshotevent;

import android.app.Activity;
import android.os.Environment;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "ScreenshotEvent")
public class ScreenshotEventPlugin extends Plugin {

    private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/";
    private ScreenshotEvent screenshotEvent = new ScreenshotEvent(PATH, this::notifyListeners);

    @PluginMethod
    public void startWatchEvent(PluginCall call) {
        Activity activity = getActivity();
        screenshotEvent.startWatching(activity);
        call.resolve();
    }

    @PluginMethod
    public void removeWatchEvent(PluginCall call) {
        Activity activity = getActivity();
        screenshotEvent.stopWatching(activity);
        call.resolve();
    }
}
