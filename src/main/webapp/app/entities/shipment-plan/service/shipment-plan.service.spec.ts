import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShipmentPlan } from '../shipment-plan.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../shipment-plan.test-samples';

import { ShipmentPlanService, RestShipmentPlan } from './shipment-plan.service';

const requireRestSample: RestShipmentPlan = {
  ...sampleWithRequiredData,
  estimatedPickupFromDate: sampleWithRequiredData.estimatedPickupFromDate?.toJSON(),
  estimatedPickupUntilDate: sampleWithRequiredData.estimatedPickupUntilDate?.toJSON(),
  estimatedDropoffFromDate: sampleWithRequiredData.estimatedDropoffFromDate?.toJSON(),
  estimatedDropoffUntilDate: sampleWithRequiredData.estimatedDropoffUntilDate?.toJSON(),
};

describe('ShipmentPlan Service', () => {
  let service: ShipmentPlanService;
  let httpMock: HttpTestingController;
  let expectedResult: IShipmentPlan | IShipmentPlan[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipmentPlanService);
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

    it('should create a ShipmentPlan', () => {
      const shipmentPlan = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shipmentPlan).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShipmentPlan', () => {
      const shipmentPlan = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shipmentPlan).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShipmentPlan', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShipmentPlan', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ShipmentPlan', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShipmentPlanToCollectionIfMissing', () => {
      it('should add a ShipmentPlan to an empty array', () => {
        const shipmentPlan: IShipmentPlan = sampleWithRequiredData;
        expectedResult = service.addShipmentPlanToCollectionIfMissing([], shipmentPlan);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipmentPlan);
      });

      it('should not add a ShipmentPlan to an array that contains it', () => {
        const shipmentPlan: IShipmentPlan = sampleWithRequiredData;
        const shipmentPlanCollection: IShipmentPlan[] = [
          {
            ...shipmentPlan,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShipmentPlanToCollectionIfMissing(shipmentPlanCollection, shipmentPlan);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShipmentPlan to an array that doesn't contain it", () => {
        const shipmentPlan: IShipmentPlan = sampleWithRequiredData;
        const shipmentPlanCollection: IShipmentPlan[] = [sampleWithPartialData];
        expectedResult = service.addShipmentPlanToCollectionIfMissing(shipmentPlanCollection, shipmentPlan);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipmentPlan);
      });

      it('should add only unique ShipmentPlan to an array', () => {
        const shipmentPlanArray: IShipmentPlan[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shipmentPlanCollection: IShipmentPlan[] = [sampleWithRequiredData];
        expectedResult = service.addShipmentPlanToCollectionIfMissing(shipmentPlanCollection, ...shipmentPlanArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipmentPlan: IShipmentPlan = sampleWithRequiredData;
        const shipmentPlan2: IShipmentPlan = sampleWithPartialData;
        expectedResult = service.addShipmentPlanToCollectionIfMissing([], shipmentPlan, shipmentPlan2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipmentPlan);
        expect(expectedResult).toContain(shipmentPlan2);
      });

      it('should accept null and undefined values', () => {
        const shipmentPlan: IShipmentPlan = sampleWithRequiredData;
        expectedResult = service.addShipmentPlanToCollectionIfMissing([], null, shipmentPlan, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipmentPlan);
      });

      it('should return initial array if no ShipmentPlan is added', () => {
        const shipmentPlanCollection: IShipmentPlan[] = [sampleWithRequiredData];
        expectedResult = service.addShipmentPlanToCollectionIfMissing(shipmentPlanCollection, undefined, null);
        expect(expectedResult).toEqual(shipmentPlanCollection);
      });
    });

    describe('compareShipmentPlan', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShipmentPlan(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = null;

        const compareResult1 = service.compareShipmentPlan(entity1, entity2);
        const compareResult2 = service.compareShipmentPlan(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

        const compareResult1 = service.compareShipmentPlan(entity1, entity2);
        const compareResult2 = service.compareShipmentPlan(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

        const compareResult1 = service.compareShipmentPlan(entity1, entity2);
        const compareResult2 = service.compareShipmentPlan(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
