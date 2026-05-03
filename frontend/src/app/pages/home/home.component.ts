import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
})

export class HomeComponent {

  mensaje = '';

  constructor(private api: ApiService) {
    this.api.getHello().subscribe(() => {
      console.log("Front y Backend se comunican correctamente");
    });
  }
}