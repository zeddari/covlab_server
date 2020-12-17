import { IInventory } from 'app/shared/model/inventory.model';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { ITickets } from 'app/shared/model/tickets.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IProduct {
  id?: number;
  productId?: number;
  description?: string;
  productCode?: string;
  temperature?: string;
  inventories?: IInventory[];
  purchaseOrders?: IPurchaseOrder[];
  tickets?: ITickets[];
  category?: ICategory;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productId?: number,
    public description?: string,
    public productCode?: string,
    public temperature?: string,
    public inventories?: IInventory[],
    public purchaseOrders?: IPurchaseOrder[],
    public tickets?: ITickets[],
    public category?: ICategory
  ) {}
}
