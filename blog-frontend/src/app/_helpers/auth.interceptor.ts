import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

import { TokenStorageService } from '../_services/token-storage/token-storage.service';

const TOKEN_HEADER_KEY = 'Authorization';
// export const API_BASE_URL = 'http://blog-backend-dev.eu-central-1.elasticbeanstalk.com/'
export const API_BASE_URL = 'http://localhost:8080/'


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private token: TokenStorageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authReq = req;
    const token = this.token.getToken();
    // filter amazon requests due to AWS "Only one auth mechanism allowed"
    if (token != null && !req.url.includes("amazonaws")) {
      authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
    }
    return next.handle(authReq);
  }
}

export const authInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];