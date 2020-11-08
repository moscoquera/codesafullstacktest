import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserFormComponent } from './user-form/user-form.component';
import {ReactiveFormsModule} from "@angular/forms";
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    UserFormComponent
  ],
    imports: [
        BrowserModule,
        NgbModule,
        ReactiveFormsModule,
        NgSelectModule,
        FormsModule,
        HttpClientModule,
        DataTablesModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
