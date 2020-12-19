import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CovlabServerTestModule } from '../../../test.module';
import { PurchaseOrderUpdateComponent } from 'app/entities/purchase-order/purchase-order-update.component';
import { PurchaseOrderService } from 'app/entities/purchase-order/purchase-order.service';
import { PurchaseOrder } from 'app/shared/model/purchase-order.model';

describe('Component Tests', () => {
  describe('PurchaseOrder Management Update Component', () => {
    let comp: PurchaseOrderUpdateComponent;
    let fixture: ComponentFixture<PurchaseOrderUpdateComponent>;
    let service: PurchaseOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CovlabServerTestModule],
        declarations: [PurchaseOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PurchaseOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PurchaseOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PurchaseOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PurchaseOrder(123);
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
        const entity = new PurchaseOrder();
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
