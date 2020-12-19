import { IInventory } from 'app/shared/model/inventory.model';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { ITickets } from 'app/shared/model/tickets.model';
import { IDeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';

export interface IOutlet {
  id?: number;
  outletId?: number;
  outletName?: string;
  outletRegion?: string;
  outletAdress?: string;
  outletLat?: number;
  outletLng?: number;
  inventories?: IInventory[];
  purchaseOrders?: IPurchaseOrder[];
  tickets?: ITickets[];
  deviceOverviewStats?: IDeviceOverviewStats[];
}

export class Outlet implements IOutlet {
  constructor(
    public id?: number,
    public outletId?: number,
    public outletName?: string,
    public outletRegion?: string,
    public outletAdress?: string,
    public outletLat?: number,
    public outletLng?: number,
    public inventories?: IInventory[],
    public purchaseOrders?: IPurchaseOrder[],
    public tickets?: ITickets[],
    public deviceOverviewStats?: IDeviceOverviewStats[]
  ) {}
}
