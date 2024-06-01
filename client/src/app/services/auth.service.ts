import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, @Inject('environment') private env: any) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      `${this.env.baseUrl}/api/auth/signin`,{ username, password }, httpOptions);
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(
      `${this.env.baseUrl}/api/auth/signup`,{ username, email, password }, httpOptions );
  }

  logout(): Observable<any> {
    return this.http.post(`${this.env.baseUrl}/api/auth/signout`, {}, httpOptions);
  }
}
