import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContainerType } from '../container-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../container-type.test-samples';

import { ContainerTypeService } from './container-type.service';

const requireRestSample: IContainerType = {
  ...sampleWithRequiredData,
};

describe('ContainerType Service', () => {
  let service: ContainerTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IContainerType | IContainerType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContainerTypeService);
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

    it('should create a ContainerType', () => {
      const containerType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(containerType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContainerType', () => {
      const containerType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(containerType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContainerType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContainerType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContainerType', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContainerTypeToCollectionIfMissing', () => {
      it('should add a ContainerType to an empty array', () => {
        const containerType: IContainerType = sampleWithRequiredData;
        expectedResult = service.addContainerTypeToCollectionIfMissing([], containerType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerType);
      });

      it('should not add a ContainerType to an array that contains it', () => {
        const containerType: IContainerType = sampleWithRequiredData;
        const containerTypeCollection: IContainerType[] = [
          {
            ...containerType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContainerTypeToCollectionIfMissing(containerTypeCollection, containerType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContainerType to an array that doesn't contain it", () => {
        const containerType: IContainerType = sampleWithRequiredData;
        const containerTypeCollection: IContainerType[] = [sampleWithPartialData];
        expectedResult = service.addContainerTypeToCollectionIfMissing(containerTypeCollection, containerType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerType);
      });

      it('should add only unique ContainerType to an array', () => {
        const containerTypeArray: IContainerType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const containerTypeCollection: IContainerType[] = [sampleWithRequiredData];
        expectedResult = service.addContainerTypeToCollectionIfMissing(containerTypeCollection, ...containerTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const containerType: IContainerType = sampleWithRequiredData;
        const containerType2: IContainerType = sampleWithPartialData;
        expectedResult = service.addContainerTypeToCollectionIfMissing([], containerType, containerType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerType);
        expect(expectedResult).toContain(containerType2);
      });

      it('should accept null and undefined values', () => {
        const containerType: IContainerType = sampleWithRequiredData;
        expectedResult = service.addContainerTypeToCollectionIfMissing([], null, containerType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerType);
      });

      it('should return initial array if no ContainerType is added', () => {
        const containerTypeCollection: IContainerType[] = [sampleWithRequiredData];
        expectedResult = service.addContainerTypeToCollectionIfMissing(containerTypeCollection, undefined, null);
        expect(expectedResult).toEqual(containerTypeCollection);
      });
    });

    describe('compareContainerType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContainerType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareContainerType(entity1, entity2);
        const compareResult2 = service.compareContainerType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareContainerType(entity1, entity2);
        const compareResult2 = service.compareContainerType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareContainerType(entity1, entity2);
        const compareResult2 = service.compareContainerType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
