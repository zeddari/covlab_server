import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { PoStatusUpdateComponent } from 'app/entities/po-status/po-status-update.component';
import { PoStatusService } from 'app/entities/po-status/po-status.service';
import { PoStatus } from 'app/shared/model/po-status.model';

describe('Component Tests', () => {
  describe('PoStatus Management Update Component', () => {
    let comp: PoStatusUpdateComponent;
    let fixture: ComponentFixture<PoStatusUpdateComponent>;
    let service: PoStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [PoStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PoStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PoStatus(123);
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
        const entity = new PoStatus();
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
