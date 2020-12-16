import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOutlet, Outlet } from 'app/shared/model/outlet.model';
import { OutletService } from './outlet.service';

@Component({
  selector: 'jhi-outlet-update',
  templateUrl: './outlet-update.component.html',
})
export class OutletUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    outletId: [],
    outletName: [],
    outletRegion: [],
    outletAdress: [],
    outletLat: [],
    outletLng: [],
  });

  constructor(protected outletService: OutletService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outlet }) => {
      this.updateForm(outlet);
    });
  }

  updateForm(outlet: IOutlet): void {
    this.editForm.patchValue({
      id: outlet.id,
      outletId: outlet.outletId,
      outletName: outlet.outletName,
      outletRegion: outlet.outletRegion,
      outletAdress: outlet.outletAdress,
      outletLat: outlet.outletLat,
      outletLng: outlet.outletLng,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const outlet = this.createFromForm();
    if (outlet.id !== undefined) {
      this.subscribeToSaveResponse(this.outletService.update(outlet));
    } else {
      this.subscribeToSaveResponse(this.outletService.create(outlet));
    }
  }

  private createFromForm(): IOutlet {
    return {
      ...new Outlet(),
      id: this.editForm.get(['id'])!.value,
      outletId: this.editForm.get(['outletId'])!.value,
      outletName: this.editForm.get(['outletName'])!.value,
      outletRegion: this.editForm.get(['outletRegion'])!.value,
      outletAdress: this.editForm.get(['outletAdress'])!.value,
      outletLat: this.editForm.get(['outletLat'])!.value,
      outletLng: this.editForm.get(['outletLng'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOutlet>>): void {
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
}
