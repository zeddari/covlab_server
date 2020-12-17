import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

export interface IStatusPO {
  id?: number;
  statusPoId?: number;
  statusPoName?: string;
  purchaseOrder?: IPurchaseOrder;
}

export class StatusPO implements IStatusPO {
  constructor(public id?: number, public statusPoId?: number, public statusPoName?: string, public purchaseOrder?: IPurchaseOrder) {}
}
