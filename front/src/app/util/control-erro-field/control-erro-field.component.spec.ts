import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlErroFieldComponent } from './control-erro-field.component';

describe('ControlErroFieldComponent', () => {
  let component: ControlErroFieldComponent;
  let fixture: ComponentFixture<ControlErroFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ControlErroFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlErroFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
