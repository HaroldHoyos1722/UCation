import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

// ── Interfaces ──────────────────────────────────────────────

export type TipoNotificacion =
    | 'solicitud_aceptada'
    | 'solicitud_rechazada'
    | 'sesion_programada'
    | 'sesion_cancelada'
    | 'sesion_reprogramada'
    | 'bitacora_registrada'
    | 'mentoria_finalizada';

export interface Notificacion {
    id: string;
    tipo: TipoNotificacion;
    titulo: string;
    mensaje: string;
    leida: boolean;
    fecha: string; // ISO
}

// ── Servicio ─────────────────────────────────────────────────

@Injectable({ providedIn: 'root' })
export class NotificacionesService {

    // TODO: reemplazar con HttpClient + WebSocket/SSE cuando esté el backend
    // getNotificaciones(): Observable<Notificacion[]>
    // marcarLeida(id: string): Observable<void>
    // marcarTodasLeidas(): Observable<void>

    getNotificaciones(): Observable<Notificacion[]> {
        return of(MOCK_NOTIFICACIONES);
    }

    marcarLeida(id: string): Observable<{ success: boolean }> {
        // TODO: return this.http.patch(`/api/notificaciones/${id}/leer`, {});
        return of({ success: true });
    }

    marcarTodasLeidas(): Observable<{ success: boolean }> {
        // TODO: return this.http.patch('/api/notificaciones/leer-todas', {});
        return of({ success: true });
    }
}

// ── Mock data ────────────────────────────────────────────────

const MOCK_NOTIFICACIONES: Notificacion[] = [
    {
        id: '1',
        tipo: 'solicitud_aceptada',
        titulo: 'Solicitud aceptada',
        mensaje: 'Carlos Patiño aceptó tu solicitud de mentoría.',
        leida: false,
        fecha: '2025-05-14T09:00:00'
    },
    {
        id: '2',
        tipo: 'sesion_programada',
        titulo: 'Sesión programada',
        mensaje: 'Se agendó una sesión con María López para el 20 de mayo a las 10:00 am.',
        leida: false,
        fecha: '2025-05-13T15:30:00'
    },
    {
        id: '3',
        tipo: 'bitacora_registrada',
        titulo: 'Bitácora actualizada',
        mensaje: 'Carlos Patiño registró las observaciones de tu última sesión.',
        leida: false,
        fecha: '2025-05-13T11:00:00'
    },
    {
        id: '4',
        tipo: 'sesion_cancelada',
        titulo: 'Sesión cancelada',
        mensaje: 'La sesión del 10 de mayo con Ricardo Arango fue cancelada.',
        leida: true,
        fecha: '2025-05-09T08:00:00'
    },
    {
        id: '5',
        tipo: 'mentoria_finalizada',
        titulo: 'Mentoría finalizada',
        mensaje: 'Tu mentoría con Ricardo Arango ha sido marcada como finalizada.',
        leida: true,
        fecha: '2025-05-07T16:00:00'
    }
];