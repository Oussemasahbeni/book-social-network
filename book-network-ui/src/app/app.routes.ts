import {Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";
import {AuthGuard} from "./services/guard/auth.guard";

export const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "activate-account",
    component: ActivateAccountComponent
  },
  {
    path: "books",
    canActivate: [AuthGuard],
    loadChildren: () => import('./modules/book/book.routes'),
    // canActivate: [AuthGuard]
  }
];
