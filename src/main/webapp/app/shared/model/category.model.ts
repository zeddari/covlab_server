import { IProduct } from 'app/shared/model/product.model';

export interface ICategory {
  id?: number;
  categoryId?: number;
  categoryCode?: string;
  categoryDescription?: string;
  products?: IProduct[];
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public categoryId?: number,
    public categoryCode?: string,
    public categoryDescription?: string,
    public products?: IProduct[]
  ) {}
}
