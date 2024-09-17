import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Crud Angular + Spring Boot';

  constructor(private router: Router) {}

  toCidade() {
    this.router.navigate(['/cidade']);
  }

  toPessoa() {
    this.router.navigate(['/pessoa']);
  }
}
