import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProfileService, UserProfile, TipoIdentificacion, Sede } from '../../services/perfil.service';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  profile: UserProfile | null = null;
  editForm: UserProfile | null = null;
  loading = true;
  saving = false;
  isEditing = false;

  readonly tiposIdentificacion: TipoIdentificacion[] = [
    'Cédula de Ciudadanía',
    'Tarjeta de Identidad',
    'Cédula de Extranjería'
  ];

  readonly sedes: Sede[] = ['Medellín', 'Rionegro', 'Urabá', 'Otro'];

  constructor(private profileService: ProfileService) { }

  ngOnInit(): void {
    this.profileService.getProfile().subscribe({
      next: (p) => { this.profile = p; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  startEdit(): void {
    // Copia profunda para no mutar el original hasta guardar
    this.editForm = JSON.parse(JSON.stringify(this.profile));
    this.isEditing = true;
  }

  cancelEdit(): void {
    this.editForm = null;
    this.isEditing = false;
  }

  saveChanges(): void {
    if (!this.editForm) return;
    this.saving = true;

    // TODO: reemplazar con llamada al backend
    // this.profileService.updateProfile(this.editForm).subscribe({
    //   next: (updated) => {
    //     this.profile = updated;
    //     this.isEditing = false;
    //     this.saving = false;
    //   },
    //   error: () => { this.saving = false; }
    // });

    // Simulación mientras no hay backend
    setTimeout(() => {
      this.profile = JSON.parse(JSON.stringify(this.editForm));
      this.isEditing = false;
      this.saving = false;
      this.editForm = null;
    }, 800);
  }

  onFotoChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length || !this.editForm) return;
    const file = input.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      this.editForm!.fotoPerfil = reader.result as string;
    };
    reader.readAsDataURL(file);
    // TODO: subir archivo al backend cuando esté disponible
  }

  getInitials(name?: string): string {
    const source = name ?? this.profile?.nombreCompleto ?? '';
    return source.split(' ').slice(0, 2).map(n => n[0]).join('').toUpperCase();
  }

  formatDate(iso: string): string {
    const [y, m, d] = iso.split('-');
    const months = ['enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio',
      'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre'];
    return `${parseInt(d)} de ${months[parseInt(m) - 1]} de ${y}`;
  }
}