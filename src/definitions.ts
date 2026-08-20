import type { PluginListenerHandle } from '@capacitor/core';

export interface ScreenshotEventPlugin {
  startWatchEvent(): Promise<void>;
  removeWatchEvent(): Promise<void>;

  addListener(eventName: 'userDidTakeScreenshot', listenerFunc: () => void): Promise<PluginListenerHandle>;
  addListener(eventName: 'screenCaptureStarted', listenerFunc: () => void): Promise<PluginListenerHandle>;
  addListener(eventName: 'screenCaptureStopped', listenerFunc: () => void): Promise<PluginListenerHandle>;
}
