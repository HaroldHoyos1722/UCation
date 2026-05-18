import { Component, OnInit, Output, EventEmitter, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  NotificacionesService,
  Notificacion,
  TipoNotificacion
} from '../../../services/notificaciones.service';

@Component({
  selector: 'app-notificaciones-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notificaciones-popup.component.html',
  styleUrls: ['./notificaciones-popup.component.css']
})
export class NotificacionesPopupComponent implements OnInit {

  @Output() cerrar = new EventEmitter<void>();
  @Output() sinLeer = new EventEmitter<number>();

  notificaciones: Notificacion[] = [];
  loading = true;

  readonly MESES = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
    'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];

  constructor(private notificacionesService: NotificacionesService) { }

  ngOnInit(): void {
    this.notificacionesService.getNotificaciones().subscribe({
      next: (data) => {
        this.notificaciones = data;
        this.loading = false;
        this.emitirSinLeer();
      },
      error: () => { this.loading = false; }
    });
  }

  // Cierra el popup al hacer clic fuera
  @HostListener('document:keydown.escape')
  onEscape(): void { this.cerrar.emit(); }

  marcarLeida(n: Notificacion, event: Event): void {
    event.stopPropagation();
    if (n.leida) return;
    this.notificacionesService.marcarLeida(n.id).subscribe({
      next: () => {
        n.leida = true;
        this.emitirSinLeer();
      }
    });
  }

  marcarTodasLeidas(): void {
    this.notificacionesService.marcarTodasLeidas().subscribe({
      next: () => {
        this.notificaciones.forEach(n => n.leida = true);
        this.emitirSinLeer();
      }
    });
  }

  private emitirSinLeer(): void {
    this.sinLeer.emit(this.notificaciones.filter(n => !n.leida).length);
  }

  get noLeidas(): number {
    return this.notificaciones.filter(n => !n.leida).length;
  }

  getIcono(tipo: TipoNotificacion): string {
    const iconos: Record<TipoNotificacion, string> = {
      solicitud_aceptada: 'ti-circle-check',
      solicitud_rechazada: 'ti-circle-x',
      sesion_programada: 'ti-calendar-plus',
      sesion_cancelada: 'ti-calendar-off',
      sesion_reprogramada: 'ti-calendar-event',
      bitacora_registrada: 'ti-notebook',
      mentoria_finalizada: 'ti-flag'
    };
    return iconos[tipo] ?? 'ti-bell';
  }

  getColor(tipo: TipoNotificacion): string {
    const colores: Record<TipoNotificacion, string> = {
      solicitud_aceptada: '#2e7d32',
      solicitud_rechazada: '#c62828',
      sesion_programada: '#1565c0',
      sesion_cancelada: '#c62828',
      sesion_reprogramada: '#c9a227',
      bitacora_registrada: '#2e7d32',
      mentoria_finalizada: '#888'
    };
    return colores[tipo] ?? '#888';
  }

  formatFecha(iso: string): string {
    const d = new Date(iso);
    const ahora = new Date();
    const diffMs = ahora.getTime() - d.getTime();
    const diffMin = Math.floor(diffMs / 60000);
    const diffHrs = Math.floor(diffMin / 60);
    const diffDias = Math.floor(diffHrs / 24);

    if (diffMin < 1) return 'Ahora';
    if (diffMin < 60) return `Hace ${diffMin} min`;
    if (diffHrs < 24) return `Hace ${diffHrs}h`;
    if (diffDias === 1) return 'Ayer';
    if (diffDias < 7) return `Hace ${diffDias} días`;
    return `${d.getDate()} ${this.MESES[d.getMonth()]}`;
  }
}