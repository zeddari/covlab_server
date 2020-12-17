import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPoStatus, PoStatus } from 'app/shared/model/po-status.model';
import { PoStatusService } from './po-status.service';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order/purchase-order.service';

@Component({
  selector: 'jhi-po-status-update',
  templateUrl: './po-status-update.component.html',
})
export class PoStatusUpdateComponent implements OnInit {
  isSaving = false;
  purchaseorders: IPurchaseOrder[] = [];
  updatedAtDp: any;

  editForm = this.fb.group({
    id: [],
    status: [],
    updatedAt: [],
    purchaseOrder: [],
  });

  constructor(
    protected poStatusService: PoStatusService,
    protected purchaseOrderService: PurchaseOrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poStatus }) => {
      this.updateForm(poStatus);

      this.purchaseOrderService.query().subscribe((res: HttpResponse<IPurchaseOrder[]>) => (this.purchaseorders = res.body || []));
    });
  }

  updateForm(poStatus: IPoStatus): void {
    this.editForm.patchValue({
      id: poStatus.id,
      status: poStatus.status,
      updatedAt: poStatus.updatedAt,
      purchaseOrder: poStatus.purchaseOrder,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poStatus = this.createFromForm();
    if (poStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.poStatusService.update(poStatus));
    } else {
      this.subscribeToSaveResponse(this.poStatusService.create(poStatus));
    }
  }

  private createFromForm(): IPoStatus {
    return {
      ...new PoStatus(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      purchaseOrder: this.editForm.get(['purchaseOrder'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoStatus>>): void {
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
