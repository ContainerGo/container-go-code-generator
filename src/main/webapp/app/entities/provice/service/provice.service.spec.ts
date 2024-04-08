import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProvice } from '../provice.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../provice.test-samples';

import { ProviceService } from './provice.service';

const requireRestSample: IProvice = {
  ...sampleWithRequiredData,
};

describe('Provice Service', () => {
  let service: ProviceService;
  let httpMock: HttpTestingController;
  let expectedResult: IProvice | IProvice[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProviceService);
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

    it('should create a Provice', () => {
      const provice = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(provice).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Provice', () => {
      const provice = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(provice).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Provice', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Provice', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Provice', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProviceToCollectionIfMissing', () => {
      it('should add a Provice to an empty array', () => {
        const provice: IProvice = sampleWithRequiredData;
        expectedResult = service.addProviceToCollectionIfMissing([], provice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provice);
      });

      it('should not add a Provice to an array that contains it', () => {
        const provice: IProvice = sampleWithRequiredData;
        const proviceCollection: IProvice[] = [
          {
            ...provice,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProviceToCollectionIfMissing(proviceCollection, provice);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Provice to an array that doesn't contain it", () => {
        const provice: IProvice = sampleWithRequiredData;
        const proviceCollection: IProvice[] = [sampleWithPartialData];
        expectedResult = service.addProviceToCollectionIfMissing(proviceCollection, provice);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provice);
      });

      it('should add only unique Provice to an array', () => {
        const proviceArray: IProvice[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const proviceCollection: IProvice[] = [sampleWithRequiredData];
        expectedResult = service.addProviceToCollectionIfMissing(proviceCollection, ...proviceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const provice: IProvice = sampleWithRequiredData;
        const provice2: IProvice = sampleWithPartialData;
        expectedResult = service.addProviceToCollectionIfMissing([], provice, provice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(provice);
        expect(expectedResult).toContain(provice2);
      });

      it('should accept null and undefined values', () => {
        const provice: IProvice = sampleWithRequiredData;
        expectedResult = service.addProviceToCollectionIfMissing([], null, provice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(provice);
      });

      it('should return initial array if no Provice is added', () => {
        const proviceCollection: IProvice[] = [sampleWithRequiredData];
        expectedResult = service.addProviceToCollectionIfMissing(proviceCollection, undefined, null);
        expect(expectedResult).toEqual(proviceCollection);
      });
    });

    describe('compareProvice', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProvice(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareProvice(entity1, entity2);
        const compareResult2 = service.compareProvice(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareProvice(entity1, entity2);
        const compareResult2 = service.compareProvice(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareProvice(entity1, entity2);
        const compareResult2 = service.compareProvice(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
