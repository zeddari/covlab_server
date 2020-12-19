import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { StatusPODetailComponent } from 'app/entities/status-po/status-po-detail.component';
import { StatusPO } from 'app/shared/model/status-po.model';

describe('Component Tests', () => {
  describe('StatusPO Management Detail Component', () => {
    let comp: StatusPODetailComponent;
    let fixture: ComponentFixture<StatusPODetailComponent>;
    const route = ({ data: of({ statusPO: new StatusPO(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [StatusPODetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StatusPODetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatusPODetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load statusPO on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statusPO).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
