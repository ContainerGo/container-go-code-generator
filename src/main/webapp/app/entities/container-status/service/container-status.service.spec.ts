import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContainerStatus } from '../container-status.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../container-status.test-samples';

import { ContainerStatusService } from './container-status.service';

const requireRestSample: IContainerStatus = {
  ...sampleWithRequiredData,
};

describe('ContainerStatus Service', () => {
  let service: ContainerStatusService;
  let httpMock: HttpTestingController;
  let expectedResult: IContainerStatus | IContainerStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContainerStatusService);
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

    it('should create a ContainerStatus', () => {
      const containerStatus = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(containerStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContainerStatus', () => {
      const containerStatus = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(containerStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContainerStatus', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContainerStatus', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContainerStatus', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContainerStatusToCollectionIfMissing', () => {
      it('should add a ContainerStatus to an empty array', () => {
        const containerStatus: IContainerStatus = sampleWithRequiredData;
        expectedResult = service.addContainerStatusToCollectionIfMissing([], containerStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerStatus);
      });

      it('should not add a ContainerStatus to an array that contains it', () => {
        const containerStatus: IContainerStatus = sampleWithRequiredData;
        const containerStatusCollection: IContainerStatus[] = [
          {
            ...containerStatus,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContainerStatusToCollectionIfMissing(containerStatusCollection, containerStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContainerStatus to an array that doesn't contain it", () => {
        const containerStatus: IContainerStatus = sampleWithRequiredData;
        const containerStatusCollection: IContainerStatus[] = [sampleWithPartialData];
        expectedResult = service.addContainerStatusToCollectionIfMissing(containerStatusCollection, containerStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerStatus);
      });

      it('should add only unique ContainerStatus to an array', () => {
        const containerStatusArray: IContainerStatus[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const containerStatusCollection: IContainerStatus[] = [sampleWithRequiredData];
        expectedResult = service.addContainerStatusToCollectionIfMissing(containerStatusCollection, ...containerStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const containerStatus: IContainerStatus = sampleWithRequiredData;
        const containerStatus2: IContainerStatus = sampleWithPartialData;
        expectedResult = service.addContainerStatusToCollectionIfMissing([], containerStatus, containerStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(containerStatus);
        expect(expectedResult).toContain(containerStatus2);
      });

      it('should accept null and undefined values', () => {
        const containerStatus: IContainerStatus = sampleWithRequiredData;
        expectedResult = service.addContainerStatusToCollectionIfMissing([], null, containerStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(containerStatus);
      });

      it('should return initial array if no ContainerStatus is added', () => {
        const containerStatusCollection: IContainerStatus[] = [sampleWithRequiredData];
        expectedResult = service.addContainerStatusToCollectionIfMissing(containerStatusCollection, undefined, null);
        expect(expectedResult).toEqual(containerStatusCollection);
      });
    });

    describe('compareContainerStatus', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContainerStatus(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareContainerStatus(entity1, entity2);
        const compareResult2 = service.compareContainerStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareContainerStatus(entity1, entity2);
        const compareResult2 = service.compareContainerStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareContainerStatus(entity1, entity2);
        const compareResult2 = service.compareContainerStatus(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
