import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventory } from 'app/shared/model/inventory.model';

type EntityResponseType = HttpResponse<IInventory>;
type EntityArrayResponseType = HttpResponse<IInventory[]>;

@Injectable({ providedIn: 'root' })
export class InventoryService {
  public resourceUrl = SERVER_API_URL + 'api/inventories';

  constructor(protected http: HttpClient) {}

  create(inventory: IInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventory);
    return this.http
      .post<IInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventory: IInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventory);
    return this.http
      .put<IInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventory: IInventory): IInventory {
    const copy: IInventory = Object.assign({}, inventory, {
      lasterUpdated: inventory.lasterUpdated && inventory.lasterUpdated.isValid() ? inventory.lasterUpdated.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lasterUpdated = res.body.lasterUpdated ? moment(res.body.lasterUpdated) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventory: IInventory) => {
        inventory.lasterUpdated = inventory.lasterUpdated ? moment(inventory.lasterUpdated) : undefined;
      });
    }
    return res;
  }
}
