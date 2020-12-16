import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOutlet } from 'app/shared/model/outlet.model';

type EntityResponseType = HttpResponse<IOutlet>;
type EntityArrayResponseType = HttpResponse<IOutlet[]>;

@Injectable({ providedIn: 'root' })
export class OutletService {
  public resourceUrl = SERVER_API_URL + 'api/outlets';

  constructor(protected http: HttpClient) {}

  create(outlet: IOutlet): Observable<EntityResponseType> {
    return this.http.post<IOutlet>(this.resourceUrl, outlet, { observe: 'response' });
  }

  update(outlet: IOutlet): Observable<EntityResponseType> {
    return this.http.put<IOutlet>(this.resourceUrl, outlet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOutlet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOutlet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
