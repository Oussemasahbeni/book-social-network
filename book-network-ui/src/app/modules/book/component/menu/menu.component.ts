import {Component} from '@angular/core';
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterModule, FormsModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {

  logout() {
    localStorage.removeItem('token');
    window.location.reload();
  }
}
