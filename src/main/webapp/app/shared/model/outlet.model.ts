import { IInventory } from 'app/shared/model/inventory.model';

export interface IOutlet {
  id?: number;
  outletId?: string;
  outletName?: string;
  outletLocation?: string;
  outletLat?: string;
  outletLong?: string;
  inventories?: IInventory[];
}

export class Outlet implements IOutlet {
  constructor(
    public id?: number,
    public outletId?: string,
    public outletName?: string,
    public outletLocation?: string,
    public outletLat?: string,
    public outletLong?: string,
    public inventories?: IInventory[]
  ) {}
}
