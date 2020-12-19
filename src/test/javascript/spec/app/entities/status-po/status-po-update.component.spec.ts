import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { StatusPOUpdateComponent } from 'app/entities/status-po/status-po-update.component';
import { StatusPOService } from 'app/entities/status-po/status-po.service';
import { StatusPO } from 'app/shared/model/status-po.model';

describe('Component Tests', () => {
  describe('StatusPO Management Update Component', () => {
    let comp: StatusPOUpdateComponent;
    let fixture: ComponentFixture<StatusPOUpdateComponent>;
    let service: StatusPOService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [StatusPOUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StatusPOUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatusPOUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatusPOService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StatusPO(123);
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
        const entity = new StatusPO();
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
