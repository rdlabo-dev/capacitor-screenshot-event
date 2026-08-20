import { ScreenshotEvent } from '@rdlabo/capacitor-screenshot-event';
import type { PluginListenerHandle } from '@capacitor/core';
import { Component, inject, NgZone, OnDestroy } from '@angular/core';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonList,
  IonItem,
  IonLabel,
  IonToggle,
  IonIcon,
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { checkmarkCircle } from 'ionicons/icons';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  imports: [IonHeader, IonToolbar, IonTitle, IonContent, IonList, IonItem, IonLabel, IonToggle, FormsModule, IonIcon],
})
export class HomePage implements OnDestroy {
  isEnabled = false;
  didNoticed = false;
  screenCaptureState = 'Not detected';
  private readonly listenerHandles: Promise<PluginListenerHandle[]>;
  private readonly zone = inject(NgZone);

  constructor() {
    this.listenerHandles = Promise.all([
      ScreenshotEvent.addListener('userDidTakeScreenshot', () => {
        this.zone.run(() => (this.didNoticed = true));
      }),
      ScreenshotEvent.addListener('screenCaptureStarted', () => {
        this.zone.run(() => (this.screenCaptureState = 'Started'));
      }),
      ScreenshotEvent.addListener('screenCaptureStopped', () => {
        this.zone.run(() => (this.screenCaptureState = 'Stopped'));
      }),
    ]);
    addIcons({ checkmarkCircle });
  }

  ngOnDestroy(): void {
    void this.listenerHandles.then((handles) => Promise.all(handles.map((handle) => handle.remove())));
  }

  initialize(event: CustomEvent) {
    this.didNoticed = false;
    this.screenCaptureState = 'Not detected';
    if (event.detail.checked) {
      ScreenshotEvent.startWatchEvent().then(() => console.log('startWatchEvent'));
    } else {
      ScreenshotEvent.removeWatchEvent();
    }
  }
}
