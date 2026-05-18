import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {}

  // Mock implementation until backend integration
  forgotPassword(email: string): Observable<any> {
    // TODO: replace with real HTTP call: return this.http.post('/api/auth/forgot', { email });
    return of({ success: true });
  }

  verifyCode(email: string, code: string): Observable<any> {
    // TODO: real verify endpoint
    const valid = code === '123456' || code.length > 3;
    return of({ success: valid });
  }

  resetPassword(email: string, code: string, newPassword: string): Observable<any> {
    // TODO: real reset endpoint
    return of({ success: true });
  }
}

@Component({
  selector: 'app-forgot-password-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './forgot-password-modal.component.html',
  styleUrls: ['./forgot-password-modal.component.css']
})
export class ForgotPasswordModalComponent {

  constructor(private authService: AuthService) {}

  @Input() isOpen = false;
  @Output() closed = new EventEmitter<void>();

  email = '';
  loading = false;
  emailSent = false;
  error = '';
  // flow steps: 'email' -> 'verify' -> 'reset'
  step: 'email' | 'verify' | 'reset' = 'email';
  code = '';
  newPassword = '';
  confirmPassword = '';

  sendRecoveryEmail() {
    if (!this.email) return;

    this.loading = true;
    this.error = '';

    this.authService.forgotPassword(this.email).subscribe({
      next: () => {
        this.loading = false;
        this.emailSent = true;
        this.step = 'verify';
      },
      error: () => {
        this.loading = false;
        this.error = 'No fue posible enviar el correo.';
      }
    });
  }

  verifyRecoveryCode() {
    if (!this.code) return;
    this.loading = true;
    this.error = '';
    this.authService.verifyCode(this.email, this.code).subscribe({
      next: (res) => {
        this.loading = false;
        if (res && res.success) {
          this.step = 'reset';
        } else {
          this.error = 'Código inválido.';
        }
      },
      error: () => {
        this.loading = false;
        this.error = 'Error al verificar el código.';
      }
    });
  }

  submitNewPassword() {
    if (!this.newPassword || this.newPassword.length < 6) {
      this.error = 'La contraseña debe tener al menos 6 caracteres.';
      return;
    }
    if (this.newPassword !== this.confirmPassword) {
      this.error = 'Las contraseñas no coinciden.';
      return;
    }
    this.loading = true;
    this.error = '';
    this.authService.resetPassword(this.email, this.code, this.newPassword).subscribe({
      next: () => {
        this.loading = false;
        this.close();
      },
      error: () => {
        this.loading = false;
        this.error = 'No se pudo restablecer la contraseña.';
      }
    });
  }

  close() {
    this.email = '';
    this.loading = false;
    this.emailSent = false;
    this.error = '';
    this.step = 'email';
    this.code = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.closed.emit();
  }

  onOverlayClick(event: MouseEvent) {
    if ((event.target as HTMLElement).classList.contains('modal-overlay')) {
      this.close();
    }
  }
}