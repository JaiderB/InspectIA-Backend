import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { environment } from '@env/enviromnet.prod';
import { Observable, tap } from 'rxjs';

import { Analysis } from '@app/core/models/Analysis/analysis.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AnalysisService {

  private baseUrl = `${environment.apiUrl}/analyze`;

  constructor(
    private http: HttpClient,
    @Inject(AuthService) private authService: AuthService
  ) { }

  createNewAnalysis(files: File[]): Observable<Analysis> {
    const formData = new FormData();
    for (const file of files) {
      formData.append('files', file, file.name);
    }
    const endpointUrl = `${this.baseUrl}/create`;
    const headers: HttpHeaders = this.authService.getAuthorizationHeader();
    return this.http.post<Analysis>(endpointUrl, formData, {headers: headers});
  }

  getAnalysisById(analysisId: string): Observable<Analysis> {
    const endpointUrl = `${this.baseUrl}/get/${analysisId}`;
    return this.http.get<Analysis>(endpointUrl);
  }

  getAnalysisByUser(){
    const endpointUrl = `${this.baseUrl}/getByUser`;
    const headers: HttpHeaders = this.authService.getAuthorizationHeader();
    return this.http.get<Analysis[]>(endpointUrl, {headers: headers}).pipe(
      tap(response => console.log(response))
    );

  }

  deleteAnalysisByHash(hashId: string){
    const endpointUrl = `${this.baseUrl}/delete/${hashId}`;
    const headers: HttpHeaders = this.authService.getAuthorizationHeader();
    return this.http.delete(endpointUrl, {headers: headers})
  }
}
