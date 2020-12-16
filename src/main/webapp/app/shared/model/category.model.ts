import { IProduct } from 'app/shared/model/product.model';

export interface ICategory {
  id?: number;
  categoryId?: string;
  descriptionCategory?: string;
  product?: IProduct;
}

export class Category implements ICategory {
  constructor(public id?: number, public categoryId?: string, public descriptionCategory?: string, public product?: IProduct) {}
}
