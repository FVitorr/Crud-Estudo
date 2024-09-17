import {Pageable} from "./pageable.model";

export declare class Page<T>{
  content: T[];
  pageable: Pageable;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;

  constructor(init?: Partial<Page<T>>);
}
