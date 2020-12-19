import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStatusPO } from 'app/shared/model/status-po.model';

type EntityResponseType = HttpResponse<IStatusPO>;
type EntityArrayResponseType = HttpResponse<IStatusPO[]>;

@Injectable({ providedIn: 'root' })
export class StatusPOService {
  public resourceUrl = SERVER_API_URL + 'api/status-pos';

  constructor(protected http: HttpClient) {}

  create(statusPO: IStatusPO): Observable<EntityResponseType> {
    return this.http.post<IStatusPO>(this.resourceUrl, statusPO, { observe: 'response' });
  }

  update(statusPO: IStatusPO): Observable<EntityResponseType> {
    return this.http.put<IStatusPO>(this.resourceUrl, statusPO, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatusPO>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatusPO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
