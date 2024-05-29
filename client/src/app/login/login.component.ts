import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    // Replace with your authentication logic (e.g., API call)
    if (this.username === 'admin' && this.password === 'admin') {
      this.router.navigate(['/']); // Redirect to home page on success
    } else {
      this.errorMessage = 'Invalid username or password';
    }
  }
}
