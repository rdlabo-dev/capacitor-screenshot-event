# @rdlabo/capacitor-screenshot-event

<!-- rdlabo-docs-omit -->
[![npm version](https://badge.fury.io/js/@rdlabo%2Fcapacitor-screenshot-event.svg)](https://badge.fury.io/js/@rdlabo%2Fcapacitor-screenshot-event)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
<!-- /rdlabo-docs-omit -->

Notify your Capacitor app after the user takes a screenshot.

Use the event for post-capture guidance or in-app UI updates (for example a toast or analytics log). The notification arrives after a screenshot is taken; it does not protect or blur content before capture.

<!-- rdlabo-docs-omit -->
**Full documentation:** [https://docs.rdlabo.dev/projects/capacitor-screenshot-event](https://docs.rdlabo.dev/projects/capacitor-screenshot-event)
<!-- /rdlabo-docs-omit -->

## Install

```bash
npm install @rdlabo/capacitor-screenshot-event
npx cap sync
```

## Usage

See [ScreenshotEvent](./docs/screenshot-event.md) to register a listener, start watching, confirm one physical screenshot on a device, then stop and remove the handle.

<!-- rdlabo-docs-omit -->
Register a listener, start watching, take a screenshot on a physical device, then stop watching and remove the handle when leaving or destroying the screen:

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
};

const stop = async () => {
  await ScreenshotEvent.removeWatchEvent();
  await handle?.remove();
  handle = undefined;
};
```

<!-- /rdlabo-docs-omit -->

## Platform notes

- **iOS**: Uses the `UIApplication.userDidTakeScreenshotNotification` notification.
- **Android** (8.0.0): Watches `FileObserver.CREATE` on the fixed path `Pictures/Screenshots/` under external storage. Detection depends on screenshots being saved to that directory; it is not a MediaStore change observer and is not guaranteed on every Android device or OEM gallery path.
- **Web**: Not supported because browsers do not expose screenshot events.

## API

<docgen-index>

* [`startWatchEvent()`](#startwatchevent)
* [`removeWatchEvent()`](#removewatchevent)
* [`addListener('userDidTakeScreenshot', ...)`](#addlisteneruserdidtakescreenshot-)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startWatchEvent()

```typescript
startWatchEvent() => Promise<void>
```

--------------------


### removeWatchEvent()

```typescript
removeWatchEvent() => Promise<void>
```

--------------------


### addListener('userDidTakeScreenshot', ...)

```typescript
addListener(eventName: 'userDidTakeScreenshot', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                 |
| ------------------ | ------------------------------------ |
| **`eventName`**    | <code>'userDidTakeScreenshot'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>           |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>

<!-- rdlabo-docs-omit -->
## Maintainers

- [rdlabo](https://rdlabo.dev/)
<!-- /rdlabo-docs-omit -->

<!-- rdlabo-docs-omit -->
## License

This project is licensed under the [MIT License](./LICENSE).
<!-- /rdlabo-docs-omit -->
