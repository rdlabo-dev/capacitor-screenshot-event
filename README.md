# @rdlabo/capacitor-screenshot-event

<!-- rdlabo-docs-omit -->
[![npm version](https://badge.fury.io/js/@rdlabo%2Fcapacitor-screenshot-event.svg)](https://badge.fury.io/js/@rdlabo%2Fcapacitor-screenshot-event)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
<!-- /rdlabo-docs-omit -->

Notify your Capacitor app when the user takes a screenshot or starts and stops screen capture.

This plugin listens for screenshot events on iOS and Android and delivers them to your web layer through a Capacitor event listener. It is useful for analytics, security prompts, or content-protection workflows.

<!-- rdlabo-docs-omit -->
**Full documentation:** [https://docs.rdlabo.dev/projects/capacitor-screenshot-event](https://docs.rdlabo.dev/projects/capacitor-screenshot-event)
<!-- /rdlabo-docs-omit -->

## Install

```bash
npm install @rdlabo/capacitor-screenshot-event
npx cap sync
```

## Usage

See [ScreenshotEvent](https://docs.rdlabo.dev/projects/capacitor-screenshot-event/docs/screenshot-event) to watch, handle, and stop screenshot events.

<!-- rdlabo-docs-omit -->
Start watching for screenshot events and register a listener:

```ts
import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';

const start = async () => {
  await ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
    console.log('Screenshot was taken');
  });

  await ScreenshotEvent.startWatchEvent();
};
```

Stop watching when the listener is no longer needed:

```ts
await ScreenshotEvent.removeWatchEvent();
```

<!-- /rdlabo-docs-omit -->

## When to use

Use this plugin when you want to react to screenshots in your app, for example:

- Show a confirmation or warning after a screenshot is taken.
- Log screenshot events for analytics or audit trails.
- Trigger UI changes, such as blurring sensitive content.

## Platform notes

- **iOS**: Uses the `UIApplication.userDidTakeScreenshotNotification` notification.
- **iOS screen capture**: Uses `UIScreen.capturedDidChangeNotification`. Capture includes screen
  recording, mirroring, AirPlay, and other forms of screen cloning.
- **Android screenshots**: Uses `Activity.ScreenCaptureCallback` on Android 14 (API level 34) and
  later. Older versions observe the screenshots directory as a best-effort fallback.
- **Android screen capture**: Available on Android 15 (API level 35) and later. Events indicate whether
  the app is visible in a screen recording. Older versions do not provide a reliable public API.
- **Screen capture event timing**: `screenCaptureStarted` and `screenCaptureStopped` report state
  transitions that occur after `startWatchEvent()`; the current state is not emitted on registration.
- **Android permissions**: The library declares the install-time permissions
  `android.permission.DETECT_SCREEN_CAPTURE` and `android.permission.DETECT_SCREEN_RECORDING`. They
  appear in the consuming app's merged manifest; neither permission requires a runtime prompt.
- **Web**: Not supported because browsers do not expose screenshot events.

## API

<docgen-index>

* [`startWatchEvent()`](#startwatchevent)
* [`removeWatchEvent()`](#removewatchevent)
* [`addListener('userDidTakeScreenshot', ...)`](#addlisteneruserdidtakescreenshot-)
* [`addListener('screenCaptureStarted', ...)`](#addlistenerscreencapturestarted-)
* [`addListener('screenCaptureStopped', ...)`](#addlistenerscreencapturestopped-)
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


### addListener('screenCaptureStarted', ...)

```typescript
addListener(eventName: 'screenCaptureStarted', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                |
| ------------------ | ----------------------------------- |
| **`eventName`**    | <code>'screenCaptureStarted'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>          |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### addListener('screenCaptureStopped', ...)

```typescript
addListener(eventName: 'screenCaptureStopped', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                |
| ------------------ | ----------------------------------- |
| **`eventName`**    | <code>'screenCaptureStopped'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>          |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>

<!-- rdlabo-docs-omit -->
## Prerelease channels

An open, non-draft pull request can be published to the npm `beta` dist-tag after its `Validation` and `Package Candidate` workflows pass. A repository owner or maintainer must add a comment whose entire body is:

```text
/beta
```

The request authorizes only the pull request head SHA that existed when the comment was added. The workflow revalidates the owner or maintainer permission and head SHA immediately before publishing. Any new commit requires CI to pass again and a fresh owner or maintainer `/beta` comment. Fork pull requests are supported. Pull requests that change a release-gating workflow cannot be beta-published until those workflow changes land on `main`.

Beta versions use `<base>-beta.pr<PR number>.sha<12-character SHA>`. The candidate is built in a read-only workflow without npm publishing credentials. The privileged release workflow publishes only the validated immutable package artifact with lifecycle scripts disabled. A notification failure cannot invalidate a successful npm publish.

When a pull request is merged into `main`, it is automatically published to `beta` only after the required CI and `Package Candidate` succeed for that exact merge commit. Direct pushes to `main` do not publish a candidate.

Only `npm run release` creates a release tag. Stable `vX.Y.Z` tags publish to npm `latest`; revision/prerelease tags publish to `next`. Neither `beta` nor `next` publishing changes the npm `latest` dist-tag.

## Maintainers

- [rdlabo](https://rdlabo.dev/)
<!-- /rdlabo-docs-omit -->

<!-- rdlabo-docs-omit -->
## License

This project is licensed under the [MIT License](./LICENSE).
<!-- /rdlabo-docs-omit -->
