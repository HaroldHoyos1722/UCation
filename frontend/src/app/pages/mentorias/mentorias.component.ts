import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  MentoriasService, Mentoria, Sesion,
  SesionForm, UserRole, EstadoMentoria, CalificacionMentor
} from '../../services/mentorias.service';
import { EstadoFilterPipe } from './estado-filter.pipe';

type TabType = 'lista' | 'calendario';

interface CalendarDay {
  date: number | null;
  isToday: boolean;
  sesiones: Sesion[];
}

@Component({
  selector: 'app-mentorias',
  standalone: true,
  imports: [CommonModule, FormsModule, EstadoFilterPipe],
  templateUrl: './mentorias.component.html',
  styleUrls: ['./mentorias.component.css']
})
export class MentoriasComponent implements OnInit {

  // TODO: obtener del AuthService cuando esté el backend
  //currentRole: UserRole = 'mentor';
  //currentUserId = 'm1';

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

  modalCalificacionAbierto = false;
  sesionCalificando: Sesion | null = null;

  calificacionForm: CalificacionMentor = this.calificacionVacia();

  // Calendario
  calendarYear = new Date().getFullYear();
  calendarMonth = new Date().getMonth();
  calendarWeeks: CalendarDay[][] = [];
  sesionesTooltip: Sesion[] | null = null;

  readonly DURACIONES = [30, 45, 60];
  readonly today = new Date();
  readonly ESTADOS_LABEL: Record<string, string> = {
    pendiente: 'Pendiente', aceptada: 'Aceptada',
    finalizada: 'Finalizada', rechazada: 'Rechazada'
  };
  readonly MESES = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
    'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
  readonly DIAS = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];
  readonly CAMPOS_CALIFICACION: {
    key: keyof Omit<CalificacionMentor, 'sesionId' | 'comentario'>;
    label: string;
  }[] = [
      { key: 'claridad', label: 'Claridad de la explicación' },
      { key: 'dominio', label: 'Dominio del tema' },
      { key: 'aprendizaje', label: 'Ayuda al aprendizaje' },
      { key: 'comunicacion', label: 'Comunicación' },
      { key: 'satisfaccion', label: 'Satisfacción general' }
    ];

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

  estudianteSeleccionadoId: string = '';

  onEstudianteChange(mentoriaId: string): void {
    const mentoria = this.mentorias.find(m => m.id === mentoriaId);
    if (mentoria) this.mentoriaActivaSesion = mentoria;
  }

  abrirModalSesion(mentoria: Mentoria | null, sesion?: Sesion): void {
    this.mentoriaActivaSesion = mentoria;
    this.estudianteSeleccionadoId = mentoria?.id ?? '';
    this.sesionEditando = sesion ?? null;
    this.sesionForm = sesion
      ? {
        fecha: sesion.fecha, hora: sesion.hora,
        duracionMinutos: sesion.duracionMinutos,
        lugar: sesion.lugar, observaciones: sesion.observaciones
      }
      : this.formVacio();
    if (this.fechaSeleccionada && !sesion) {
      this.sesionForm.fecha = this.fechaSeleccionada;
    }
    this.modalSesionAbierto = true;
  }


  cerrarModalSesion(): void {
    this.modalSesionAbierto = false;
    this.mentoriaActivaSesion = null;
    this.sesionEditando = null;
    this.estudianteSeleccionadoId = '';
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
  diaSeleccionado: CalendarDay | null = null;
  fechaSeleccionada: string = '';

  buildCalendar(): void {
    const todasSesiones = this.mentoriasFiltradas
      .flatMap(m => m.sesiones.map(s => ({ ...s, mentoriaRef: m })))
      .filter(s => s.estado === 'programada');

    const firstDay = new Date(this.calendarYear, this.calendarMonth, 1);
    const lastDay = new Date(this.calendarYear, this.calendarMonth + 1, 0);
    const today = new Date();

    let dow = (firstDay.getDay() + 6) % 7;
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


  seleccionarDia(day: CalendarDay): void {
    if (!day.date) return;
    if (this.diaSeleccionado?.date === day.date) {
      // Toggle: si haces clic en el mismo día, deselecciona
      this.diaSeleccionado = null;
      this.fechaSeleccionada = '';
      return;
    }
    this.diaSeleccionado = day;
    this.fechaSeleccionada = `${this.calendarYear}-${String(this.calendarMonth + 1).padStart(2, '0')}-${String(day.date).padStart(2, '0')}`;
  }

  tieneSesion(mentoria: Mentoria, sesionId: string): boolean {
    return mentoria.sesiones.some(s => s.id === sesionId);
  }

  programarDesdeDia(mentoria: Mentoria): void {
    this.abrirModalSesion(mentoria);
    // Precargar la fecha seleccionada en el formulario
    if (this.fechaSeleccionada) {
      this.sesionForm.fecha = this.fechaSeleccionada;
    }
  }

  getMentoriasConSesionEnDia(day: CalendarDay): Mentoria[] {
    if (!day.sesiones.length) return [];
    const ids = new Set(day.sesiones.map(s => s.mentoriaId));
    return this.mentoriasFiltradas.filter(m => ids.has(m.id));
  }

  isPasado(day: CalendarDay): boolean {
    if (!day.date) return false;
    const hoy = new Date();
    const fecha = new Date(this.calendarYear, this.calendarMonth, day.date);
    fecha.setHours(23, 59, 59);
    return fecha < hoy && !day.isToday;
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

  abrirModalCalificacion(sesion: Sesion): void {
    this.sesionCalificando = sesion;
    this.calificacionForm = {
      ...this.calificacionVacia(),
      sesionId: sesion.id
    };
    this.modalCalificacionAbierto = true;
  }

  cerrarModalCalificacion(): void {
    this.modalCalificacionAbierto = false;
    this.sesionCalificando = null;
    this.calificacionForm = this.calificacionVacia();
  }

  setRating(field: keyof Omit<CalificacionMentor, 'sesionId' | 'comentario'>, value: number): void {
    this.calificacionForm[field] = value;
  }

  guardarCalificacion(): void {
    this.mentoriasService.calificarMentor(this.calificacionForm).subscribe({
      next: () => {
        this.cerrarModalCalificacion();
      }
    });
  }

  // ── Helpers ─────────────────────────────────────────────────

  private formVacio(): SesionForm {
    return { fecha: '', hora: '', duracionMinutos: 45, lugar: '', observaciones: '' };
  }

  private calificacionVacia(): CalificacionMentor {
    return {
      sesionId: '',
      claridad: 0,
      dominio: 0,
      aprendizaje: 0,
      comunicacion: 0,
      satisfaccion: 0
    };
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
    const tieneEstudiante = !!this.mentoriaActivaSesion;
    return !!(tieneEstudiante && f.fecha && f.hora && f.lugar &&
      f.duracionMinutos >= 30 && f.duracionMinutos <= 60);
  }

  get calificacionPromedio(): number {
    const valores = [
      this.calificacionForm.claridad,
      this.calificacionForm.dominio,
      this.calificacionForm.aprendizaje,
      this.calificacionForm.comunicacion,
      this.calificacionForm.satisfaccion
    ];

    const suma = valores.reduce((a, b) => a + b, 0);
    return +(suma / 5).toFixed(1);
  }

  get calificacionCompleta(): boolean {
    return (
      this.calificacionForm.claridad > 0 &&
      this.calificacionForm.dominio > 0 &&
      this.calificacionForm.aprendizaje > 0 &&
      this.calificacionForm.comunicacion > 0 &&
      this.calificacionForm.satisfaccion > 0
    );
  }
}