import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInventory, Inventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';
import { IOutlet } from 'app/shared/model/outlet.model';
import { OutletService } from 'app/entities/outlet/outlet.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IOutlet | IProduct;

@Component({
  selector: 'jhi-inventory-update',
  templateUrl: './inventory-update.component.html',
})
export class InventoryUpdateComponent implements OnInit {
  isSaving = false;
  outlets: IOutlet[] = [];
  products: IProduct[] = [];
  lasterUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    inventoryId: [],
    itemCode: [],
    description: [],
    quantitiesInHand: [],
    quantitiesInTransit: [],
    uom: [],
    actualDailyConsumption: [],
    recordLevel: [],
    suggestedQuantity: [],
    expectedCoveringDay: [],
    quantity: [],
    location: [],
    lasterUpdated: [],
    outlet: [],
    product: [],
  });

  constructor(
    protected inventoryService: InventoryService,
    protected outletService: OutletService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventory }) => {
      this.updateForm(inventory);

      this.outletService.query().subscribe((res: HttpResponse<IOutlet[]>) => (this.outlets = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(inventory: IInventory): void {
    this.editForm.patchValue({
      id: inventory.id,
      inventoryId: inventory.inventoryId,
      itemCode: inventory.itemCode,
      description: inventory.description,
      quantitiesInHand: inventory.quantitiesInHand,
      quantitiesInTransit: inventory.quantitiesInTransit,
      uom: inventory.uom,
      actualDailyConsumption: inventory.actualDailyConsumption,
      recordLevel: inventory.recordLevel,
      suggestedQuantity: inventory.suggestedQuantity,
      expectedCoveringDay: inventory.expectedCoveringDay,
      quantity: inventory.quantity,
      location: inventory.location,
      lasterUpdated: inventory.lasterUpdated,
      outlet: inventory.outlet,
      product: inventory.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventory = this.createFromForm();
    if (inventory.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryService.update(inventory));
    } else {
      this.subscribeToSaveResponse(this.inventoryService.create(inventory));
    }
  }

  private createFromForm(): IInventory {
    return {
      ...new Inventory(),
      id: this.editForm.get(['id'])!.value,
      inventoryId: this.editForm.get(['inventoryId'])!.value,
      itemCode: this.editForm.get(['itemCode'])!.value,
      description: this.editForm.get(['description'])!.value,
      quantitiesInHand: this.editForm.get(['quantitiesInHand'])!.value,
      quantitiesInTransit: this.editForm.get(['quantitiesInTransit'])!.value,
      uom: this.editForm.get(['uom'])!.value,
      actualDailyConsumption: this.editForm.get(['actualDailyConsumption'])!.value,
      recordLevel: this.editForm.get(['recordLevel'])!.value,
      suggestedQuantity: this.editForm.get(['suggestedQuantity'])!.value,
      expectedCoveringDay: this.editForm.get(['expectedCoveringDay'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      location: this.editForm.get(['location'])!.value,
      lasterUpdated: this.editForm.get(['lasterUpdated'])!.value,
      outlet: this.editForm.get(['outlet'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventory>>): void {
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
