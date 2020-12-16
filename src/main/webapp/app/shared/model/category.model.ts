import { IProduct } from 'app/shared/model/product.model';

export interface ICategory {
  id?: number;
  categoryId?: number;
  descriptionCategory?: string;
  products?: IProduct[];
}

export class Category implements ICategory {
  constructor(public id?: number, public categoryId?: number, public descriptionCategory?: string, public products?: IProduct[]) {}
}
