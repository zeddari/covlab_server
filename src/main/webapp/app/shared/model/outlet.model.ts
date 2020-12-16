import { IInventory } from 'app/shared/model/inventory.model';

export interface IOutlet {
  id?: number;
  outletId?: number;
  outletName?: string;
  outletRegion?: string;
  outletAdress?: string;
  outletLat?: number;
  outletLng?: number;
  inventories?: IInventory[];
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
    public inventories?: IInventory[]
  ) {}
}
