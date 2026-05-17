import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { DashboardService, DashboardData } from '../../services/dashboard.service';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  imports: [
    CommonModule
  ],
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  data: DashboardData | null = null;
  loading = true;
  mensaje = '';

  readonly weekDays = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];

  constructor(
    private dashboardService: DashboardService,
    private api: ApiService
  ) {}

  ngOnInit(): void {

    this.dashboardService.getDashboardData().subscribe({
      next: (d) => {
        this.data = d;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });

    this.api.getHello().subscribe(() => {
      console.log("Front y Backend se comunican correctamente");
    });
  }
}