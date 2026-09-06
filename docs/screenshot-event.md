# ScreenshotEvent

`ScreenshotEvent` watches for screenshot activity. Call this after [Installation](/docs/readme#installation). Register `addListener` before `startWatchEvent` so the first screenshot is not missed. Keep watching while you need notifications, confirm a physical screenshot on a device, then stop watching and remove the handle when leaving or destroying the screen.

## Watch lifecycle

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';
import type { PluginListenerHandle } from '@capacitor/core';

let handle: PluginListenerHandle | undefined;

const start = async () => {
  if (handle) return;
  handle = await ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
    console.log('Screenshot was taken');
  });

  await ScreenshotEvent.startWatchEvent();
  // Take a physical screenshot on the device and confirm the listener runs.
};

const stop = async () => {
  await ScreenshotEvent.removeWatchEvent();
  await handle?.remove();
  handle = undefined;
};
```

Call `start` when the screen becomes active and await `stop` when leaving or destroying it. Do not register a listener only to remove it immediately.

See [API](/docs/api) for the watcher and listener signatures.
