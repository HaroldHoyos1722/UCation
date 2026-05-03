import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface RegisterFormData {
  //Paso 1
  role: 'estudiante' | 'mentor' | '';
  // Paso 2
  fullName: string;
  idType: string;
  idNumber: string;
  institutionalEmail: string;
  phone: string;
  birthDate: string;
  sede: string;
  // Paso 3 – Perfil 
  // Campos compartidos
  profilePhoto: File | null;
  address: string;

  // Solo estudiante
  academicProgram: string;
  currentSemester: string;

  // Solo mentor  
  personalDescription: string;
  academicBackground: string;

  // Paso 4 – Seguridad
  password: string;
  confirmPassword: string;
  termsAccepted: boolean;
}

@Component({
  selector: 'app-register-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-modal.component.html',
  styleUrls: ['./register-modal.component.css']
})

export class RegisterModalComponent {

  /** Controla la visibilidad del modal desde el padre */
  @Input() isOpen = false;

  /** Emite cuando el usuario cierra el modal */
  @Output() closed = new EventEmitter<void>();

  /** Emite los datos finales al terminar el registro */
  @Output() submitted = new EventEmitter<RegisterFormData>();

  @ViewChild('photoInput') photoInput!: ElementRef<HTMLInputElement>;

  steps = ['Tu rol', 'Datos', 'Perfil', 'Seguridad'];
  currentStep = 1;

  /** Activa mensajes de error al intentar avanzar sin completar */
  s2Touched = false;
  s3Touched = false;
  s4Touched = false;

  photoPreview: string | null = null;

  semesters = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

  /** Fecha máxima para fecha de nacimiento (mayor de 14 años) */
  maxBirthDate: string = (() => {
    const d = new Date();
    d.setFullYear(d.getFullYear() - 14);
    return d.toISOString().split('T')[0];
  })();


  formData: RegisterFormData = {
    role: '',
    fullName: '', idType: '', idNumber: '',
    institutionalEmail: '', phone: '', birthDate: '', sede: '',
    profilePhoto: null, address: '',
    academicProgram: '', currentSemester: '',
    personalDescription: '', academicBackground: '',
    password: '', confirmPassword: '', termsAccepted: false
  };

  // ─────────────────────────────────────────────
  //  Navegación
  // ─────────────────────────────────────────────

  nextStep() {
    if (this.currentStep === 2 && !this.isStep2Valid()) {
      this.s2Touched = true;
      return;
    }
    if (this.currentStep === 3 && !this.isStep3Valid()) {
      this.s3Touched = true;
      return;
    }
    if (this.currentStep === 4 && !this.isStep4Valid()) {
      this.s4Touched = true;
      return;
    }

    this.s2Touched = false;
    this.s3Touched = false;
    this.s4Touched = false;

    if (this.currentStep < this.steps.length) {
      this.currentStep++;
    } else {
      this.submitted.emit(this.formData);
      this.close();
    }
  }

  prevStep() {
    if (this.currentStep > 1) {
      this.s2Touched = false;
      this.s3Touched = false;
      this.s4Touched = false;
      this.currentStep--;
    }
  }

  // ─────────────────────────────────────────────
  //  Paso 1 – Rol
  // ─────────────────────────────────────────────

  selectRole(role: 'estudiante' | 'mentor') {
    this.formData.role = role;
  }

  // ── Paso 2 ───────────────────────────────────

  isStep2Valid(): boolean {
    const d = this.formData;
    return !!(d.fullName && d.idType && d.idNumber &&
      d.institutionalEmail && d.phone && d.birthDate && d.sede);
  }

  // ── Paso 3 ───────────────────────────────────

  isStep3Valid(): boolean {
    const d = this.formData;
    if (d.role === 'estudiante') {
      return !!(d.academicProgram && d.currentSemester);
    }
    if (d.role === 'mentor') {
      return !!(d.personalDescription && d.academicBackground);
    }
    return false;
  }

    // ── Paso 4 ───────────────────────────────────

  isStep4Valid(): boolean {
    const d = this.formData;
    return !!(
      d.password && d.password.length >= 8 &&
      d.confirmPassword &&
      d.password === d.confirmPassword &&
      d.termsAccepted
    );
  }

  triggerPhotoUpload() {
    this.photoInput.nativeElement.click();
  }

  onPhotoSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;

    // Validar tamaño (2 MB)
    if (file.size > 2 * 1024 * 1024) {
      alert('La imagen no puede superar 2 MB.');
      return;
    }

    this.formData.profilePhoto = file;

    const reader = new FileReader();
    reader.onload = (e) => {
      this.photoPreview = e.target?.result as string;
    };
    reader.readAsDataURL(file);
  }

  // ─────────────────────────────────────────────
  //  Control del modal
  // ─────────────────────────────────────────────

  isNextDisabled(): boolean {
    if (this.currentStep === 1) return !this.formData.role;
    return false;
  }

  close() {
    this.currentStep = 1;
    this.s2Touched = false;
    this.s3Touched = false;
    this.s4Touched = false;
    this.photoPreview = null;
    this.formData = {
      role: '',
      fullName: '', idType: '', idNumber: '',
      institutionalEmail: '', phone: '', birthDate: '', sede: '',
      profilePhoto: null, address: '',
      academicProgram: '', currentSemester: '',
      personalDescription: '', academicBackground: '',
      password: '', confirmPassword: '', termsAccepted: false
    };
    this.closed.emit();
  }

  /** Cierra al hacer clic en el overlay (fuera del contenedor) */
  onOverlayClick(event: MouseEvent) {
    if ((event.target as HTMLElement).classList.contains('modal-overlay')) {
      this.close();
    }
  }

}
