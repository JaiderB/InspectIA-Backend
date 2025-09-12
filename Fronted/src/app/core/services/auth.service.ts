import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Inject, Injectable } from '@angular/core';
import { environment } from '@env/enviromnet.prod';
import { LoginRequest } from '@models/Auth/login-request.model';
import { RegisterRequest } from '@models/Auth/register-request.model';
import { TokenResponse } from '@models/Auth/token-response.models';
import { CookieService } from 'ngx-cookie-service';
import { Observable, tap } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = `${environment.apiUrl}/auth`;

  constructor(
    private http: HttpClient,
    @Inject(CookieService) private cookieService: CookieService = inject(CookieService)
  ) {
    console.log("✅ TestCookieService inyectado y CookieService funciona!");

  }

  register(requestData: RegisterRequest): Observable<TokenResponse> {
    const endpointUrl = `${this.baseUrl}/register`;
    return this.http.post<TokenResponse>(endpointUrl, requestData).pipe(
      tap(response => this.saveTokensInCookies(response))
    );
  }

  login(credentials: LoginRequest): Observable<TokenResponse> {
    const endpointUrl = `${this.baseUrl}/login`;
    return this.http.post<TokenResponse>(endpointUrl, credentials).pipe(
      tap(response => this.saveTokensInCookies(response))
    );
  }

  refreshToken(): Observable<TokenResponse> {
    const endpointUrl = `${this.baseUrl}/refresh`;
    const headers = new HttpHeaders({ "refresh_token": this.getRefreshTokenFromCookie() });
    return this.http.post<TokenResponse>(endpointUrl, { headers: { headers } }).pipe(
      tap(response => this.saveTokensInCookies(response))
    );
  }

  logout() {
    const endpointUrl = `${this.baseUrl}/logout`;
    const headers = this.getAuthorizationHeader();
    return this.http.post<void>(endpointUrl, {} ,{headers: headers}).pipe(
      tap(() => this.clearTokenCookies())
    );
  }

  getAuthorizationHeader(){
    return new HttpHeaders().set("Authorization", `Bearer ${this.getAccessToken()}`);
  }

  private getRefreshTokenFromCookie(){
    return this.cookieService.get("accessToken");
  }

  private getAccessToken(){
    return this.cookieService.get("refreshToken");
  }

  private clearTokenCookies(){
    this.cookieService.delete('accessToken', '/');
    this.cookieService.delete('refreshToken', '/');
  }

  private saveTokensInCookies(response: TokenResponse) {

    const expires = new Date();
    expires.setDate(expires.getDate() + 1);
    this.cookieService.set(
      "accessToken",
      response.access_token,
      expires,
      "/",
      undefined,
      true,
      "Strict"
    )

    this.cookieService.set(
      "refreshToken",
      response.refresh_token,
      3,
      "/",
      undefined,
      true,
      "Strict"
    )
  }

}
