import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { OutletUpdateComponent } from 'app/entities/outlet/outlet-update.component';
import { OutletService } from 'app/entities/outlet/outlet.service';
import { Outlet } from 'app/shared/model/outlet.model';

describe('Component Tests', () => {
  describe('Outlet Management Update Component', () => {
    let comp: OutletUpdateComponent;
    let fixture: ComponentFixture<OutletUpdateComponent>;
    let service: OutletService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [OutletUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OutletUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutletUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutletService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outlet(123);
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
        const entity = new Outlet();
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
