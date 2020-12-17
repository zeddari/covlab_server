import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPurchaseOrder, PurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from './purchase-order.service';
import { IOutlet } from 'app/shared/model/outlet.model';
import { OutletService } from 'app/entities/outlet/outlet.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IOutlet | IProduct;

@Component({
  selector: 'jhi-purchase-order-update',
  templateUrl: './purchase-order-update.component.html',
})
export class PurchaseOrderUpdateComponent implements OnInit {
  isSaving = false;
  outlets: IOutlet[] = [];
  products: IProduct[] = [];
  createdOnDp: any;
  deliveredDateDp: any;
  updatedAtDp: any;
  createdAtDp: any;

  editForm = this.fb.group({
    id: [],
    orderNo: [],
    quantity: [],
    createdBy: [],
    createdOn: [],
    deliveredDate: [],
    updatedAt: [],
    createdAt: [],
    outlet: [],
    product: [],
  });

  constructor(
    protected purchaseOrderService: PurchaseOrderService,
    protected outletService: OutletService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseOrder }) => {
      this.updateForm(purchaseOrder);

      this.outletService.query().subscribe((res: HttpResponse<IOutlet[]>) => (this.outlets = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(purchaseOrder: IPurchaseOrder): void {
    this.editForm.patchValue({
      id: purchaseOrder.id,
      orderNo: purchaseOrder.orderNo,
      quantity: purchaseOrder.quantity,
      createdBy: purchaseOrder.createdBy,
      createdOn: purchaseOrder.createdOn,
      deliveredDate: purchaseOrder.deliveredDate,
      updatedAt: purchaseOrder.updatedAt,
      createdAt: purchaseOrder.createdAt,
      outlet: purchaseOrder.outlet,
      product: purchaseOrder.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchaseOrder = this.createFromForm();
    if (purchaseOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseOrderService.update(purchaseOrder));
    } else {
      this.subscribeToSaveResponse(this.purchaseOrderService.create(purchaseOrder));
    }
  }

  private createFromForm(): IPurchaseOrder {
    return {
      ...new PurchaseOrder(),
      id: this.editForm.get(['id'])!.value,
      orderNo: this.editForm.get(['orderNo'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value,
      deliveredDate: this.editForm.get(['deliveredDate'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value,
      outlet: this.editForm.get(['outlet'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrder>>): void {
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
