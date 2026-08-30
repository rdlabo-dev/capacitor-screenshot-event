# ScreenshotEvent

`ScreenshotEvent` watches for screenshot activity. Call this after [Installation](/docs/readme#installation). Register `addListener` before `startWatchEvent` so the first screenshot is not missed.

## addListener

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

const handle = await ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
  // Notice take screenshot
});

await handle.remove();
```

<!-- !::addListener.userDidTakeScreenshot:: -->

<!-- !::PluginListenerHandle:: -->

## startWatchEvent

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
  // Notice take screenshot
});

ScreenshotEvent.startWatchEvent();
```

<!-- !::startWatchEvent:: -->

### Android 14+ note

On Android 14 (API 34) and later, this plugin uses the system privacy-preserving
`Activity.ScreenCaptureCallback` API. Detection is only triggered while the current
`Activity` is visible, and older Android versions are treated as best-effort.

The system may show a notification when screenshot detection is triggered.

## removeWatchEvent

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

ScreenshotEvent.removeWatchEvent();
```

<!-- !::removeWatchEvent:: -->
