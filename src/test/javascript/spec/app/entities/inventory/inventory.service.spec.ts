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

      elemDefault = new Inventory(0, 0, 0, 0, 'AAAAAAA', 0, 0, 'AAAAAAA', 0, 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastUpdatedAt: currentDate.format(DATE_FORMAT),
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
            lastUpdatedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUpdatedAt: currentDate,
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
            inventoryId: 1,
            quantitiesInHand: 1,
            quantitiesInTransit: 1,
            uom: 'BBBBBB',
            actualDailyConsumption: 1,
            actualAvgConsumption: 1,
            reOrderLevel: 'BBBBBB',
            suggestedQuantity: 1,
            expectedCoveringDay: 1,
            lastUpdatedAt: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUpdatedAt: currentDate,
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
            inventoryId: 1,
            quantitiesInHand: 1,
            quantitiesInTransit: 1,
            uom: 'BBBBBB',
            actualDailyConsumption: 1,
            actualAvgConsumption: 1,
            reOrderLevel: 'BBBBBB',
            suggestedQuantity: 1,
            expectedCoveringDay: 1,
            lastUpdatedAt: currentDate.format(DATE_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUpdatedAt: currentDate,
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
