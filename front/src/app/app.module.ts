import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatButtonModule } from "@angular/material/button";
import { registerLocaleData } from '@angular/common';
import ptBR from '@angular/common/locales/pt';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PurchaseCreateComponent } from './purchase/purchase-create/purchase-create.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ControlErroFieldComponent } from './util/control-erro-field/control-erro-field.component';
import { PurchaseAddproductComponent } from './purchase/purchase-addproduct/purchase-addproduct.component';
import { PurchaseCloseComponent } from './purchase/purchase-close/purchase-close.component';

registerLocaleData(ptBR, 'pt-BR');

@NgModule({
  declarations: [
    AppComponent,
    PurchaseCreateComponent,
    ControlErroFieldComponent,
    PurchaseAddproductComponent,
    PurchaseCloseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    MatButtonModule,
  ],
  providers: [
    {
      provide: LOCALE_ID,
      useValue: 'pt-BR'
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
