import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITickets, Tickets } from 'app/shared/model/tickets.model';
import { TicketsService } from './tickets.service';
import { IOutlet } from 'app/shared/model/outlet.model';
import { OutletService } from 'app/entities/outlet/outlet.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IOutlet | IProduct;

@Component({
  selector: 'jhi-tickets-update',
  templateUrl: './tickets-update.component.html',
})
export class TicketsUpdateComponent implements OnInit {
  isSaving = false;
  outlets: IOutlet[] = [];
  products: IProduct[] = [];
  ticketDueDateDp: any;
  ticketCreatedOnDp: any;
  ticketUpdateAtDp: any;

  editForm = this.fb.group({
    id: [],
    ticketNo: [],
    ticketType: [],
    ticketStatus: [],
    ticketDueDate: [],
    ticketPriority: [],
    ticketCreatedOn: [],
    ticketUpdateAt: [],
    outlet: [],
    product: [],
  });

  constructor(
    protected ticketsService: TicketsService,
    protected outletService: OutletService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tickets }) => {
      this.updateForm(tickets);

      this.outletService.query().subscribe((res: HttpResponse<IOutlet[]>) => (this.outlets = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(tickets: ITickets): void {
    this.editForm.patchValue({
      id: tickets.id,
      ticketNo: tickets.ticketNo,
      ticketType: tickets.ticketType,
      ticketStatus: tickets.ticketStatus,
      ticketDueDate: tickets.ticketDueDate,
      ticketPriority: tickets.ticketPriority,
      ticketCreatedOn: tickets.ticketCreatedOn,
      ticketUpdateAt: tickets.ticketUpdateAt,
      outlet: tickets.outlet,
      product: tickets.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tickets = this.createFromForm();
    if (tickets.id !== undefined) {
      this.subscribeToSaveResponse(this.ticketsService.update(tickets));
    } else {
      this.subscribeToSaveResponse(this.ticketsService.create(tickets));
    }
  }

  private createFromForm(): ITickets {
    return {
      ...new Tickets(),
      id: this.editForm.get(['id'])!.value,
      ticketNo: this.editForm.get(['ticketNo'])!.value,
      ticketType: this.editForm.get(['ticketType'])!.value,
      ticketStatus: this.editForm.get(['ticketStatus'])!.value,
      ticketDueDate: this.editForm.get(['ticketDueDate'])!.value,
      ticketPriority: this.editForm.get(['ticketPriority'])!.value,
      ticketCreatedOn: this.editForm.get(['ticketCreatedOn'])!.value,
      ticketUpdateAt: this.editForm.get(['ticketUpdateAt'])!.value,
      outlet: this.editForm.get(['outlet'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITickets>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
