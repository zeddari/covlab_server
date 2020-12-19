import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITickets } from 'app/shared/model/tickets.model';

type EntityResponseType = HttpResponse<ITickets>;
type EntityArrayResponseType = HttpResponse<ITickets[]>;

@Injectable({ providedIn: 'root' })
export class TicketsService {
  public resourceUrl = SERVER_API_URL + 'api/tickets';

  constructor(protected http: HttpClient) {}

  create(tickets: ITickets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tickets);
    return this.http
      .post<ITickets>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tickets: ITickets): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tickets);
    return this.http
      .put<ITickets>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITickets>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITickets[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tickets: ITickets): ITickets {
    const copy: ITickets = Object.assign({}, tickets, {
      ticketDueDate: tickets.ticketDueDate && tickets.ticketDueDate.isValid() ? tickets.ticketDueDate.format(DATE_FORMAT) : undefined,
      ticketCreatedOn:
        tickets.ticketCreatedOn && tickets.ticketCreatedOn.isValid() ? tickets.ticketCreatedOn.format(DATE_FORMAT) : undefined,
      ticketUpdateAt: tickets.ticketUpdateAt && tickets.ticketUpdateAt.isValid() ? tickets.ticketUpdateAt.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ticketDueDate = res.body.ticketDueDate ? moment(res.body.ticketDueDate) : undefined;
      res.body.ticketCreatedOn = res.body.ticketCreatedOn ? moment(res.body.ticketCreatedOn) : undefined;
      res.body.ticketUpdateAt = res.body.ticketUpdateAt ? moment(res.body.ticketUpdateAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tickets: ITickets) => {
        tickets.ticketDueDate = tickets.ticketDueDate ? moment(tickets.ticketDueDate) : undefined;
        tickets.ticketCreatedOn = tickets.ticketCreatedOn ? moment(tickets.ticketCreatedOn) : undefined;
        tickets.ticketUpdateAt = tickets.ticketUpdateAt ? moment(tickets.ticketUpdateAt) : undefined;
      });
    }
    return res;
  }
}
