import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { IOutlet } from 'app/shared/model/outlet.model';

export interface IDeviceOverviewStats {
  id?: number;
  deviceId?: string;
  timestamp?: Moment;
  serialNumber?: string;
  humidity?: number;
  temperature?: number;
  co2?: number;
  pressure?: number;
  differentialPressure?: number;
  products?: IProduct[];
  outlet?: IOutlet;
}

export class DeviceOverviewStats implements IDeviceOverviewStats {
  constructor(
    public id?: number,
    public deviceId?: string,
    public timestamp?: Moment,
    public serialNumber?: string,
    public humidity?: number,
    public temperature?: number,
    public co2?: number,
    public pressure?: number,
    public differentialPressure?: number,
    public products?: IProduct[],
    public outlet?: IOutlet
  ) {}
}
