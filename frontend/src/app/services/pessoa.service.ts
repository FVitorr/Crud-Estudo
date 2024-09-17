import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pessoa } from '../shared/model/pessoa';
import { FiltroPessoaVO } from '../model/filtro-pessoa-vo';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  private readonly API = 'api/pessoa';

  constructor(private httpClient: HttpClient) { }

  // Salvar nova pessoa
  salvar(pessoaDTO: Pessoa): Observable<Pessoa> {
    return this.httpClient.post<Pessoa>(`${this.API}`, pessoaDTO);
  }

  // Buscar todas as pessoas com paginação e filtro
  buscarTodasPessoas(page: number, size: number, filtro: FiltroPessoaVO): Observable<any> {
    const params = { page: `${page}`, size: `${size}`, ...filtro };
    return this.httpClient.get<any>(`${this.API}/consultar`, { params });
  }

  // Buscar pessoa por ID
  buscarPessoaPorId(pessoaId: number): Observable<Pessoa> {
    return this.httpClient.get<Pessoa>(`${this.API}/${pessoaId}`);
  }

  // Atualizar pessoa existente
  atualizar(pessoaDTO: Pessoa): Observable<void> {
    return this.httpClient.put<void>(`${this.API}/${pessoaDTO.id}`, pessoaDTO);
  }

  // Apagar pessoa por ID
  apagar(pessoaId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.API}/${pessoaId}`);
  }

  // Verificar se existe cidade
  verificarExisteCidade(): Observable<boolean> {
    return this.httpClient.get<boolean>(`${this.API}/verificar-cidade`);
  }

  // Gerar PDF para uma pessoa específica
  gerarPdfGeral(pessoaId: number): Observable<Blob> {
    return this.httpClient.get(`${this.API}/gerar-pdf/${pessoaId}`, { responseType: 'blob' });
  }
}
