import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificacionesPopupComponent } from './notificaciones-popup.component';

describe('NotificacionesPopupComponent', () => {
  let component: NotificacionesPopupComponent;
  let fixture: ComponentFixture<NotificacionesPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotificacionesPopupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificacionesPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
