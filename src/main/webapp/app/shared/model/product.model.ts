import { IInventory } from 'app/shared/model/inventory.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IProduct {
  id?: number;
  productId?: string;
  description?: string;
  productCode?: string;
  inventories?: IInventory[];
  category?: ICategory;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productId?: string,
    public description?: string,
    public productCode?: string,
    public inventories?: IInventory[],
    public category?: ICategory
  ) {}
}
