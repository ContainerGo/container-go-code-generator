import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICarrierAccount } from '../carrier-account.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../carrier-account.test-samples';

import { CarrierAccountService } from './carrier-account.service';

const requireRestSample: ICarrierAccount = {
  ...sampleWithRequiredData,
};

describe('CarrierAccount Service', () => {
  let service: CarrierAccountService;
  let httpMock: HttpTestingController;
  let expectedResult: ICarrierAccount | ICarrierAccount[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CarrierAccountService);
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

    it('should create a CarrierAccount', () => {
      const carrierAccount = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(carrierAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CarrierAccount', () => {
      const carrierAccount = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(carrierAccount).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CarrierAccount', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CarrierAccount', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CarrierAccount', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCarrierAccountToCollectionIfMissing', () => {
      it('should add a CarrierAccount to an empty array', () => {
        const carrierAccount: ICarrierAccount = sampleWithRequiredData;
        expectedResult = service.addCarrierAccountToCollectionIfMissing([], carrierAccount);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrierAccount);
      });

      it('should not add a CarrierAccount to an array that contains it', () => {
        const carrierAccount: ICarrierAccount = sampleWithRequiredData;
        const carrierAccountCollection: ICarrierAccount[] = [
          {
            ...carrierAccount,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCarrierAccountToCollectionIfMissing(carrierAccountCollection, carrierAccount);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CarrierAccount to an array that doesn't contain it", () => {
        const carrierAccount: ICarrierAccount = sampleWithRequiredData;
        const carrierAccountCollection: ICarrierAccount[] = [sampleWithPartialData];
        expectedResult = service.addCarrierAccountToCollectionIfMissing(carrierAccountCollection, carrierAccount);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrierAccount);
      });

      it('should add only unique CarrierAccount to an array', () => {
        const carrierAccountArray: ICarrierAccount[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const carrierAccountCollection: ICarrierAccount[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierAccountToCollectionIfMissing(carrierAccountCollection, ...carrierAccountArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const carrierAccount: ICarrierAccount = sampleWithRequiredData;
        const carrierAccount2: ICarrierAccount = sampleWithPartialData;
        expectedResult = service.addCarrierAccountToCollectionIfMissing([], carrierAccount, carrierAccount2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(carrierAccount);
        expect(expectedResult).toContain(carrierAccount2);
      });

      it('should accept null and undefined values', () => {
        const carrierAccount: ICarrierAccount = sampleWithRequiredData;
        expectedResult = service.addCarrierAccountToCollectionIfMissing([], null, carrierAccount, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(carrierAccount);
      });

      it('should return initial array if no CarrierAccount is added', () => {
        const carrierAccountCollection: ICarrierAccount[] = [sampleWithRequiredData];
        expectedResult = service.addCarrierAccountToCollectionIfMissing(carrierAccountCollection, undefined, null);
        expect(expectedResult).toEqual(carrierAccountCollection);
      });
    });

    describe('compareCarrierAccount', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCarrierAccount(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCarrierAccount(entity1, entity2);
        const compareResult2 = service.compareCarrierAccount(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCarrierAccount(entity1, entity2);
        const compareResult2 = service.compareCarrierAccount(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCarrierAccount(entity1, entity2);
        const compareResult2 = service.compareCarrierAccount(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
