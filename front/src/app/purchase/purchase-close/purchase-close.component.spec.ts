import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseCloseComponent } from './purchase-close.component';

describe('PurchaseCloseComponent', () => {
  let component: PurchaseCloseComponent;
  let fixture: ComponentFixture<PurchaseCloseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchaseCloseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseCloseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
