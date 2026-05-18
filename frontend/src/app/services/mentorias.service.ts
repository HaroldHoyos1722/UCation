import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

// ── Interfaces ──────────────────────────────────────────────

export type EstadoMentoria = 'pendiente' | 'aceptada' | 'finalizada' | 'rechazada';
export type EstadoSesion = 'programada' | 'cancelada' | 'realizada';
export type UserRole = 'estudiante' | 'mentor';

export interface Sesion {
    id: string;
    mentoriaId: string;
    fecha: string;        // ISO: '2025-05-20'
    hora: string;         // '10:00'
    duracionMinutos: number; // 30-60
    lugar: string;
    observaciones: string;
    estado: EstadoSesion;
}

export interface Mentoria {
    id: string;
    // Quien solicita
    estudianteId: string;
    estudianteNombre: string;
    estudianteIniciales: string;
    estudianteColor: string;
    // Mentor asignado
    mentorId: string;
    mentorNombre: string;
    mentorIniciales: string;
    mentorColor: string;
    // Info
    estado: EstadoMentoria;
    fechaSolicitud: string;  // ISO
    sesiones: Sesion[];
}

export interface SesionForm {
    fecha: string;
    hora: string;
    duracionMinutos: number;
    lugar: string;
    observaciones: string;
}

export interface CalificacionMentor {
    sesionId: string;
    claridad: number;
    dominio: number;
    aprendizaje: number;
    comunicacion: number;
    satisfaccion: number;
}

// ── Servicio ─────────────────────────────────────────────────

@Injectable({ providedIn: 'root' })
export class MentoriasService {

    // TODO: reemplazar con HttpClient cuando esté el backend
    // getMentorias(role: UserRole): Observable<Mentoria[]>
    // responderSolicitud(id: string, accion: 'aceptar'|'rechazar'): Observable<void>
    // crearSesion(mentoriaId: string, form: SesionForm): Observable<Sesion>
    // editarSesion(sesionId: string, form: SesionForm): Observable<Sesion>
    // cancelarSesion(sesionId: string): Observable<void>

    getMentorias(role: UserRole): Observable<Mentoria[]> {
        return of(MOCK_MENTORIAS);
    }

    responderSolicitud(id: string, accion: 'aceptar' | 'rechazar'): Observable<{ success: boolean }> {
        console.log(`Solicitud ${id} -> ${accion}`);
        return of({ success: true });
    }

    crearSesion(mentoriaId: string, form: SesionForm): Observable<Sesion> {
        const nueva: Sesion = {
            id: Date.now().toString(),
            mentoriaId,
            ...form,
            estado: 'programada'
        };
        return of(nueva);
    }

    editarSesion(sesionId: string, form: SesionForm): Observable<Sesion> {
        return of({ id: sesionId, mentoriaId: '', ...form, estado: 'programada' });
    }

    cancelarSesion(sesionId: string): Observable<{ success: boolean }> {
        console.log('Cancelar sesión:', sesionId);
        return of({ success: true });
    }

    calificarMentor(data: CalificacionMentor): Observable<{ success: boolean }> {
        console.log('Calificación enviada:', data);
        return of({ success: true });
    }
}

// ── Mock data ────────────────────────────────────────────────

const MOCK_MENTORIAS: Mentoria[] = [
    {
        id: '1',
        estudianteId: 'e1',
        estudianteNombre: 'Juan Díaz',
        estudianteIniciales: 'JD',
        estudianteColor: '#4ade80',
        mentorId: 'm1',
        mentorNombre: 'Carlos Patiño',
        mentorIniciales: 'CP',
        mentorColor: '#60a5fa',
        estado: 'aceptada',
        fechaSolicitud: '2025-05-01',
        sesiones: [
            {
                id: 's1', mentoriaId: '1',
                fecha: '2025-05-14', hora: '15:00',
                duracionMinutos: 60,
                lugar: 'Sala B - Bloque 3',
                observaciones: 'Repaso de integrales por sustitución.',
                estado: 'realizada'
            },
            {
                id: 's2', mentoriaId: '1',
                fecha: '2025-05-20', hora: '10:00',
                duracionMinutos: 45,
                lugar: 'Sala A - Bloque 1',
                observaciones: 'Continuar con derivadas parciales.',
                estado: 'programada'
            }
        ]
    },
    {
        id: '2',
        estudianteId: 'e1',
        estudianteNombre: 'Juan Díaz',
        estudianteIniciales: 'JD',
        estudianteColor: '#4ade80',
        mentorId: 'm2',
        mentorNombre: 'María López',
        mentorIniciales: 'ML',
        mentorColor: '#f59e0b',
        estado: 'pendiente',
        fechaSolicitud: '2025-05-10',
        sesiones: []
    },
    {
        id: '3',
        estudianteId: 'e2',
        estudianteNombre: 'Sofía Ruiz',
        estudianteIniciales: 'SR',
        estudianteColor: '#c084fc',
        mentorId: 'm1',
        mentorNombre: 'Carlos Patiño',
        mentorIniciales: 'CP',
        mentorColor: '#60a5fa',
        estado: 'pendiente',
        fechaSolicitud: '2025-05-12',
        sesiones: []
    }
];