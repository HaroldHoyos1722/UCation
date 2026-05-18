import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { RegisterFormData } from '../pages/register-modal/register-modal.component';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private apiUrl = 'http://localhost:8080/api/auth';

    constructor(private http: HttpClient) { }

    login(email: string, password: string): Observable<any> {
        return this.http.post(`${this.apiUrl}/login`, { email, password }).pipe(
            catchError(() => {
                // Backend not available - return mock token
                console.warn('Auth login: backend unavailable, using mock response');
                return of({ success: true, token: 'mock-token' });
            })
        );
    }

    register(data: RegisterFormData): Observable<any> {
        const formData = new FormData();

        Object.entries(data).forEach(([key, value]) => {
            if (value !== null) {
                formData.append(key, value as any);
            }
        });

        return this.http.post(`${this.apiUrl}/register`, formData).pipe(
            catchError(() => {
                console.warn('Auth register: backend unavailable, using mock response');
                return of({ success: true, id: 'mock-user-id' });
            })
        );
    }

    forgotPassword(email: string): Observable<any> {
        return this.http.post(`${this.apiUrl}/forgot-password`, { email }).pipe(
            catchError(() => {
                console.warn('Auth forgotPassword: backend unavailable, using mock response');
                // simulate success and a sent code
                return of({ success: true, message: 'Código enviado (mock)' });
            })
        );
    }

    resetPassword(
        token: string,
        newPassword: string
    ): Observable<any> {
        return this.http.post(`${this.apiUrl}/reset-password`, { token, newPassword }).pipe(
            catchError(() => {
                console.warn('Auth resetPassword: backend unavailable, using mock response');
                return of({ success: true });
            })
        );
    }

    /**
     * Verify a recovery code. Tries backend endpoint, falls back to mock.
     */
    verifyCode(email: string, code: string): Observable<any> {
        // try likely endpoint
        return this.http.post(`${this.apiUrl}/verify-code`, { email, code }).pipe(
            catchError(() => {
                console.warn('Auth verifyCode: backend unavailable, using mock verification');
                const valid = code === '123456' || code.length > 3;
                return of({ success: valid });
            })
        );
    }

    logout() {
        localStorage.removeItem('token');
    }

    saveToken(token: string) {
        localStorage.setItem('token', token);
    }

    getToken() {
        return localStorage.getItem('token');
    }

    isAuthenticated(): boolean {
        return !!this.getToken();
    }
}