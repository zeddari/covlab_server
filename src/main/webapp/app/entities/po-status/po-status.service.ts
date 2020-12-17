import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPoStatus } from 'app/shared/model/po-status.model';

type EntityResponseType = HttpResponse<IPoStatus>;
type EntityArrayResponseType = HttpResponse<IPoStatus[]>;

@Injectable({ providedIn: 'root' })
export class PoStatusService {
  public resourceUrl = SERVER_API_URL + 'api/po-statuses';

  constructor(protected http: HttpClient) {}

  create(poStatus: IPoStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(poStatus);
    return this.http
      .post<IPoStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(poStatus: IPoStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(poStatus);
    return this.http
      .put<IPoStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPoStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPoStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(poStatus: IPoStatus): IPoStatus {
    const copy: IPoStatus = Object.assign({}, poStatus, {
      updatedAt: poStatus.updatedAt && poStatus.updatedAt.isValid() ? poStatus.updatedAt.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((poStatus: IPoStatus) => {
        poStatus.updatedAt = poStatus.updatedAt ? moment(poStatus.updatedAt) : undefined;
      });
    }
    return res;
  }
}
