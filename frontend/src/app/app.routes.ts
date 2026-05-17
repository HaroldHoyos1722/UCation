import { Routes } from '@angular/router';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import { MentoresComponent } from './pages/mentores/mentores.component';
import { MentoriasComponent } from './pages/mentorias/mentorias.component';
import { BitacoraComponent } from './pages/bitacora/bitacora.component';


export const routes: Routes = [
  // Login por defecto
  {
    path: '',
    component: LoginComponent
  },

  // Páginas con sidebar
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: 'home', component: HomeComponent },
      {path: 'perfil', component: PerfilComponent },
      { path: 'mentores', component: MentoresComponent },
      { path: 'mentorias', component: MentoriasComponent },
      { path: 'bitacora', component: BitacoraComponent }

    ]
  },

  // fallback
  {
    path: '**',
    redirectTo: ''
  }
];