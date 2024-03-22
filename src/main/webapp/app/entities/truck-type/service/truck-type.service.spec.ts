import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITruckType } from '../truck-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../truck-type.test-samples';

import { TruckTypeService } from './truck-type.service';

const requireRestSample: ITruckType = {
  ...sampleWithRequiredData,
};

describe('TruckType Service', () => {
  let service: TruckTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ITruckType | ITruckType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TruckTypeService);
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

    it('should create a TruckType', () => {
      const truckType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(truckType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TruckType', () => {
      const truckType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(truckType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TruckType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TruckType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TruckType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTruckTypeToCollectionIfMissing', () => {
      it('should add a TruckType to an empty array', () => {
        const truckType: ITruckType = sampleWithRequiredData;
        expectedResult = service.addTruckTypeToCollectionIfMissing([], truckType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(truckType);
      });

      it('should not add a TruckType to an array that contains it', () => {
        const truckType: ITruckType = sampleWithRequiredData;
        const truckTypeCollection: ITruckType[] = [
          {
            ...truckType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTruckTypeToCollectionIfMissing(truckTypeCollection, truckType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TruckType to an array that doesn't contain it", () => {
        const truckType: ITruckType = sampleWithRequiredData;
        const truckTypeCollection: ITruckType[] = [sampleWithPartialData];
        expectedResult = service.addTruckTypeToCollectionIfMissing(truckTypeCollection, truckType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(truckType);
      });

      it('should add only unique TruckType to an array', () => {
        const truckTypeArray: ITruckType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const truckTypeCollection: ITruckType[] = [sampleWithRequiredData];
        expectedResult = service.addTruckTypeToCollectionIfMissing(truckTypeCollection, ...truckTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const truckType: ITruckType = sampleWithRequiredData;
        const truckType2: ITruckType = sampleWithPartialData;
        expectedResult = service.addTruckTypeToCollectionIfMissing([], truckType, truckType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(truckType);
        expect(expectedResult).toContain(truckType2);
      });

      it('should accept null and undefined values', () => {
        const truckType: ITruckType = sampleWithRequiredData;
        expectedResult = service.addTruckTypeToCollectionIfMissing([], null, truckType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(truckType);
      });

      it('should return initial array if no TruckType is added', () => {
        const truckTypeCollection: ITruckType[] = [sampleWithRequiredData];
        expectedResult = service.addTruckTypeToCollectionIfMissing(truckTypeCollection, undefined, null);
        expect(expectedResult).toEqual(truckTypeCollection);
      });
    });

    describe('compareTruckType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTruckType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTruckType(entity1, entity2);
        const compareResult2 = service.compareTruckType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTruckType(entity1, entity2);
        const compareResult2 = service.compareTruckType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTruckType(entity1, entity2);
        const compareResult2 = service.compareTruckType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
