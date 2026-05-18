import { Pipe, PipeTransform } from '@angular/core';
import { Mentoria } from '../../services/mentorias.service';

@Pipe({ name: 'estadoFilter', standalone: true })
export class EstadoFilterPipe implements PipeTransform {
    transform(mentorias: Mentoria[], estado: string): Mentoria[] {
        return mentorias.filter(m => m.estado === estado);
    }
}