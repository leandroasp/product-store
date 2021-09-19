import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../Product.model';
import { Purchase } from '../Purchase.model';
import { PurchaseService } from '../purchase.service';
import { PurchaseProduct } from '../PurchaseProduct.model';

@Component({
  selector: 'app-purchase-addproduct',
  templateUrl: './purchase-addproduct.component.html',
  styleUrls: ['./purchase-addproduct.component.css']
})
export class PurchaseAddproductComponent implements OnInit {
  products: Product[] = [];
  purchase: Purchase = {description: ""};
  total = 0.0;
  form!: FormGroup;

  constructor(
    private service: PurchaseService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.purchase.id = this.route.snapshot.paramMap.get("id")!;
    this.findById();
    this.getTotal(this.purchase.id);

    this.form = this.formBuilder.group({
      purchase_id: this.purchase.id,
      product_id: [null, [Validators.required]],
      quantity: [null, [Validators.required]],
    });
  }

  findById(): void {
    this.service.findById(this.purchase.id!).subscribe(
      (reply) => {
        this.purchase = reply;
        this.products = [];
        this.purchase.products?.forEach((value, index) => {
          this.products.push({
            id: value.product.id,
            name: value.product.name,
            quantity: value.quantity
          });
        });
    },
    (err) => {
      this.service.mensage(err.error.error);
      this.router.navigate([`purchase`]);
    });
  }

  addProduct() {
    if (this.form.valid) {
      
      this.service.addProduct(this.form.value)
      .subscribe(
        (reply) => {
          this.form.reset();
          this.ngOnInit();

          this.service.mensage("Produto adicionado com sucesso.");
        },
        (err) => {
          if (err.error.errors != undefined && err.error.errors.length > 0) {
            this.service.mensage(err.error.errors[0].message);
          } else {
            this.service.mensage(err.error.error);
          }
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

  deleteProduct(productId: any, quantity: any) {
    let purchaseProduct: PurchaseProduct = {
      product_id: productId,
      purchase_id: this.purchase.id!,
      quantity: quantity
    }

    if (confirm("Realmente deseja remover o produto deste pedido?")) {
      this.service.deleteProduct(purchaseProduct).subscribe(
        (reply) => {
          this.ngOnInit();
          this.service.mensage("Produto removido com sucesso");
        },
        (err) => {
          this.service.mensage(err.error.error);
        });
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
