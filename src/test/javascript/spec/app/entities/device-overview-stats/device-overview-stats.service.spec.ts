import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DeviceOverviewStatsService } from 'app/entities/device-overview-stats/device-overview-stats.service';
import { IDeviceOverviewStats, DeviceOverviewStats } from 'app/shared/model/device-overview-stats.model';

describe('Service Tests', () => {
  describe('DeviceOverviewStats Service', () => {
    let injector: TestBed;
    let service: DeviceOverviewStatsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDeviceOverviewStats;
    let expectedResult: IDeviceOverviewStats | IDeviceOverviewStats[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DeviceOverviewStatsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DeviceOverviewStats(0, 'AAAAAAA', currentDate, 'AAAAAAA', 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            timestamp: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DeviceOverviewStats', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            timestamp: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timestamp: currentDate,
          },
          returnedFromService
        );

        service.create(new DeviceOverviewStats()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DeviceOverviewStats', () => {
        const returnedFromService = Object.assign(
          {
            deviceId: 'BBBBBB',
            timestamp: currentDate.format(DATE_FORMAT),
            serialNumber: 'BBBBBB',
            humidity: 1,
            temperature: 1,
            co2: 1,
            pressure: 1,
            differentialPressure: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timestamp: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DeviceOverviewStats', () => {
        const returnedFromService = Object.assign(
          {
            deviceId: 'BBBBBB',
            timestamp: currentDate.format(DATE_FORMAT),
            serialNumber: 'BBBBBB',
            humidity: 1,
            temperature: 1,
            co2: 1,
            pressure: 1,
            differentialPressure: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timestamp: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DeviceOverviewStats', () => {
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
