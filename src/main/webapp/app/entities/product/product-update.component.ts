import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { IDeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';
import { DeviceOverviewStatsService } from 'app/entities/device-overview-stats/device-overview-stats.service';

type SelectableEntity = ICategory | IDeviceOverviewStats;

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];
  deviceoverviewstats: IDeviceOverviewStats[] = [];

  editForm = this.fb.group({
    id: [],
    productId: [],
    description: [],
    productCode: [],
    category: [],
    deviceOverviewStats: [],
  });

  constructor(
    protected productService: ProductService,
    protected categoryService: CategoryService,
    protected deviceOverviewStatsService: DeviceOverviewStatsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));

      this.deviceOverviewStatsService
        .query()
        .subscribe((res: HttpResponse<IDeviceOverviewStats[]>) => (this.deviceoverviewstats = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      productId: product.productId,
      description: product.description,
      productCode: product.productCode,
      category: product.category,
      deviceOverviewStats: product.deviceOverviewStats,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      description: this.editForm.get(['description'])!.value,
      productCode: this.editForm.get(['productCode'])!.value,
      category: this.editForm.get(['category'])!.value,
      deviceOverviewStats: this.editForm.get(['deviceOverviewStats'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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
