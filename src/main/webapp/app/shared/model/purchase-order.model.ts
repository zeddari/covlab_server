import { Moment } from 'moment';
import { IPoStatus } from 'app/shared/model/po-status.model';
import { IOutlet } from 'app/shared/model/outlet.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IPurchaseOrder {
  id?: number;
  orderNo?: number;
  quantity?: number;
  createdBy?: string;
  createdOn?: Moment;
  deliveredDate?: Moment;
  updatedAt?: Moment;
  createdAt?: Moment;
  poStatuses?: IPoStatus[];
  outlet?: IOutlet;
  product?: IProduct;
}

export class PurchaseOrder implements IPurchaseOrder {
  constructor(
    public id?: number,
    public orderNo?: number,
    public quantity?: number,
    public createdBy?: string,
    public createdOn?: Moment,
    public deliveredDate?: Moment,
    public updatedAt?: Moment,
    public createdAt?: Moment,
    public poStatuses?: IPoStatus[],
    public outlet?: IOutlet,
    public product?: IProduct
  ) {}
}
