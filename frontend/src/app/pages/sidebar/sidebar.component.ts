import { Component } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { RouterModule } from '@angular/router';

interface SidebarUser {
  fullName: string;
  role: string;
  initials: string;
}

interface NavBadges {
  mentorias: number | null;
  // Agrega aquí otros items que necesiten badge en el futuro
  // agenda: number | null;
  // historial: number | null;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, NgClass, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  collapsed = false;
  activeItem = 'inicio';
  logoSrc = '/EscudoPoli.png';

  // TODO: reemplazar con llamada al AuthService cuando esté el backend
  currentUser: SidebarUser = {
    fullName: 'Juan Díaz',
    role: 'Estudiante',
    initials: 'JD'
  };

  // TODO: reemplazar con llamada al NotificationService / MentoringService cuando esté el backend
  badges: NavBadges = {
    mentorias: 4
  };

  toggleSidebar(): void {
    this.collapsed = !this.collapsed;
  }

  setActive(item: string): void {
    this.activeItem = item;
  }

  logout(): void {
    // TODO: llamar AuthService.logout()
    console.log('Cerrando sesión...');
  }
}