import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Cidade} from "../shared/model/cidade.model";
import {catchError, Observable, throwError} from 'rxjs';
import {Page} from "../shared/model/page.model";


@Injectable({
  providedIn: 'root'
})
export class CidadeService {

  private readonly API = 'api/cidade';

  constructor(private httpClient: HttpClient) {
  }

  buscarTodasCidades(): Observable<Cidade[]> {
    return this.httpClient.get<Cidade[]>(this.API);
  }

  isCidadeAssociada(id: number): Observable<Boolean> {
    return this.httpClient.get<Boolean>(`${this.API}/associada/${id}`);
  }


  buscarTodasCidadesPage(page: number, size: number): Observable<Page<Cidade>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.httpClient.get<Page<Cidade>>(`${this.API}/page`, { params });
  }

  salvar(cidade: Cidade): Observable<Cidade> {
    return this.httpClient.post<Cidade>(this.API, cidade);
  }

  apagar(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.API}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  atualizar(cidade: Cidade): Observable<Cidade> {
    return this.httpClient.put<Cidade>(`${this.API}/${cidade.id}`, cidade);
  }

  obterEstados(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.API}/estados`);
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
