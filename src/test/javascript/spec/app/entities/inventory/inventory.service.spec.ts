import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InventoryService } from 'app/entities/inventory/inventory.service';
import { IInventory, Inventory } from 'app/shared/model/inventory.model';

describe('Service Tests', () => {
  describe('Inventory Service', () => {
    let injector: TestBed;
    let service: InventoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IInventory;
    let expectedResult: IInventory | IInventory[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InventoryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Inventory(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lasterUpdated: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Inventory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lasterUpdated: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lasterUpdated: currentDate,
          },
          returnedFromService
        );

        service.create(new Inventory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Inventory', () => {
        const returnedFromService = Object.assign(
          {
            inventoryId: 'BBBBBB',
            itemCode: 'BBBBBB',
            description: 'BBBBBB',
            quantitiesInHand: 'BBBBBB',
            quantitiesInTransit: 'BBBBBB',
            uom: 'BBBBBB',
            actualDailyConsumption: 'BBBBBB',
            recordLevel: 'BBBBBB',
            suggestedQuantity: 'BBBBBB',
            expectedCoveringDay: 'BBBBBB',
            quantity: 'BBBBBB',
            location: 'BBBBBB',
            lasterUpdated: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lasterUpdated: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Inventory', () => {
        const returnedFromService = Object.assign(
          {
            inventoryId: 'BBBBBB',
            itemCode: 'BBBBBB',
            description: 'BBBBBB',
            quantitiesInHand: 'BBBBBB',
            quantitiesInTransit: 'BBBBBB',
            uom: 'BBBBBB',
            actualDailyConsumption: 'BBBBBB',
            recordLevel: 'BBBBBB',
            suggestedQuantity: 'BBBBBB',
            expectedCoveringDay: 'BBBBBB',
            quantity: 'BBBBBB',
            location: 'BBBBBB',
            lasterUpdated: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lasterUpdated: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Inventory', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
