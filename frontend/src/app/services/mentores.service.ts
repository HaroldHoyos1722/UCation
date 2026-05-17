import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

// ── Interfaces ──────────────────────────────────────────────

export type DisponibilidadMentor = 'disponible' | 'ocupado' | 'sin_cupos';

export interface Mentor {
    id: string;
    nombreCompleto: string;
    iniciales: string;
    color: string;
    correo: string;
    descripcionPersonal: string;
    formacionAcademica: string;
    disponibilidad: DisponibilidadMentor;
    calificacion: number;
    sede: string;
}

export interface MentoresFilter {
    busqueda: string;
    disponibilidad: string;
}

// ── Servicio ─────────────────────────────────────────────────

@Injectable({ providedIn: 'root' })
export class MentoresService {

    // TODO: reemplazar con HttpClient cuando esté el backend
    // getMentores(filters: MentoresFilter): Observable<Mentor[]>
    // solicitarMentoria(mentorId: string): Observable<void>

    getMentores(): Observable<Mentor[]> {
        return of(MOCK_MENTORES);
    }

    solicitarMentoria(mentorId: string): Observable<{ success: boolean }> {
        // TODO: return this.http.post(`/api/mentorias/solicitar`, { mentorId });
        console.log('Solicitud enviada al mentor:', mentorId);
        return of({ success: true });
    }
}

// ── Mock data ────────────────────────────────────────────────

const MOCK_MENTORES: Mentor[] = [
    {
        id: '1',
        nombreCompleto: 'Carlos Patiño',
        iniciales: 'CP',
        color: '#4ade80',
        correo: 'carlos.patino@policia.edu.co',
        descripcionPersonal: 'Docente con más de 10 años de experiencia en matemáticas y sistemas. Me apasiona guiar a estudiantes en su proceso de aprendizaje.',
        formacionAcademica: 'Magíster en Matemáticas Aplicadas - Universidad Nacional. Ingeniero de Sistemas - Politécnico.',
        disponibilidad: 'disponible',
        calificacion: 4.9,
        sede: 'Medellín'
    },
    {
        id: '2',
        nombreCompleto: 'María López',
        iniciales: 'ML',
        color: '#f59e0b',
        correo: 'maria.lopez@policia.edu.co',
        descripcionPersonal: 'Especialista en estadística y economía. Ayudo a los estudiantes a entender los datos y su aplicación en el mundo real.',
        formacionAcademica: 'PhD en Estadística - Universidad de Antioquia. Economista - Universidad EAFIT.',
        disponibilidad: 'disponible',
        calificacion: 4.7,
        sede: 'Medellín'
    },
    {
        id: '3',
        nombreCompleto: 'Ricardo Arango',
        iniciales: 'RA',
        color: '#60a5fa',
        correo: 'ricardo.arango@policia.edu.co',
        descripcionPersonal: 'Desarrollador senior con experiencia en múltiples lenguajes y frameworks. Enfocado en buenas prácticas y arquitectura de software.',
        formacionAcademica: 'Magíster en Ingeniería de Software - Universidad de los Andes.',
        disponibilidad: 'ocupado',
        calificacion: 4.5,
        sede: 'Rionegro'
    },
    {
        id: '4',
        nombreCompleto: 'Laura Gómez',
        iniciales: 'LG',
        color: '#c084fc',
        correo: 'laura.gomez@policia.edu.co',
        descripcionPersonal: 'Investigadora y docente en ciencias básicas. Me especializo en hacer accesibles los temas más complejos de física y química.',
        formacionAcademica: 'Magíster en Ciencias Físicas - Universidad Nacional.',
        disponibilidad: 'disponible',
        calificacion: 4.8,
        sede: 'Urabá'
    },
    {
        id: '5',
        nombreCompleto: 'Andrés Torres',
        iniciales: 'AT',
        color: '#fb7185',
        correo: 'andres.torres@policia.edu.co',
        descripcionPersonal: 'Contador público con experiencia en finanzas corporativas y auditoría. Apoyo a estudiantes en contabilidad y finanzas.',
        formacionAcademica: 'Especialista en Finanzas - Universidad Pontificia Bolivariana.',
        disponibilidad: 'sin_cupos',
        calificacion: 4.3,
        sede: 'Medellín'
    },
    {
        id: '6',
        nombreCompleto: 'Valentina Ríos',
        iniciales: 'VR',
        color: '#34d399',
        correo: 'valentina.rios@policia.edu.co',
        descripcionPersonal: 'Diseñadora UX/UI y docente de medios digitales. Apasionada por la creatividad y el diseño centrado en el usuario.',
        formacionAcademica: 'Magíster en Diseño Digital - Universidad de Medellín.',
        disponibilidad: 'disponible',
        calificacion: 4.6,
        sede: 'Medellín'
    }
];