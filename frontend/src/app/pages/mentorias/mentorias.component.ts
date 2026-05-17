import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  MentoriasService, Mentoria, Sesion,
  SesionForm, UserRole, EstadoMentoria
} from '../../services/mentorias.service';

type TabType = 'lista' | 'calendario';

interface CalendarDay {
  date: number | null;
  isToday: boolean;
  sesiones: Sesion[];
}

@Component({
  selector: 'app-mentorias',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mentorias.component.html',
  styleUrls: ['./mentorias.component.css']
})
export class MentoriasComponent implements OnInit {

  // TODO: obtener del AuthService cuando esté el backend
  currentRole: UserRole = 'estudiante';
  currentUserId = 'e1';

  mentorias: Mentoria[] = [];
  loading = true;
  activeTab: TabType = 'lista';

  // Filtro de estado
  filtroEstado: EstadoMentoria | '' = '';

  // Modal sesión
  modalSesionAbierto = false;
  mentoriaActivaSesion: Mentoria | null = null;
  sesionEditando: Sesion | null = null;
  guardandoSesion = false;

  sesionForm: SesionForm = this.formVacio();

  // Modal detalle mentoría
  mentoriaDetalle: Mentoria | null = null;

  // Calendario
  calendarYear = new Date().getFullYear();
  calendarMonth = new Date().getMonth();
  calendarWeeks: CalendarDay[][] = [];
  sesionesTooltip: Sesion[] | null = null;

  readonly DURACIONES = [30, 45, 60];
  readonly ESTADOS_LABEL: Record<string, string> = {
    pendiente: 'Pendiente', aceptada: 'Aceptada',
    finalizada: 'Finalizada', rechazada: 'Rechazada'
  };
  readonly MESES = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
    'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
  readonly DIAS = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];

  constructor(private mentoriasService: MentoriasService) { }

  ngOnInit(): void {
    this.cargarMentorias();
  }

  cargarMentorias(): void {
    this.mentoriasService.getMentorias(this.currentRole).subscribe({
      next: (data) => {
        this.mentorias = data;
        this.loading = false;
        this.buildCalendar();
      },
      error: () => { this.loading = false; }
    });
  }

  // ── Filtro ──────────────────────────────────────────────────

  get mentoriasFiltradas(): Mentoria[] {
    return this.mentorias.filter(m => {
      const matchRol = this.currentRole === 'estudiante'
        ? m.estudianteId === this.currentUserId
        : m.mentorId === this.currentUserId;
      const matchEstado = !this.filtroEstado || m.estado === this.filtroEstado;
      return matchRol && matchEstado;
    });
  }

  // ── Responder solicitud (mentor) ────────────────────────────

  responder(mentoria: Mentoria, accion: 'aceptar' | 'rechazar'): void {
    this.mentoriasService.responderSolicitud(mentoria.id, accion).subscribe({
      next: () => {
        mentoria.estado = accion === 'aceptar' ? 'aceptada' : 'rechazada';
      }
    });
  }

  finalizarMentoria(mentoria: Mentoria): void {
    // TODO: llamar endpoint cuando esté el backend
    mentoria.estado = 'finalizada';
  }

  // ── Modal sesión ────────────────────────────────────────────

  abrirModalSesion(mentoria: Mentoria, sesion?: Sesion): void {
    this.mentoriaActivaSesion = mentoria;
    this.sesionEditando = sesion ?? null;
    this.sesionForm = sesion
      ? {
        fecha: sesion.fecha, hora: sesion.hora,
        duracionMinutos: sesion.duracionMinutos,
        lugar: sesion.lugar, observaciones: sesion.observaciones
      }
      : this.formVacio();
    this.modalSesionAbierto = true;
  }

  cerrarModalSesion(): void {
    this.modalSesionAbierto = false;
    this.mentoriaActivaSesion = null;
    this.sesionEditando = null;
    this.sesionForm = this.formVacio();
  }

  guardarSesion(): void {
    if (!this.mentoriaActivaSesion) return;
    this.guardandoSesion = true;

    if (this.sesionEditando) {
      this.mentoriasService.editarSesion(this.sesionEditando.id, this.sesionForm).subscribe({
        next: (actualizada) => {
          const mentoria = this.mentorias.find(m => m.id === this.mentoriaActivaSesion!.id);
          if (mentoria) {
            const idx = mentoria.sesiones.findIndex(s => s.id === this.sesionEditando!.id);
            if (idx >= 0) mentoria.sesiones[idx] = actualizada;
          }
          this.guardandoSesion = false;
          this.cerrarModalSesion();
          this.buildCalendar();
        }
      });
    } else {
      this.mentoriasService.crearSesion(this.mentoriaActivaSesion.id, this.sesionForm).subscribe({
        next: (nueva) => {
          const mentoria = this.mentorias.find(m => m.id === this.mentoriaActivaSesion!.id);
          if (mentoria) mentoria.sesiones.push(nueva);
          this.guardandoSesion = false;
          this.cerrarModalSesion();
          this.buildCalendar();
        }
      });
    }
  }

  cancelarSesion(mentoria: Mentoria, sesion: Sesion): void {
    this.mentoriasService.cancelarSesion(sesion.id).subscribe({
      next: () => { sesion.estado = 'cancelada'; this.buildCalendar(); }
    });
  }

  // ── Detalle mentoría ────────────────────────────────────────

  abrirDetalle(mentoria: Mentoria): void { this.mentoriaDetalle = mentoria; }
  cerrarDetalle(): void { this.mentoriaDetalle = null; }

  // ── Calendario ──────────────────────────────────────────────

  buildCalendar(): void {
    const todasSesiones = this.mentoriasFiltradas
      .flatMap(m => m.sesiones)
      .filter(s => s.estado === 'programada');

    const firstDay = new Date(this.calendarYear, this.calendarMonth, 1);
    const lastDay = new Date(this.calendarYear, this.calendarMonth + 1, 0);
    const today = new Date();

    let dow = (firstDay.getDay() + 6) % 7; // Lunes = 0
    const weeks: CalendarDay[][] = [];
    let week: CalendarDay[] = [];

    for (let i = 0; i < dow; i++) week.push({ date: null, isToday: false, sesiones: [] });

    for (let d = 1; d <= lastDay.getDate(); d++) {
      const dateStr = `${this.calendarYear}-${String(this.calendarMonth + 1).padStart(2, '0')}-${String(d).padStart(2, '0')}`;
      const sesionesDelDia = todasSesiones.filter(s => s.fecha === dateStr);
      const isToday = today.getFullYear() === this.calendarYear &&
        today.getMonth() === this.calendarMonth &&
        today.getDate() === d;
      week.push({ date: d, isToday, sesiones: sesionesDelDia });
      if (week.length === 7) { weeks.push(week); week = []; }
    }

    while (week.length > 0 && week.length < 7) week.push({ date: null, isToday: false, sesiones: [] });
    if (week.length) weeks.push(week);

    this.calendarWeeks = weeks;
  }

  prevMonth(): void {
    if (this.calendarMonth === 0) { this.calendarMonth = 11; this.calendarYear--; }
    else this.calendarMonth--;
    this.buildCalendar();
  }

  nextMonth(): void {
    if (this.calendarMonth === 11) { this.calendarMonth = 0; this.calendarYear++; }
    else this.calendarMonth++;
    this.buildCalendar();
  }

  // ── Helpers ─────────────────────────────────────────────────

  private formVacio(): SesionForm {
    return { fecha: '', hora: '', duracionMinutos: 45, lugar: '', observaciones: '' };
  }

  formatFecha(iso: string): string {
    if (!iso) return '';
    const [y, m, d] = iso.split('-');
    return `${parseInt(d)} ${this.MESES[parseInt(m) - 1].slice(0, 3)} ${y}`;
  }

  getSesionesProgramadas(m: Mentoria): Sesion[] {
    return m.sesiones.filter(s => s.estado === 'programada');
  }

  contarPorEstado(estado: EstadoMentoria | ''): number {
    if (!estado) return this.mentoriasFiltradas.length;
    return this.mentoriasFiltradas.filter(m => m.estado === estado).length;
  }

  formularioValido(): boolean {
    const f = this.sesionForm;
    return !!(f.fecha && f.hora && f.lugar &&
      f.duracionMinutos >= 30 && f.duracionMinutos <= 60);
  }
}