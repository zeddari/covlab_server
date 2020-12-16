import { Moment } from 'moment';
import { IOutlet } from 'app/shared/model/outlet.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IInventory {
  id?: number;
  inventoryId?: number;
  quantitiesInHand?: number;
  quantitiesInTransit?: number;
  uom?: string;
  actualDailyConsumption?: number;
  actualAvgConsumption?: number;
  reOrderLevel?: string;
  suggestedQuantity?: number;
  expectedCoveringDay?: number;
  lastUpdatedAt?: Moment;
  status?: string;
  outlet?: IOutlet;
  product?: IProduct;
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public inventoryId?: number,
    public quantitiesInHand?: number,
    public quantitiesInTransit?: number,
    public uom?: string,
    public actualDailyConsumption?: number,
    public actualAvgConsumption?: number,
    public reOrderLevel?: string,
    public suggestedQuantity?: number,
    public expectedCoveringDay?: number,
    public lastUpdatedAt?: Moment,
    public status?: string,
    public outlet?: IOutlet,
    public product?: IProduct
  ) {}
}
