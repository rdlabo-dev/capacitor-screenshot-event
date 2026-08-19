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

## startWatchEvent

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
  // Notice take screenshot
});

ScreenshotEvent.startWatchEvent();
```

## removeWatchEvent

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

ScreenshotEvent.removeWatchEvent();
```

Signatures are on the [API](/docs/api) page.
