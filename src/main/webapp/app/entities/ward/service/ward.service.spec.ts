import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWard } from '../ward.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ward.test-samples';

import { WardService } from './ward.service';

const requireRestSample: IWard = {
  ...sampleWithRequiredData,
};

describe('Ward Service', () => {
  let service: WardService;
  let httpMock: HttpTestingController;
  let expectedResult: IWard | IWard[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WardService);
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

    it('should create a Ward', () => {
      const ward = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ward).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Ward', () => {
      const ward = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ward).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Ward', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Ward', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Ward', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWardToCollectionIfMissing', () => {
      it('should add a Ward to an empty array', () => {
        const ward: IWard = sampleWithRequiredData;
        expectedResult = service.addWardToCollectionIfMissing([], ward);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ward);
      });

      it('should not add a Ward to an array that contains it', () => {
        const ward: IWard = sampleWithRequiredData;
        const wardCollection: IWard[] = [
          {
            ...ward,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWardToCollectionIfMissing(wardCollection, ward);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Ward to an array that doesn't contain it", () => {
        const ward: IWard = sampleWithRequiredData;
        const wardCollection: IWard[] = [sampleWithPartialData];
        expectedResult = service.addWardToCollectionIfMissing(wardCollection, ward);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ward);
      });

      it('should add only unique Ward to an array', () => {
        const wardArray: IWard[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const wardCollection: IWard[] = [sampleWithRequiredData];
        expectedResult = service.addWardToCollectionIfMissing(wardCollection, ...wardArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ward: IWard = sampleWithRequiredData;
        const ward2: IWard = sampleWithPartialData;
        expectedResult = service.addWardToCollectionIfMissing([], ward, ward2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ward);
        expect(expectedResult).toContain(ward2);
      });

      it('should accept null and undefined values', () => {
        const ward: IWard = sampleWithRequiredData;
        expectedResult = service.addWardToCollectionIfMissing([], null, ward, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ward);
      });

      it('should return initial array if no Ward is added', () => {
        const wardCollection: IWard[] = [sampleWithRequiredData];
        expectedResult = service.addWardToCollectionIfMissing(wardCollection, undefined, null);
        expect(expectedResult).toEqual(wardCollection);
      });
    });

    describe('compareWard', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWard(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareWard(entity1, entity2);
        const compareResult2 = service.compareWard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareWard(entity1, entity2);
        const compareResult2 = service.compareWard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareWard(entity1, entity2);
        const compareResult2 = service.compareWard(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
