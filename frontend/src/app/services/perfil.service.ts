import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

// ── Interfaces ──────────────────────────────────────────────

export type UserRole = 'estudiante' | 'mentor';
export type TipoIdentificacion = 'Cédula de Ciudadanía' | 'Tarjeta de Identidad' | 'Cédula de Extranjería';
export type Sede = 'Medellín' | 'Rionegro' | 'Urabá' | 'Otro';

export interface EstudianteInfo {
    programaAcademico: string;
    semestre: number;
}

export interface MentorInfo {
    descripcionPersonal: string;
    formacionAcademica: string;
}

export interface UserProfile {
    // Datos básicos
    id: string;
    nombreCompleto: string;
    tipoIdentificacion: TipoIdentificacion;
    numeroIdentificacion: string;
    correoInstitucional: string;
    numeroTelefonico: string;
    fechaNacimiento: string;       // ISO: '1999-04-15'
    sede: Sede;
    role: UserRole;

    // Opcionales
    fotoPerfil: string | null;     // URL o null
    direccion: string | null;

    // Según rol
    estudianteInfo?: EstudianteInfo;
    mentorInfo?: MentorInfo;
}

// ── Servicio ─────────────────────────────────────────────────

@Injectable({ providedIn: 'root' })
export class ProfileService {

    // TODO: reemplazar con HttpClient cuando esté el backend
    // private apiUrl = '/api/profile';

    getProfile(): Observable<UserProfile> {
        // TODO: return this.http.get<UserProfile>(this.apiUrl);
        return of(MOCK_PROFILE);
    }
}

// ── Mock data ────────────────────────────────────────────────

const MOCK_PROFILE: UserProfile = {
    id: '1',
    nombreCompleto: 'Juan Díaz',
    tipoIdentificacion: 'Cédula de Ciudadanía',
    numeroIdentificacion: '1234567890',
    correoInstitucional: 'juan.diaz@policia.edu.co',
    numeroTelefonico: '+57 300 123 4567',
    fechaNacimiento: '1999-04-15',
    sede: 'Medellín',
    role: 'estudiante',
    fotoPerfil: null,
    direccion: 'Calle 123 #45-67',
    estudianteInfo: {
        programaAcademico: 'Ingeniería de Sistemas',
        semestre: 5
    }
};