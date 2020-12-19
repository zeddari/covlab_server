import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeviceOverviewStats, DeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';
import { DeviceOverviewStatsService } from './device-overview-stats.service';
import { IOutlet } from 'app/shared/model/outlet.model';
import { OutletService } from 'app/entities/outlet/outlet.service';

@Component({
  selector: 'jhi-device-overview-stats-update',
  templateUrl: './device-overview-stats-update.component.html',
})
export class DeviceOverviewStatsUpdateComponent implements OnInit {
  isSaving = false;
  outlets: IOutlet[] = [];
  timestampDp: any;

  editForm = this.fb.group({
    id: [],
    deviceId: [],
    timestamp: [],
    serialNumber: [],
    humidity: [],
    temperature: [],
    co2: [],
    pressure: [],
    differentialPressure: [],
    outlet: [],
  });

  constructor(
    protected deviceOverviewStatsService: DeviceOverviewStatsService,
    protected outletService: OutletService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deviceOverviewStats }) => {
      this.updateForm(deviceOverviewStats);

      this.outletService.query().subscribe((res: HttpResponse<IOutlet[]>) => (this.outlets = res.body || []));
    });
  }

  updateForm(deviceOverviewStats: IDeviceOverviewStats): void {
    this.editForm.patchValue({
      id: deviceOverviewStats.id,
      deviceId: deviceOverviewStats.deviceId,
      timestamp: deviceOverviewStats.timestamp,
      serialNumber: deviceOverviewStats.serialNumber,
      humidity: deviceOverviewStats.humidity,
      temperature: deviceOverviewStats.temperature,
      co2: deviceOverviewStats.co2,
      pressure: deviceOverviewStats.pressure,
      differentialPressure: deviceOverviewStats.differentialPressure,
      outlet: deviceOverviewStats.outlet,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deviceOverviewStats = this.createFromForm();
    if (deviceOverviewStats.id !== undefined) {
      this.subscribeToSaveResponse(this.deviceOverviewStatsService.update(deviceOverviewStats));
    } else {
      this.subscribeToSaveResponse(this.deviceOverviewStatsService.create(deviceOverviewStats));
    }
  }

  private createFromForm(): IDeviceOverviewStats {
    return {
      ...new DeviceOverviewStats(),
      id: this.editForm.get(['id'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
      timestamp: this.editForm.get(['timestamp'])!.value,
      serialNumber: this.editForm.get(['serialNumber'])!.value,
      humidity: this.editForm.get(['humidity'])!.value,
      temperature: this.editForm.get(['temperature'])!.value,
      co2: this.editForm.get(['co2'])!.value,
      pressure: this.editForm.get(['pressure'])!.value,
      differentialPressure: this.editForm.get(['differentialPressure'])!.value,
      outlet: this.editForm.get(['outlet'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeviceOverviewStats>>): void {
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

  trackById(index: number, item: IOutlet): any {
    return item.id;
  }
}
