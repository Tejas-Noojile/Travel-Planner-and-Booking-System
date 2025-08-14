import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export type ToastType = 'success' | 'error';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  public isVisible$ = new BehaviorSubject<boolean>(false);
  public isHiding$ = new BehaviorSubject<boolean>(false); // To track hiding state
  public message$ = new BehaviorSubject<string>('');
  public type$ = new BehaviorSubject<ToastType>('success');

  private timer: any;

  constructor() {}

  show(message: string, type: ToastType = 'success', duration: number = 4000) {
    this.message$.next(message);
    this.type$.next(type);
    this.isHiding$.next(false);
    this.isVisible$.next(true);

    if (this.timer) {
      clearTimeout(this.timer);
    }

    this.timer = setTimeout(() => {
      this.hide();
    }, duration);
  }

  hide() {
    this.isHiding$.next(true);
    setTimeout(() => {
      this.isVisible$.next(false);
      this.isHiding$.next(false);
    }, 300);
  }
}
