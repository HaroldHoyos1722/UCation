import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

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
    mentorColor: string;
    subject: string;
    dateLabel: string;
    status: 'aceptada' | 'pendiente' | 'finalizada';
}

export interface CalendarDay {
    date: number | null;
    isToday: boolean;
    hasSession: boolean;
}

export interface AgendaMonth {
    label: string;
    weeks: CalendarDay[][];
}

export interface DashboardData {
    greeting: string;
    greetingSubtitle: string;
    currentDate: string;
    stats: DashboardStats;
    mentorias: MentoriaItem[];
    agenda: AgendaMonth;
}

@Injectable({ providedIn: 'root' })
export class DashboardService {
    // TODO: reemplazar con HttpClient cuando esté el backend
    getDashboardData(): Observable<DashboardData> {
        return of(MOCK_DASHBOARD);
    }
}

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
                { date: null, isToday: false, hasSession: false }, { date: null, isToday: false, hasSession: false },
                { date: null, isToday: false, hasSession: false }, { date: 1, isToday: false, hasSession: false },
                { date: 2, isToday: false, hasSession: false }, { date: 3, isToday: false, hasSession: false },
                { date: 4, isToday: false, hasSession: false }
            ],
            [
                { date: 5, isToday: false, hasSession: false }, { date: 6, isToday: false, hasSession: false },
                { date: 7, isToday: false, hasSession: false }, { date: 8, isToday: false, hasSession: true },
                { date: 9, isToday: false, hasSession: false }, { date: 10, isToday: false, hasSession: false },
                { date: 11, isToday: false, hasSession: false }
            ],
            [
                { date: 12, isToday: false, hasSession: false }, { date: 13, isToday: false, hasSession: false },
                { date: 14, isToday: true, hasSession: false }, { date: 15, isToday: false, hasSession: true },
                { date: 16, isToday: false, hasSession: false }, { date: 17, isToday: false, hasSession: false },
                { date: 18, isToday: false, hasSession: false }
            ],
            [
                { date: 19, isToday: false, hasSession: true }, { date: 20, isToday: false, hasSession: false },
                { date: 21, isToday: false, hasSession: false }, { date: 22, isToday: false, hasSession: false },
                { date: 23, isToday: false, hasSession: true }, { date: 24, isToday: false, hasSession: false },
                { date: 25, isToday: false, hasSession: false }
            ]
        ]
    }
};