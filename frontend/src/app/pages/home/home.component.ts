import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { DashboardService, DashboardData } from '../../services/dashboard.service';
import { NotificacionesPopupComponent } from './notificaciones-popup/notificaciones-popup.component';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  imports: [CommonModule, RouterModule, NotificacionesPopupComponent],
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  data: DashboardData | null = null;
  loading = true;
  popupAbierto = false;
  notificacionesSinLeer = 0;

  readonly weekDays = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];

  constructor(
    private dashboardService: DashboardService,
    private api: ApiService
  ) {}

  ngOnInit(): void {
    this.dashboardService.getDashboardData().subscribe({
      next: (d) => { this.data = d; this.loading = false; },
      error: () => { this.loading = false; }
    });

    this.api.getHello().subscribe(() => {
      console.log('Front y Backend se comunican correctamente');
    });
  }

  togglePopup(): void {
    this.popupAbierto = !this.popupAbierto;
  }
}