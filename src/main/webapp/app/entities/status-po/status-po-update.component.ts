import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatusPO, StatusPO } from 'app/shared/model/status-po.model';
import { StatusPOService } from './status-po.service';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order/purchase-order.service';

@Component({
  selector: 'jhi-status-po-update',
  templateUrl: './status-po-update.component.html',
})
export class StatusPOUpdateComponent implements OnInit {
  isSaving = false;
  purchaseorders: IPurchaseOrder[] = [];

  editForm = this.fb.group({
    id: [],
    statusPoId: [],
    statusPoName: [],
    purchaseOrder: [],
  });

  constructor(
    protected statusPOService: StatusPOService,
    protected purchaseOrderService: PurchaseOrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusPO }) => {
      this.updateForm(statusPO);

      this.purchaseOrderService.query().subscribe((res: HttpResponse<IPurchaseOrder[]>) => (this.purchaseorders = res.body || []));
    });
  }

  updateForm(statusPO: IStatusPO): void {
    this.editForm.patchValue({
      id: statusPO.id,
      statusPoId: statusPO.statusPoId,
      statusPoName: statusPO.statusPoName,
      purchaseOrder: statusPO.purchaseOrder,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusPO = this.createFromForm();
    if (statusPO.id !== undefined) {
      this.subscribeToSaveResponse(this.statusPOService.update(statusPO));
    } else {
      this.subscribeToSaveResponse(this.statusPOService.create(statusPO));
    }
  }

  private createFromForm(): IStatusPO {
    return {
      ...new StatusPO(),
      id: this.editForm.get(['id'])!.value,
      statusPoId: this.editForm.get(['statusPoId'])!.value,
      statusPoName: this.editForm.get(['statusPoName'])!.value,
      purchaseOrder: this.editForm.get(['purchaseOrder'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusPO>>): void {
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

  trackById(index: number, item: IPurchaseOrder): any {
    return item.id;
  }
}
