import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

// ── Interfaces ──────────────────────────────────────────────

export interface DashboardStats {
    mentoriasActivas: number;
    sesionesEstaSemana: number;
    entradasBitacora: number;
    calificacionPromedio: number;
}

export interface MentoriaItem {
    id: string;
    mentorName: string;
    mentorInitials: string;
    mentorColor: string;       // color del avatar
    subject: string;
    dateLabel: string;         // ej: "Hoy 3:00 pm"
    status: 'aceptada' | 'pendiente' | 'finalizada';
}

export interface CalendarDay {
    date: number | null;       // null = celda vacía
    isToday: boolean;
    hasSession: boolean;
}

export interface AgendaMonth {
    label: string;             // "Mayo 2025"
    weeks: CalendarDay[][];
}

export interface BitacoraEntry {
    id: string;
    title: string;
    description: string;
    dateLabel: string;
}

export interface MentorDestacado {
    id: string;
    name: string;
    initials: string;
    color: string;
    subjects: string;
    rating: number;
}

export interface DashboardData {
    greeting: string;          // "¡Buenas tardes, Juan! 👋"
    greetingSubtitle: string;  // "Tienes 2 mentorías pendientes esta semana."
    currentDate: string;       // "Miércoles, 14 de mayo de 2025"
    stats: DashboardStats;
    mentorias: MentoriaItem[];
    agenda: AgendaMonth;
    bitacora: BitacoraEntry[];
    mentoresDestacados: MentorDestacado[];
}

// ── Servicio ─────────────────────────────────────────────────

@Injectable({ providedIn: 'root' })
export class DashboardService {

    // TODO: reemplazar con HttpClient cuando esté el backend
    // private apiUrl = '/api/dashboard';

    getDashboardData(): Observable<DashboardData> {
        // TODO: return this.http.get<DashboardData>(this.apiUrl);
        return of(MOCK_DASHBOARD);
    }
}

// ── Mock data ────────────────────────────────────────────────

const MOCK_DASHBOARD: DashboardData = {
    greeting: '¡Buenas tardes, Juan! 👋',
    greetingSubtitle: 'Tienes 2 mentorías pendientes esta semana.',
    currentDate: 'Miércoles, 14 de mayo de 2025',
    stats: {
        mentoriasActivas: 4,
        sesionesEstaSemana: 2,
        entradasBitacora: 11,
        calificacionPromedio: 4.7
    },
    mentorias: [
        { id: '1', mentorName: 'Carlos Patiño', mentorInitials: 'CP', mentorColor: '#4ade80', subject: 'Cálculo II', dateLabel: 'Hoy · 3:00 pm', status: 'aceptada' },
        { id: '2', mentorName: 'María López', mentorInitials: 'ML', mentorColor: '#f59e0b', subject: 'Estadística', dateLabel: 'Mañana · 10:00 am', status: 'pendiente' },
        { id: '3', mentorName: 'Ricardo Arango', mentorInitials: 'RA', mentorColor: '#60a5fa', subject: 'Programación', dateLabel: '19 may', status: 'finalizada' }
    ],
    agenda: {
        label: 'Mayo 2025',
        weeks: [
            [
                { date: null, isToday: false, hasSession: false },
                { date: null, isToday: false, hasSession: false },
                { date: null, isToday: false, hasSession: false },
                { date: 1, isToday: false, hasSession: false },
                { date: 2, isToday: false, hasSession: false },
                { date: 3, isToday: false, hasSession: false },
                { date: 4, isToday: false, hasSession: false }
            ],
            [
                { date: 5, isToday: false, hasSession: false },
                { date: 6, isToday: false, hasSession: false },
                { date: 7, isToday: false, hasSession: false },
                { date: 8, isToday: false, hasSession: true },
                { date: 9, isToday: false, hasSession: false },
                { date: 10, isToday: false, hasSession: false },
                { date: 11, isToday: false, hasSession: false }
            ],
            [
                { date: 12, isToday: false, hasSession: false },
                { date: 13, isToday: false, hasSession: false },
                { date: 14, isToday: true, hasSession: false },
                { date: 15, isToday: false, hasSession: true },
                { date: 16, isToday: false, hasSession: false },
                { date: 17, isToday: false, hasSession: false },
                { date: 18, isToday: false, hasSession: false }
            ],
            [
                { date: 19, isToday: false, hasSession: true },
                { date: 20, isToday: false, hasSession: false },
                { date: 21, isToday: false, hasSession: false },
                { date: 22, isToday: false, hasSession: false },
                { date: 23, isToday: false, hasSession: true },
                { date: 24, isToday: false, hasSession: false },
                { date: 25, isToday: false, hasSession: false }
            ]
        ]
    },
    bitacora: [
        { id: '1', title: 'Sesión con Carlos Patiño', description: 'Revisión de integrales por sustitución. Se recomienda reforzar derivadas.', dateLabel: '13 may' },
        { id: '2', title: 'Sesión con María López', description: 'Análisis de distribuciones de probabilidad. Avance satisfactorio.', dateLabel: '10 may' }
    ],
    mentoresDestacados: [
        { id: '1', name: 'Carlos Patiño', initials: 'CP', color: '#4ade80', subjects: 'Matemáticas · Ing. Sistemas', rating: 4.9 },
        { id: '2', name: 'María López', initials: 'ML', color: '#f59e0b', subjects: 'Estadística · Economía', rating: 4.7 },
        { id: '3', name: 'Ricardo Arango', initials: 'RA', color: '#60a5fa', subjects: 'Programación · Ingeniería', rating: 4.5 }
    ]
};