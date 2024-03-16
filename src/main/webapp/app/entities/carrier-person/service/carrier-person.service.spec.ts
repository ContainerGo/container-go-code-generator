import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICarrierPerson } from '../carrier-person.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../carrier-person.test-samples';

import { CarrierPersonService } from './carrier-person.service';

const requireRestSample: ICarrierPerson = {
  ...sampleWithRequiredData,
};

describe('CarrierPerson Service', () => {
  let service: CarrierPersonService;
  let httpMock: HttpTestingController;
  let expectedResult: ICarrierPerson | ICarrierPerson[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarrierPersonService);
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

    it('should create a CarrierPerson', () => {
      const carrierPerson = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(carrierPerson).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CarrierPerson', () => {
      const carrierPerson = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(carrierPerson).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CarrierPerson', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CarrierPerson', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CarrierPerson', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCarrierPersonToCollectionIfMissing', () => {
      it('should add a CarrierPerson to an empty array', () => {
        const carrierPerson: ICarrierPerson = sampleWithRequiredData;
        expectedResult = service.addCarrierPersonToCollectionIfMissing([], carrierPerson);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrierPerson);
      });

      it('should not add a CarrierPerson to an array that contains it', () => {
        const carrierPerson: ICarrierPerson = sampleWithRequiredData;
        const carrierPersonCollection: ICarrierPerson[] = [
          {
            ...carrierPerson,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCarrierPersonToCollectionIfMissing(carrierPersonCollection, carrierPerson);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CarrierPerson to an array that doesn't contain it", () => {
        const carrierPerson: ICarrierPerson = sampleWithRequiredData;
        const carrierPersonCollection: ICarrierPerson[] = [sampleWithPartialData];
        expectedResult = service.addCarrierPersonToCollectionIfMissing(carrierPersonCollection, carrierPerson);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrierPerson);
      });

      it('should add only unique CarrierPerson to an array', () => {
        const carrierPersonArray: ICarrierPerson[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const carrierPersonCollection: ICarrierPerson[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierPersonToCollectionIfMissing(carrierPersonCollection, ...carrierPersonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carrierPerson: ICarrierPerson = sampleWithRequiredData;
        const carrierPerson2: ICarrierPerson = sampleWithPartialData;
        expectedResult = service.addCarrierPersonToCollectionIfMissing([], carrierPerson, carrierPerson2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrierPerson);
        expect(expectedResult).toContain(carrierPerson2);
      });

      it('should accept null and undefined values', () => {
        const carrierPerson: ICarrierPerson = sampleWithRequiredData;
        expectedResult = service.addCarrierPersonToCollectionIfMissing([], null, carrierPerson, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrierPerson);
      });

      it('should return initial array if no CarrierPerson is added', () => {
        const carrierPersonCollection: ICarrierPerson[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierPersonToCollectionIfMissing(carrierPersonCollection, undefined, null);
        expect(expectedResult).toEqual(carrierPersonCollection);
      });
    });

    describe('compareCarrierPerson', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCarrierPerson(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCarrierPerson(entity1, entity2);
        const compareResult2 = service.compareCarrierPerson(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCarrierPerson(entity1, entity2);
        const compareResult2 = service.compareCarrierPerson(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCarrierPerson(entity1, entity2);
        const compareResult2 = service.compareCarrierPerson(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
