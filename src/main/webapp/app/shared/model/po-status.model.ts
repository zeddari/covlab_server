import { Moment } from 'moment';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

export interface IPoStatus {
  id?: number;
  status?: string;
  updatedAt?: Moment;
  purchaseOrder?: IPurchaseOrder;
}

export class PoStatus implements IPoStatus {
  constructor(public id?: number, public status?: string, public updatedAt?: Moment, public purchaseOrder?: IPurchaseOrder) {}
}
