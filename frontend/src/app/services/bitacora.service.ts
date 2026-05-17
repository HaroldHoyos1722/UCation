import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

// ── Interfaces ──────────────────────────────────────────────

export type UserRole = 'estudiante' | 'mentor';
export type EstadoEntrada = 'registrada' | 'pendiente';

export interface EntradaBitacora {
    id: string;
    mentoriaId: string;
    sesionId: string;
    // Participantes
    mentorId: string;
    mentorNombre: string;
    mentorIniciales: string;
    mentorColor: string;
    estudianteId: string;
    estudianteNombre: string;
    // Contenido
    fechaSesion: string;       // ISO
    actividadesRealizadas: string;
    observaciones: string;
    recomendaciones: string;
    estado: EstadoEntrada;
    // Metadata
    creadoEn: string;          // ISO
    actualizadoEn: string;     // ISO
}

export interface EntradaForm {
    actividadesRealizadas: string;
    observaciones: string;
    recomendaciones: string;
}

export interface BitacoraFiltros {
    busqueda: string;
    mentor: string;
    estado: EstadoEntrada | '';
    fechaDesde: string;
    fechaHasta: string;
}

// ── Servicio ─────────────────────────────────────────────────

@Injectable({ providedIn: 'root' })
export class BitacoraService {

    // TODO: reemplazar con HttpClient cuando esté el backend
    // getEntradas(role: UserRole, userId: string): Observable<EntradaBitacora[]>
    // registrarEntrada(sesionId: string, form: EntradaForm): Observable<EntradaBitacora>
    // editarEntrada(entradaId: string, form: EntradaForm): Observable<EntradaBitacora>

    getEntradas(role: UserRole, userId: string): Observable<EntradaBitacora[]> {
        return of(MOCK_ENTRADAS);
    }

    registrarEntrada(sesionId: string, form: EntradaForm): Observable<EntradaBitacora> {
        const nueva: EntradaBitacora = {
            id: Date.now().toString(),
            mentoriaId: '1',
            sesionId,
            mentorId: 'm1',
            mentorNombre: 'Carlos Patiño',
            mentorIniciales: 'CP',
            mentorColor: '#60a5fa',
            estudianteId: 'e1',
            estudianteNombre: 'Juan Díaz',
            fechaSesion: new Date().toISOString().split('T')[0],
            ...form,
            estado: 'registrada',
            creadoEn: new Date().toISOString(),
            actualizadoEn: new Date().toISOString()
        };
        return of(nueva);
    }

    editarEntrada(entradaId: string, form: EntradaForm): Observable<EntradaBitacora> {
        const entrada = MOCK_ENTRADAS.find(e => e.id === entradaId)!;
        return of({ ...entrada, ...form, actualizadoEn: new Date().toISOString() });
    }
}

// ── Mock data ────────────────────────────────────────────────

const MOCK_ENTRADAS: EntradaBitacora[] = [
    {
        id: '1',
        mentoriaId: '1',
        sesionId: 's1',
        mentorId: 'm1',
        mentorNombre: 'Carlos Patiño',
        mentorIniciales: 'CP',
        mentorColor: '#60a5fa',
        estudianteId: 'e1',
        estudianteNombre: 'Juan Díaz',
        fechaSesion: '2025-05-14',
        actividadesRealizadas: 'Revisión de integrales por sustitución trigonométrica. Ejercicios prácticos del capítulo 7.',
        observaciones: 'El estudiante muestra buena comprensión de los conceptos base. Necesita reforzar la técnica de sustitución.',
        recomendaciones: 'Practicar los ejercicios 7.3 al 7.8 del libro guía antes de la próxima sesión.',
        estado: 'registrada',
        creadoEn: '2025-05-14T16:00:00',
        actualizadoEn: '2025-05-14T16:00:00'
    },
    {
        id: '2',
        mentoriaId: '1',
        sesionId: 's2',
        mentorId: 'm1',
        mentorNombre: 'Carlos Patiño',
        mentorIniciales: 'CP',
        mentorColor: '#60a5fa',
        estudianteId: 'e1',
        estudianteNombre: 'Juan Díaz',
        fechaSesion: '2025-05-20',
        actividadesRealizadas: '',
        observaciones: '',
        recomendaciones: '',
        estado: 'pendiente',
        creadoEn: '2025-05-20T10:00:00',
        actualizadoEn: '2025-05-20T10:00:00'
    },
    {
        id: '3',
        mentoriaId: '2',
        sesionId: 's3',
        mentorId: 'm2',
        mentorNombre: 'María López',
        mentorIniciales: 'ML',
        mentorColor: '#f59e0b',
        estudianteId: 'e1',
        estudianteNombre: 'Juan Díaz',
        fechaSesion: '2025-04-28',
        actividadesRealizadas: 'Introducción a distribuciones de probabilidad. Distribución normal y binomial.',
        observaciones: 'Avance satisfactorio. El estudiante participó activamente en los ejercicios.',
        recomendaciones: 'Leer el capítulo 4 de estadística inferencial antes de la próxima sesión.',
        estado: 'registrada',
        creadoEn: '2025-04-28T11:30:00',
        actualizadoEn: '2025-04-28T11:30:00'
    },
    {
        id: '4',
        mentoriaId: '2',
        sesionId: 's4',
        mentorId: 'm2',
        mentorNombre: 'María López',
        mentorIniciales: 'ML',
        mentorColor: '#f59e0b',
        estudianteId: 'e1',
        estudianteNombre: 'Juan Díaz',
        fechaSesion: '2025-04-10',
        actividadesRealizadas: 'Ejercicios de probabilidad condicional y teorema de Bayes.',
        observaciones: 'Dificultad inicial con el teorema de Bayes, superada con ejemplos prácticos.',
        recomendaciones: 'Revisar los apuntes y resolver el taller de repaso.',
        estado: 'registrada',
        creadoEn: '2025-04-10T09:00:00',
        actualizadoEn: '2025-04-10T09:00:00'
    }
];