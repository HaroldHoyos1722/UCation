import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { RegisterModalComponent } from '../register-modal/register-modal.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, RegisterModalComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  showPassword = false;
  isLoading = false;
  showRegisterModal = false;

  logoSrc = '/EscudoPoli.png';

  logoError() {
    this.logoSrc = '/default-logo.png';
  }

  onSubmit() {
    if (!this.email || !this.password) return;
    this.isLoading = true;
    setTimeout(() => {
      this.isLoading = false;
      console.log('Login con:', this.email);
    }, 1500);
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  openRegister() {          
    this.showRegisterModal = true;
  }

  closeRegister() {         
    this.showRegisterModal = false;
  }
}