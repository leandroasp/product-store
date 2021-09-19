import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Purchase } from '../Purchase.model';
import { PurchaseService } from '../purchase.service';

@Component({
  selector: 'app-purchase-close',
  templateUrl: './purchase-close.component.html',
  styleUrls: ['./purchase-close.component.css']
})
export class PurchaseCloseComponent implements OnInit {
  purchase: Purchase = {description: ""};
  form!: FormGroup;
  total = 0.0;
  change = -1;

  constructor(
    private route: ActivatedRoute,
    private service: PurchaseService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.purchase.id = this.route.snapshot.paramMap.get("id")!;
    this.getTotal(this.purchase.id);

    this.form = this.formBuilder.group({
      purchase_id: this.purchase.id,
      payment: [null, [Validators.required]]
    });
  }

  closePurchase() {
    if (this.form.valid) {
      this.service.closePurchase(this.form.value)
      .subscribe(
        (reply) => {
          this.form.reset();
          this.change = reply.change;
        },
        (err) => {
          this.service.mensage(err.error.error);
        }
      );

    } else {
      this.verifyFieldsForm(this.form);
    }
  }

  getTotal(purchaseId: String) {
    this.service.getTotal(this.purchase.id!).subscribe(
      (reply) => {
        this.total = reply.price
      },
      (err) => {
        this.service.mensage(err.error.error);
      });
  }

  verifyFieldsForm(fieldGroup: FormGroup) {
    Object.keys(fieldGroup.controls).forEach((field) => {
      const controle = fieldGroup.get(field);

      if (controle instanceof FormGroup) {
        this.verifyFieldsForm(controle);
      } else {
        console.log(field);

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
