import {HttpInterceptorFn} from '@angular/common/http';
import {TokenService} from "../token/token.service";
import {inject} from "@angular/core";


export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {

  const tokenService: TokenService = inject(TokenService);

  const token = tokenService.token;

  if (req.url.includes('login') || req.url.includes('register') || req.url.includes('activate-account')) {
    return next(req);
  }
  if (token) {
    const headers = req.headers.set('Authorization', `Bearer ${token}`);
    const authRes = req.clone(
      {
        headers
      }
    )
    return next(authRes);
  }

  return next(req);
};
