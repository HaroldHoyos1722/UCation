import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MentoresService, Mentor, MentoresFilter } from '../../services/mentores.service';

@Component({
  selector: 'app-mentores',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mentores.component.html',
  styleUrls: ['./mentores.component.css']
})
export class MentoresComponent implements OnInit {

  mentores: Mentor[] = [];
  mentoresFiltrados: Mentor[] = [];
  loading = true;

  // Modal
  mentorSeleccionado: Mentor | null = null;
  solicitudEnviada: Set<string> = new Set();
  enviandoSolicitud = false;

  // Filtros
  filters: MentoresFilter = {
    busqueda: '',
    disponibilidad: ''
  };

  // Opciones de filtro derivadas del mock
  areasDisponibles: string[] = [];
  programasDisponibles: string[] = [];

  readonly disponibilidades = [
    { value: 'disponible', label: 'Disponible' },
    { value: 'ocupado', label: 'Ocupado' },
    { value: 'sin_cupos', label: 'Sin cupos' }
  ];

  constructor(private mentoresService: MentoresService) { }

  ngOnInit(): void {
    this.mentoresService.getMentores().subscribe({
      next: (data) => {
        this.mentores = data;
        this.mentoresFiltrados = data;
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  aplicarFiltros(): void {
    const { busqueda, disponibilidad } = this.filters;
    this.mentoresFiltrados = this.mentores.filter(m => {
      const matchBusqueda = !busqueda ||
        m.nombreCompleto.toLowerCase().includes(busqueda.toLowerCase()) ||
        m.sede.toLowerCase().includes(busqueda.toLowerCase());
      const matchDisponibilidad = !disponibilidad || m.disponibilidad === disponibilidad;
      return matchBusqueda && matchDisponibilidad;
    });
  }

  limpiarFiltros(): void {
    this.filters = { busqueda: '', disponibilidad: '' };
    this.mentoresFiltrados = this.mentores;
  }

  abrirModal(mentor: Mentor): void {
    this.mentorSeleccionado = mentor;
  }

  cerrarModal(): void {
    this.mentorSeleccionado = null;
  }

  solicitarMentoria(mentor: Mentor, event?: Event): void {
    event?.stopPropagation();
    if (this.solicitudEnviada.has(mentor.id) || mentor.disponibilidad !== 'disponible') return;
    this.enviandoSolicitud = true;

    this.mentoresService.solicitarMentoria(mentor.id).subscribe({
      next: () => {
        this.solicitudEnviada.add(mentor.id);
        this.enviandoSolicitud = false;
      },
      error: () => { this.enviandoSolicitud = false; }
    });
  }

  getDisponibilidadLabel(d: string): string {
    return { disponible: 'Disponible', ocupado: 'Ocupado', sin_cupos: 'Sin cupos' }[d] ?? d;
  }

  yaEnvio(id: string): boolean {
    return this.solicitudEnviada.has(id);
  }

  hayFiltrosActivos(): boolean {
    return !!(this.filters.busqueda || this.filters.disponibilidad);
  }
}