import { Injectable, Inject } from '@angular/core';
import { isPlatformBrowser} from '@angular/common';
import { PLATFORM_ID } from '@angular/core';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  clean(): void {
    if (this.isBrowser) {
      window.sessionStorage.clear();
    }
  }

  public saveUser(user: any): void {
    if (this.isBrowser) {
      window.sessionStorage.removeItem(USER_KEY);
      window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    }
  }

  public getUser(): any {
    if (this.isBrowser) {
      const user = window.sessionStorage.getItem(USER_KEY);
      if (user) {
        return JSON.parse(user);
      }
    }
    return {};
  }

  public isLoggedIn(): boolean {
    if (this.isBrowser) {
      const user = window.sessionStorage.getItem(USER_KEY);
      if (user) {
        return true;
      }
    }

    return false;
  }
}
