import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  BitacoraService, EntradaBitacora,
  EntradaForm, UserRole, BitacoraFiltros
} from '../../services/bitacora.service';

@Component({
  selector: 'app-bitacora',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bitacora.component.html',
  styleUrls: ['./bitacora.component.css']
})
export class BitacoraComponent implements OnInit {

  // TODO: obtener del AuthService cuando esté el backend
  currentRole: UserRole = 'mentor';
  currentUserId = 'm1';

  entradas: EntradaBitacora[] = [];
  loading = true;

  // Modal
  modalAbierto = false;
  entradaEditando: EntradaBitacora | null = null;
  guardando = false;
  entradaForm: EntradaForm = this.formVacio();

  // Detalle
  entradaDetalle: EntradaBitacora | null = null;

  // Filtros
  filtros: BitacoraFiltros = {
    busqueda: '', mentor: '',
    estado: '', fechaDesde: '', fechaHasta: ''
  };

  mentoresDisponibles: string[] = [];

  readonly MESES = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
    'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];

  constructor(private bitacoraService: BitacoraService) { }

  ngOnInit(): void {
    this.bitacoraService.getEntradas(this.currentRole, this.currentUserId).subscribe({
      next: (data) => {
        this.entradas = data;
        this.mentoresDisponibles = [...new Set(data.map(e => e.mentorNombre))];
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  // ── Filtros ──────────────────────────────────────────────────

  get entradasFiltradas(): EntradaBitacora[] {
    return this.entradas.filter(e => {
      const matchBusqueda = !this.filtros.busqueda ||
        e.mentorNombre.toLowerCase().includes(this.filtros.busqueda.toLowerCase()) ||
        e.estudianteNombre.toLowerCase().includes(this.filtros.busqueda.toLowerCase()) ||
        e.actividadesRealizadas.toLowerCase().includes(this.filtros.busqueda.toLowerCase());
      const matchMentor = !this.filtros.mentor || e.mentorNombre === this.filtros.mentor;
      const matchEstado = !this.filtros.estado || e.estado === this.filtros.estado;
      const matchDesde = !this.filtros.fechaDesde || e.fechaSesion >= this.filtros.fechaDesde;
      const matchHasta = !this.filtros.fechaHasta || e.fechaSesion <= this.filtros.fechaHasta;
      return matchBusqueda && matchMentor && matchEstado && matchDesde && matchHasta;
    }).sort((a, b) => b.fechaSesion.localeCompare(a.fechaSesion));
  }

  limpiarFiltros(): void {
    this.filtros = { busqueda: '', mentor: '', estado: '', fechaDesde: '', fechaHasta: '' };
  }

  hayFiltros(): boolean {
    return !!(this.filtros.busqueda || this.filtros.mentor ||
      this.filtros.estado || this.filtros.fechaDesde || this.filtros.fechaHasta);
  }

  // ── Modal ────────────────────────────────────────────────────

  abrirModal(entrada?: EntradaBitacora): void {
    this.entradaEditando = entrada ?? null;
    this.entradaForm = entrada
      ? {
        actividadesRealizadas: entrada.actividadesRealizadas,
        observaciones: entrada.observaciones,
        recomendaciones: entrada.recomendaciones
      }
      : this.formVacio();
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.entradaEditando = null;
    this.entradaForm = this.formVacio();
  }

  guardar(): void {
    this.guardando = true;
    if (this.entradaEditando) {
      this.bitacoraService.editarEntrada(this.entradaEditando.id, this.entradaForm).subscribe({
        next: (actualizada) => {
          const idx = this.entradas.findIndex(e => e.id === this.entradaEditando!.id);
          if (idx >= 0) this.entradas[idx] = actualizada;
          this.guardando = false;
          this.cerrarModal();
        }
      });
    } else {
      this.bitacoraService.registrarEntrada('', this.entradaForm).subscribe({
        next: (nueva) => {
          this.entradas.unshift(nueva);
          this.guardando = false;
          this.cerrarModal();
        }
      });
    }
  }

  // ── Detalle ──────────────────────────────────────────────────

  abrirDetalle(entrada: EntradaBitacora): void { this.entradaDetalle = entrada; }
  cerrarDetalle(): void { this.entradaDetalle = null; }

  // ── Helpers ─────────────────────────────────────────────────

  private formVacio(): EntradaForm {
    return { actividadesRealizadas: '', observaciones: '', recomendaciones: '' };
  }

  formularioValido(): boolean {
    return !!(this.entradaForm.actividadesRealizadas.trim() &&
      this.entradaForm.observaciones.trim());
  }

  formatFecha(iso: string): string {
    if (!iso) return '';
    const [y, m, d] = iso.split('-');
    return `${parseInt(d)} ${this.MESES[parseInt(m) - 1]} ${y}`;
  }

  formatDateTime(iso: string): string {
    if (!iso) return '';
    const d = new Date(iso);
    return `${this.formatFecha(d.toISOString().split('T')[0])} · ${d.getHours()}:${String(d.getMinutes()).padStart(2, '0')}`;
  }

  contarPorEstado(estado: 'registrada' | 'pendiente'): number {
    return this.entradas.filter(e => e.estado === estado).length;
  }
}