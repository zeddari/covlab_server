import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { OutletDetailComponent } from 'app/entities/outlet/outlet-detail.component';
import { Outlet } from 'app/shared/model/outlet.model';

describe('Component Tests', () => {
  describe('Outlet Management Detail Component', () => {
    let comp: OutletDetailComponent;
    let fixture: ComponentFixture<OutletDetailComponent>;
    const route = ({ data: of({ outlet: new Outlet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [OutletDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OutletDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutletDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load outlet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outlet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
