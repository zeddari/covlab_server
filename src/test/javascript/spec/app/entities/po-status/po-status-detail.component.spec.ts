import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { PoStatusDetailComponent } from 'app/entities/po-status/po-status-detail.component';
import { PoStatus } from 'app/shared/model/po-status.model';

describe('Component Tests', () => {
  describe('PoStatus Management Detail Component', () => {
    let comp: PoStatusDetailComponent;
    let fixture: ComponentFixture<PoStatusDetailComponent>;
    const route = ({ data: of({ poStatus: new PoStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [PoStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PoStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load poStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
