import { Component } from '@angular/core';
import { StorageService } from './services/storage.service';
import { AuthService } from './services/auth.service';
import { Router, RoutesRecognized, Event, NavigationStart } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Mini Hacker News';
  isLoggedIn = false;
  username?: string;

  constructor(private storageService: StorageService, private authService: AuthService, private router: Router) {
     router.events.subscribe((event: Event) => {
          if (event instanceof NavigationStart) {
            if (event.url == '/posts') {
               this.isLoggedIn = this.storageService.isLoggedIn();
            }
          }
    });
  }

  ngOnInit(): void {
     this.isLoggedIn = this.storageService.isLoggedIn();
     if (this.isLoggedIn) {
       const user = this.storageService.getUser();
       this.username = user.username;
     }
   }

   logout(): void {
     this.authService.logout().subscribe({
       next: res => {
         console.log(res);
         this.storageService.clean();
       },
       error: err => {
         console.log(err);
       }
     });
   }
}
