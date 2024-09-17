export class Pageable {
  pageSize: number;
  pageNumber: number;
  offset: number;
  unpaged: boolean;
  paged: boolean;
  sort?: string;

  constructor(init?: Partial<Pageable>) {
    this.pageSize = init?.pageSize ?? 10;  // Valor padrão
    this.pageNumber = init?.pageNumber ?? 0;  // Valor padrão
    this.offset = init?.offset ?? 0;  // Valor padrão
    this.unpaged = init?.unpaged ?? false;
    this.paged = init?.paged ?? true;
    this.sort = init?.sort;
  }

  static of(pageSize: number, pageNumber: number = 0): Pageable {
    return new Pageable({ pageSize, pageNumber });
  }
}
