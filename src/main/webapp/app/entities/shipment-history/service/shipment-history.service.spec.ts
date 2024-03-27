import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShipmentHistory } from '../shipment-history.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../shipment-history.test-samples';

import { ShipmentHistoryService, RestShipmentHistory } from './shipment-history.service';

const requireRestSample: RestShipmentHistory = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('ShipmentHistory Service', () => {
  let service: ShipmentHistoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IShipmentHistory | IShipmentHistory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipmentHistoryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ShipmentHistory', () => {
      const shipmentHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shipmentHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShipmentHistory', () => {
      const shipmentHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shipmentHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShipmentHistory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShipmentHistory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ShipmentHistory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShipmentHistoryToCollectionIfMissing', () => {
      it('should add a ShipmentHistory to an empty array', () => {
        const shipmentHistory: IShipmentHistory = sampleWithRequiredData;
        expectedResult = service.addShipmentHistoryToCollectionIfMissing([], shipmentHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipmentHistory);
      });

      it('should not add a ShipmentHistory to an array that contains it', () => {
        const shipmentHistory: IShipmentHistory = sampleWithRequiredData;
        const shipmentHistoryCollection: IShipmentHistory[] = [
          {
            ...shipmentHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShipmentHistoryToCollectionIfMissing(shipmentHistoryCollection, shipmentHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShipmentHistory to an array that doesn't contain it", () => {
        const shipmentHistory: IShipmentHistory = sampleWithRequiredData;
        const shipmentHistoryCollection: IShipmentHistory[] = [sampleWithPartialData];
        expectedResult = service.addShipmentHistoryToCollectionIfMissing(shipmentHistoryCollection, shipmentHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipmentHistory);
      });

      it('should add only unique ShipmentHistory to an array', () => {
        const shipmentHistoryArray: IShipmentHistory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shipmentHistoryCollection: IShipmentHistory[] = [sampleWithRequiredData];
        expectedResult = service.addShipmentHistoryToCollectionIfMissing(shipmentHistoryCollection, ...shipmentHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipmentHistory: IShipmentHistory = sampleWithRequiredData;
        const shipmentHistory2: IShipmentHistory = sampleWithPartialData;
        expectedResult = service.addShipmentHistoryToCollectionIfMissing([], shipmentHistory, shipmentHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipmentHistory);
        expect(expectedResult).toContain(shipmentHistory2);
      });

      it('should accept null and undefined values', () => {
        const shipmentHistory: IShipmentHistory = sampleWithRequiredData;
        expectedResult = service.addShipmentHistoryToCollectionIfMissing([], null, shipmentHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipmentHistory);
      });

      it('should return initial array if no ShipmentHistory is added', () => {
        const shipmentHistoryCollection: IShipmentHistory[] = [sampleWithRequiredData];
        expectedResult = service.addShipmentHistoryToCollectionIfMissing(shipmentHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(shipmentHistoryCollection);
      });
    });

    describe('compareShipmentHistory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShipmentHistory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareShipmentHistory(entity1, entity2);
        const compareResult2 = service.compareShipmentHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareShipmentHistory(entity1, entity2);
        const compareResult2 = service.compareShipmentHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareShipmentHistory(entity1, entity2);
        const compareResult2 = service.compareShipmentHistory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
