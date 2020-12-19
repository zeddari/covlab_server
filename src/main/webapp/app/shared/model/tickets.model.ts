import { Moment } from 'moment';
import { IOutlet } from 'app/shared/model/outlet.model';
import { IProduct } from 'app/shared/model/product.model';

export interface ITickets {
  id?: number;
  ticketNo?: number;
  ticketType?: string;
  ticketStatus?: string;
  ticketDueDate?: Moment;
  ticketPriority?: string;
  ticketCreatedOn?: Moment;
  ticketUpdateAt?: Moment;
  outlet?: IOutlet;
  product?: IProduct;
}

export class Tickets implements ITickets {
  constructor(
    public id?: number,
    public ticketNo?: number,
    public ticketType?: string,
    public ticketStatus?: string,
    public ticketDueDate?: Moment,
    public ticketPriority?: string,
    public ticketCreatedOn?: Moment,
    public ticketUpdateAt?: Moment,
    public outlet?: IOutlet,
    public product?: IProduct
  ) {}
}
