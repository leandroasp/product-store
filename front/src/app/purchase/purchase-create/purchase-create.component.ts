import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PurchaseService } from '../purchase.service';

@Component({
  selector: 'app-purchase-create',
  templateUrl: './purchase-create.component.html',
  styleUrls: ['./purchase-create.component.css'],
})
export class PurchaseCreateComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private service: PurchaseService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      description: [null, [Validators.required]],
    });
  }

  onSubimit() {
    if (this.form.valid) {
      
      this.service.create(this.form.value)
      .subscribe(
        (reply) => {
          this.router.navigate([`purchase/${reply.id}/addproduct`]);
          this.service.mensage("Pedido criado com sucesso. Agora adicione os produtos.");
        },
        (err) => {
          if (err.error.errors.length > 0) {
            this.service.mensage(err.error.errors[0].message);
          }
        }
      );

    } else {
      this.verifyFieldsForm(this.form);
    }
  }

  verifyFieldsForm(fieldGroup: FormGroup) {
    Object.keys(fieldGroup.controls).forEach((field) => {
      const controle = fieldGroup.get(field);

      if (controle instanceof FormGroup) {
        this.verifyFieldsForm(controle);
      } else {
        controle?.markAsDirty();
        controle?.markAsTouched();
      }
    });
  }

  verifyValidAndTouched(field: string): boolean {
    return (
      !this.form.get(field)?.valid &&
      this.form.get(field)!.touched &&
      this.form.get(field)!.dirty
    );
  }
}
