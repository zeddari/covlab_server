import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { DeviceOverviewStatsDetailComponent } from 'app/entities/device-overview-stats/device-overview-stats-detail.component';
import { DeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';

describe('Component Tests', () => {
  describe('DeviceOverviewStats Management Detail Component', () => {
    let comp: DeviceOverviewStatsDetailComponent;
    let fixture: ComponentFixture<DeviceOverviewStatsDetailComponent>;
    const route = ({ data: of({ deviceOverviewStats: new DeviceOverviewStats(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [DeviceOverviewStatsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DeviceOverviewStatsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeviceOverviewStatsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deviceOverviewStats on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deviceOverviewStats).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
