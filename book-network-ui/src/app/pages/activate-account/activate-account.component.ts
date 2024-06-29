import {Component} from '@angular/core';
import {CodeInputModule} from "angular-code-input";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

import {skipUntil} from 'rxjs';

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [CodeInputModule],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {

  message = '';
  isOkay = true;
  submitted = false;
  protected readonly skipUntil = skipUntil;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  private confirmAccount(token: string) {
    this.authService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message = 'Your account has been successfully activated.\nNow you can proceed to login';
        this.submitted = true;
      },
      error: () => {
        this.message = 'Token has been expired or invalid';
        this.submitted = true;
        this.isOkay = false;
      }
    });
  }

}
