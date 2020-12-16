import { IInventory } from 'app/shared/model/inventory.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IProduct {
  id?: number;
  productId?: number;
  description?: string;
  productCode?: string;
  inventories?: IInventory[];
  category?: ICategory;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productId?: number,
    public description?: string,
    public productCode?: string,
    public inventories?: IInventory[],
    public category?: ICategory
  ) {}
}
