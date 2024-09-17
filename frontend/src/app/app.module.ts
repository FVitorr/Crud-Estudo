import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatToolbarModule} from '@angular/material/toolbar';
import {AppRoutingModule} from './app.routes';
import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {HttpClientModule} from '@angular/common/http';
import {NgxMaskDirective, provideNgxMask} from 'ngx-mask';
import {MatIcon} from "@angular/material/icon";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatFormFieldModule,
    RouterModule,
    HttpClientModule,
    NgxMaskDirective,
    MatIcon
  ],
  bootstrap: [AppComponent],
  providers: [
    provideAnimationsAsync(),
    provideNgxMask()
  ]
})
export class AppModule {
}
