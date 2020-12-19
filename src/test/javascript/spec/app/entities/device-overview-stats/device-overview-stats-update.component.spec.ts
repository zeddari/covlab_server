import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { DeviceOverviewStatsUpdateComponent } from 'app/entities/device-overview-stats/device-overview-stats-update.component';
import { DeviceOverviewStatsService } from 'app/entities/device-overview-stats/device-overview-stats.service';
import { DeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';

describe('Component Tests', () => {
  describe('DeviceOverviewStats Management Update Component', () => {
    let comp: DeviceOverviewStatsUpdateComponent;
    let fixture: ComponentFixture<DeviceOverviewStatsUpdateComponent>;
    let service: DeviceOverviewStatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [DeviceOverviewStatsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DeviceOverviewStatsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeviceOverviewStatsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeviceOverviewStatsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceOverviewStats(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeviceOverviewStats();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
