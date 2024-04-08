import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICenterPerson } from '../center-person.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../center-person.test-samples';

import { CenterPersonService } from './center-person.service';

const requireRestSample: ICenterPerson = {
  ...sampleWithRequiredData,
};

describe('CenterPerson Service', () => {
  let service: CenterPersonService;
  let httpMock: HttpTestingController;
  let expectedResult: ICenterPerson | ICenterPerson[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CenterPersonService);
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

    it('should create a CenterPerson', () => {
      const centerPerson = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(centerPerson).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CenterPerson', () => {
      const centerPerson = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(centerPerson).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CenterPerson', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CenterPerson', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CenterPerson', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCenterPersonToCollectionIfMissing', () => {
      it('should add a CenterPerson to an empty array', () => {
        const centerPerson: ICenterPerson = sampleWithRequiredData;
        expectedResult = service.addCenterPersonToCollectionIfMissing([], centerPerson);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centerPerson);
      });

      it('should not add a CenterPerson to an array that contains it', () => {
        const centerPerson: ICenterPerson = sampleWithRequiredData;
        const centerPersonCollection: ICenterPerson[] = [
          {
            ...centerPerson,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCenterPersonToCollectionIfMissing(centerPersonCollection, centerPerson);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CenterPerson to an array that doesn't contain it", () => {
        const centerPerson: ICenterPerson = sampleWithRequiredData;
        const centerPersonCollection: ICenterPerson[] = [sampleWithPartialData];
        expectedResult = service.addCenterPersonToCollectionIfMissing(centerPersonCollection, centerPerson);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centerPerson);
      });

      it('should add only unique CenterPerson to an array', () => {
        const centerPersonArray: ICenterPerson[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const centerPersonCollection: ICenterPerson[] = [sampleWithRequiredData];
        expectedResult = service.addCenterPersonToCollectionIfMissing(centerPersonCollection, ...centerPersonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const centerPerson: ICenterPerson = sampleWithRequiredData;
        const centerPerson2: ICenterPerson = sampleWithPartialData;
        expectedResult = service.addCenterPersonToCollectionIfMissing([], centerPerson, centerPerson2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centerPerson);
        expect(expectedResult).toContain(centerPerson2);
      });

      it('should accept null and undefined values', () => {
        const centerPerson: ICenterPerson = sampleWithRequiredData;
        expectedResult = service.addCenterPersonToCollectionIfMissing([], null, centerPerson, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centerPerson);
      });

      it('should return initial array if no CenterPerson is added', () => {
        const centerPersonCollection: ICenterPerson[] = [sampleWithRequiredData];
        expectedResult = service.addCenterPersonToCollectionIfMissing(centerPersonCollection, undefined, null);
        expect(expectedResult).toEqual(centerPersonCollection);
      });
    });

    describe('compareCenterPerson', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCenterPerson(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareCenterPerson(entity1, entity2);
        const compareResult2 = service.compareCenterPerson(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareCenterPerson(entity1, entity2);
        const compareResult2 = service.compareCenterPerson(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareCenterPerson(entity1, entity2);
        const compareResult2 = service.compareCenterPerson(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
