# @rdlabo/capacitor-screenshot-event

Notification that user shot screenshot

**Documentation:** [Read the full documentation](https://docs.rdlabo.dev/projects/capacitor-screenshot-event)

## Install

```bash
npm install @rdlabo/capacitor-screenshot-event
npx cap sync
```

## Usage

```ts
(() => {
  ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
    // Notice take screenshot
  });

  // Start watch take event
  ScreenshotEvent.startWatchEvent();
})();
```

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
