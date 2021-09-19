import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from "@angular/material/snack-bar";
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Purchase } from './Purchase.model';
import { PurchaseProduct } from './PurchaseProduct.model';

@Injectable({
  providedIn: 'root',
})
export class PurchaseService {
  baseUrl: String = environment.baseUrl;

  constructor(private http: HttpClient, private _snack: MatSnackBar) {}

  create(purchase: Purchase): Observable<Purchase> {
    const url = `${this.baseUrl}purchase`;
    return this.http.post<Purchase>(url, purchase);
  }

  addProduct(purchaseProduct: PurchaseProduct): Observable<Purchase> {
    const url = `${this.baseUrl}purchase/add`;
    return this.http.post<Purchase>(url, purchaseProduct);
  }

  findById(id: String): Observable<Purchase> {
    const url = `${this.baseUrl}purchase/${id}`;
    return this.http.get<Purchase>(url);
  }

  getTotal(purchaseId: String): Observable<any> {
    const url = `${this.baseUrl}purchase/${purchaseId}/total`;
    return this.http.get<any>(url);
  }

  deleteProduct(purchaseProduct: PurchaseProduct): Observable<void> {
    const url = `${this.baseUrl}purchase/del`;
    return this.http.delete<void>(url, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: purchaseProduct
    });
  }

  closePurchase(purchase: any): Observable<any> {
    const url = `${this.baseUrl}purchase/${purchase.purchase_id}/close?payment=${purchase.payment}`;
    return this.http.get<any>(url);
  }

  mensage(str: String): void {
    this._snack.open(`${str}`, 'OK', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 5000,
    });
  }
}
