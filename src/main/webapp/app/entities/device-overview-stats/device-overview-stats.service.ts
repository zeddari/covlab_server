import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';

type EntityResponseType = HttpResponse<IDeviceOverviewStats>;
type EntityArrayResponseType = HttpResponse<IDeviceOverviewStats[]>;

@Injectable({ providedIn: 'root' })
export class DeviceOverviewStatsService {
  public resourceUrl = SERVER_API_URL + 'api/device-overview-stats';

  constructor(protected http: HttpClient) {}

  create(deviceOverviewStats: IDeviceOverviewStats): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceOverviewStats);
    return this.http
      .post<IDeviceOverviewStats>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deviceOverviewStats: IDeviceOverviewStats): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deviceOverviewStats);
    return this.http
      .put<IDeviceOverviewStats>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeviceOverviewStats>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeviceOverviewStats[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deviceOverviewStats: IDeviceOverviewStats): IDeviceOverviewStats {
    const copy: IDeviceOverviewStats = Object.assign({}, deviceOverviewStats, {
      timestamp:
        deviceOverviewStats.timestamp && deviceOverviewStats.timestamp.isValid()
          ? deviceOverviewStats.timestamp.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timestamp = res.body.timestamp ? moment(res.body.timestamp) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((deviceOverviewStats: IDeviceOverviewStats) => {
        deviceOverviewStats.timestamp = deviceOverviewStats.timestamp ? moment(deviceOverviewStats.timestamp) : undefined;
      });
    }
    return res;
  }
}
