# ScreenshotEvent

`ScreenshotEvent` watches for screenshot activity. Call this after [Installation](/docs/readme#installation). Register `addListener` before `startWatchEvent` so the first screenshot is not missed.

## addListener

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

const handle = await ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
  // Notice take screenshot
});

const captureStarted = await ScreenshotEvent.addListener('screenCaptureStarted', () => {
  // Notice screen capture start
});

const captureStopped = await ScreenshotEvent.addListener('screenCaptureStopped', () => {
  // Notice screen capture stop
});

await handle.remove();
await captureStarted.remove();
await captureStopped.remove();
```

## startWatchEvent

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
  // Notice take screenshot
});

ScreenshotEvent.startWatchEvent();
```

On iOS, screen capture includes recording, mirroring, AirPlay, and other screen cloning. On Android,
screen capture events are available on Android 15 (API level 35) and later and indicate whether this
app is visible in a screen recording. Older Android versions continue to emit screenshot events only.
The `screenCaptureStarted` and `screenCaptureStopped` events report transitions after
`startWatchEvent()`; the current capture state is not emitted when watching starts.

### Android 14+ note

On Android 14 (API 34) and later, this plugin uses the system privacy-preserving
`Activity.ScreenCaptureCallback` API. Detection is only triggered while the current
`Activity` is visible, and older Android versions are treated as best-effort.

The system may show a notification when screenshot detection is triggered.

The Android library manifest declares the install-time permissions
`android.permission.DETECT_SCREEN_CAPTURE` and `android.permission.DETECT_SCREEN_RECORDING`.
They appear in the consuming app's merged manifest; neither permission requires a runtime prompt.

## removeWatchEvent

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

ScreenshotEvent.removeWatchEvent();
```

Signatures are on the [API](/docs/api) page.
