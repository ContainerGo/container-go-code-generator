import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITruck } from '../truck.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../truck.test-samples';

import { TruckService } from './truck.service';

const requireRestSample: ITruck = {
  ...sampleWithRequiredData,
};

describe('Truck Service', () => {
  let service: TruckService;
  let httpMock: HttpTestingController;
  let expectedResult: ITruck | ITruck[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TruckService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Truck', () => {
      const truck = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(truck).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Truck', () => {
      const truck = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(truck).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Truck', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Truck', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Truck', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTruckToCollectionIfMissing', () => {
      it('should add a Truck to an empty array', () => {
        const truck: ITruck = sampleWithRequiredData;
        expectedResult = service.addTruckToCollectionIfMissing([], truck);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(truck);
      });

      it('should not add a Truck to an array that contains it', () => {
        const truck: ITruck = sampleWithRequiredData;
        const truckCollection: ITruck[] = [
          {
            ...truck,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTruckToCollectionIfMissing(truckCollection, truck);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Truck to an array that doesn't contain it", () => {
        const truck: ITruck = sampleWithRequiredData;
        const truckCollection: ITruck[] = [sampleWithPartialData];
        expectedResult = service.addTruckToCollectionIfMissing(truckCollection, truck);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(truck);
      });

      it('should add only unique Truck to an array', () => {
        const truckArray: ITruck[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const truckCollection: ITruck[] = [sampleWithRequiredData];
        expectedResult = service.addTruckToCollectionIfMissing(truckCollection, ...truckArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const truck: ITruck = sampleWithRequiredData;
        const truck2: ITruck = sampleWithPartialData;
        expectedResult = service.addTruckToCollectionIfMissing([], truck, truck2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(truck);
        expect(expectedResult).toContain(truck2);
      });

      it('should accept null and undefined values', () => {
        const truck: ITruck = sampleWithRequiredData;
        expectedResult = service.addTruckToCollectionIfMissing([], null, truck, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(truck);
      });

      it('should return initial array if no Truck is added', () => {
        const truckCollection: ITruck[] = [sampleWithRequiredData];
        expectedResult = service.addTruckToCollectionIfMissing(truckCollection, undefined, null);
        expect(expectedResult).toEqual(truckCollection);
      });
    });

    describe('compareTruck', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTruck(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareTruck(entity1, entity2);
        const compareResult2 = service.compareTruck(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareTruck(entity1, entity2);
        const compareResult2 = service.compareTruck(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareTruck(entity1, entity2);
        const compareResult2 = service.compareTruck(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
