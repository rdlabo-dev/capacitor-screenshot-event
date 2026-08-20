import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ScreenshotEventPlugin)
public class ScreenshotEventPlugin: CAPPlugin, CAPBridgedPlugin {
    private var isWatching = false
    private var isScreenCaptured = false

    public let identifier = "ScreenshotEventPlugin"
    public let jsName = "ScreenshotEvent"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "startWatchEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeWatchEvent", returnType: CAPPluginReturnPromise),
    ]
    @objc func startWatchEvent(_ call: CAPPluginCall) {
        guard !isWatching else {
            call.resolve()
            return
        }

        isWatching = true
        isScreenCaptured = UIScreen.main.isCaptured
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.didTakeScreenshot(notification:)),
            name: UIApplication.userDidTakeScreenshotNotification,
            object: nil)
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.screenCaptureDidChange(notification:)),
            name: UIScreen.capturedDidChangeNotification,
            object: UIScreen.main)

        call.resolve()
    }

    @objc func removeWatchEvent(_ call: CAPPluginCall) {
        stopWatching()
        call.resolve()
    }

    @objc func didTakeScreenshot(notification: Notification) {
        NSLog("userDidTakeScreenshot")
        self.notifyListeners("userDidTakeScreenshot", data: [:])
    }

    @objc func screenCaptureDidChange(notification: Notification) {
        guard UIScreen.main.isCaptured != isScreenCaptured else { return }

        isScreenCaptured = UIScreen.main.isCaptured
        let eventName = isScreenCaptured ? "screenCaptureStarted" : "screenCaptureStopped"
        NSLog("%@", eventName)
        notifyListeners(eventName, data: [:])
    }

    deinit {
        stopWatching()
    }

    private func stopWatching() {
        guard isWatching else { return }
        NotificationCenter.default.removeObserver(self)
        isWatching = false
        isScreenCaptured = false
    }
}
