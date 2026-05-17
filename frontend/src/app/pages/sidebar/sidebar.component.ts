import { Component, OnInit } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

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
export class SidebarComponent implements OnInit {
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

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.updateActiveItemFromUrl(this.router.url);
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe(event => this.updateActiveItemFromUrl(event.urlAfterRedirects));
  }

  private updateActiveItemFromUrl(url: string): void {
    const path = url.split('?')[0].split('#')[0];

    if (path.startsWith('/perfil')) {
      this.activeItem = 'perfil';
    } else if (path.startsWith('/home')) {
      this.activeItem = 'inicio';
    } else if (path.startsWith('/mentores')) {
      this.activeItem = 'mentores';
    } else if (path.startsWith('/mentorias')) {
      this.activeItem = 'mentorias';
    } else if (path.startsWith('/agenda')) {
      this.activeItem = 'agenda';
    } else if (path.startsWith('/bitacora')) {
      this.activeItem = 'bitacora';
    } else if (path.startsWith('/historial')) {
      this.activeItem = 'historial';
    } else if (path.startsWith('/config')) {
      this.activeItem = 'config';
    } else {
      this.activeItem = 'inicio';
    }
  }

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