import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

type EntityResponseType = HttpResponse<IPurchaseOrder>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrder[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderService {
  public resourceUrl = SERVER_API_URL + 'api/purchase-orders';

  constructor(protected http: HttpClient) {}

  create(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseOrder);
    return this.http
      .post<IPurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseOrder);
    return this.http
      .put<IPurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPurchaseOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPurchaseOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(purchaseOrder: IPurchaseOrder): IPurchaseOrder {
    const copy: IPurchaseOrder = Object.assign({}, purchaseOrder, {
      createdOn: purchaseOrder.createdOn && purchaseOrder.createdOn.isValid() ? purchaseOrder.createdOn.format(DATE_FORMAT) : undefined,
      deliveredDate:
        purchaseOrder.deliveredDate && purchaseOrder.deliveredDate.isValid() ? purchaseOrder.deliveredDate.format(DATE_FORMAT) : undefined,
      updatedAt: purchaseOrder.updatedAt && purchaseOrder.updatedAt.isValid() ? purchaseOrder.updatedAt.format(DATE_FORMAT) : undefined,
      createdAt: purchaseOrder.createdAt && purchaseOrder.createdAt.isValid() ? purchaseOrder.createdAt.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.deliveredDate = res.body.deliveredDate ? moment(res.body.deliveredDate) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((purchaseOrder: IPurchaseOrder) => {
        purchaseOrder.createdOn = purchaseOrder.createdOn ? moment(purchaseOrder.createdOn) : undefined;
        purchaseOrder.deliveredDate = purchaseOrder.deliveredDate ? moment(purchaseOrder.deliveredDate) : undefined;
        purchaseOrder.updatedAt = purchaseOrder.updatedAt ? moment(purchaseOrder.updatedAt) : undefined;
        purchaseOrder.createdAt = purchaseOrder.createdAt ? moment(purchaseOrder.createdAt) : undefined;
      });
    }
    return res;
  }
}
