import { Moment } from 'moment';
import { IOutlet } from 'app/shared/model/outlet.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IInventory {
  id?: number;
  inventoryId?: string;
  itemCode?: string;
  description?: string;
  quantitiesInHand?: string;
  quantitiesInTransit?: string;
  uom?: string;
  actualDailyConsumption?: string;
  recordLevel?: string;
  suggestedQuantity?: string;
  expectedCoveringDay?: string;
  quantity?: string;
  location?: string;
  lasterUpdated?: Moment;
  outlet?: IOutlet;
  product?: IProduct;
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public inventoryId?: string,
    public itemCode?: string,
    public description?: string,
    public quantitiesInHand?: string,
    public quantitiesInTransit?: string,
    public uom?: string,
    public actualDailyConsumption?: string,
    public recordLevel?: string,
    public suggestedQuantity?: string,
    public expectedCoveringDay?: string,
    public quantity?: string,
    public location?: string,
    public lasterUpdated?: Moment,
    public outlet?: IOutlet,
    public product?: IProduct
  ) {}
}
