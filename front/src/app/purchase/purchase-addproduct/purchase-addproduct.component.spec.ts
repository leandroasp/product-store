import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseAddproductComponent } from './purchase-addproduct.component';

describe('PurchaseAddproductComponent', () => {
  let component: PurchaseAddproductComponent;
  let fixture: ComponentFixture<PurchaseAddproductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchaseAddproductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseAddproductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
