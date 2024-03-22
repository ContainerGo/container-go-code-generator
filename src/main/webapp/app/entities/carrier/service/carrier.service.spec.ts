import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICarrier } from '../carrier.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../carrier.test-samples';

import { CarrierService, RestCarrier } from './carrier.service';

const requireRestSample: RestCarrier = {
  ...sampleWithRequiredData,
  verifiedSince: sampleWithRequiredData.verifiedSince?.toJSON(),
};

describe('Carrier Service', () => {
  let service: CarrierService;
  let httpMock: HttpTestingController;
  let expectedResult: ICarrier | ICarrier[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarrierService);
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

    it('should create a Carrier', () => {
      const carrier = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(carrier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Carrier', () => {
      const carrier = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(carrier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Carrier', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Carrier', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Carrier', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCarrierToCollectionIfMissing', () => {
      it('should add a Carrier to an empty array', () => {
        const carrier: ICarrier = sampleWithRequiredData;
        expectedResult = service.addCarrierToCollectionIfMissing([], carrier);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrier);
      });

      it('should not add a Carrier to an array that contains it', () => {
        const carrier: ICarrier = sampleWithRequiredData;
        const carrierCollection: ICarrier[] = [
          {
            ...carrier,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCarrierToCollectionIfMissing(carrierCollection, carrier);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Carrier to an array that doesn't contain it", () => {
        const carrier: ICarrier = sampleWithRequiredData;
        const carrierCollection: ICarrier[] = [sampleWithPartialData];
        expectedResult = service.addCarrierToCollectionIfMissing(carrierCollection, carrier);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrier);
      });

      it('should add only unique Carrier to an array', () => {
        const carrierArray: ICarrier[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const carrierCollection: ICarrier[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierToCollectionIfMissing(carrierCollection, ...carrierArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carrier: ICarrier = sampleWithRequiredData;
        const carrier2: ICarrier = sampleWithPartialData;
        expectedResult = service.addCarrierToCollectionIfMissing([], carrier, carrier2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrier);
        expect(expectedResult).toContain(carrier2);
      });

      it('should accept null and undefined values', () => {
        const carrier: ICarrier = sampleWithRequiredData;
        expectedResult = service.addCarrierToCollectionIfMissing([], null, carrier, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrier);
      });

      it('should return initial array if no Carrier is added', () => {
        const carrierCollection: ICarrier[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierToCollectionIfMissing(carrierCollection, undefined, null);
        expect(expectedResult).toEqual(carrierCollection);
      });
    });

    describe('compareCarrier', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCarrier(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCarrier(entity1, entity2);
        const compareResult2 = service.compareCarrier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCarrier(entity1, entity2);
        const compareResult2 = service.compareCarrier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCarrier(entity1, entity2);
        const compareResult2 = service.compareCarrier(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
