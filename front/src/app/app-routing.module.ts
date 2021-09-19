import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PurchaseAddproductComponent } from './purchase/purchase-addproduct/purchase-addproduct.component';
import { PurchaseCloseComponent } from './purchase/purchase-close/purchase-close.component';
import { PurchaseCreateComponent } from './purchase/purchase-create/purchase-create.component';

const routes: Routes = [
  { path: 'purchase', component: PurchaseCreateComponent },
  { path: 'purchase/:id/addproduct', component: PurchaseAddproductComponent },
  { path: 'purchase/:id/close', component: PurchaseCloseComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
