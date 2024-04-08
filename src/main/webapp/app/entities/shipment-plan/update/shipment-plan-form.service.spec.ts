import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipment-plan.test-samples';

import { ShipmentPlanFormService } from './shipment-plan-form.service';

describe('ShipmentPlan Form Service', () => {
  let service: ShipmentPlanFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipmentPlanFormService);
  });

  describe('Service methods', () => {
    describe('createShipmentPlanFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipmentPlanFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            estimatedPickupFromDate: expect.any(Object),
            estimatedPickupUntilDate: expect.any(Object),
            estimatedDropoffFromDate: expect.any(Object),
            estimatedDropoffUntilDate: expect.any(Object),
            driverId: expect.any(Object),
            truckId: expect.any(Object),
            container: expect.any(Object),
          }),
        );
      });

      it('passing IShipmentPlan should create a new form with FormGroup', () => {
        const formGroup = service.createShipmentPlanFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            estimatedPickupFromDate: expect.any(Object),
            estimatedPickupUntilDate: expect.any(Object),
            estimatedDropoffFromDate: expect.any(Object),
            estimatedDropoffUntilDate: expect.any(Object),
            driverId: expect.any(Object),
            truckId: expect.any(Object),
            container: expect.any(Object),
          }),
        );
      });
    });

    describe('getShipmentPlan', () => {
      it('should return NewShipmentPlan for default ShipmentPlan initial value', () => {
        const formGroup = service.createShipmentPlanFormGroup(sampleWithNewData);

        const shipmentPlan = service.getShipmentPlan(formGroup) as any;

        expect(shipmentPlan).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipmentPlan for empty ShipmentPlan initial value', () => {
        const formGroup = service.createShipmentPlanFormGroup();

        const shipmentPlan = service.getShipmentPlan(formGroup) as any;

        expect(shipmentPlan).toMatchObject({});
      });

      it('should return IShipmentPlan', () => {
        const formGroup = service.createShipmentPlanFormGroup(sampleWithRequiredData);

        const shipmentPlan = service.getShipmentPlan(formGroup) as any;

        expect(shipmentPlan).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipmentPlan should not enable id FormControl', () => {
        const formGroup = service.createShipmentPlanFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipmentPlan should disable id FormControl', () => {
        const formGroup = service.createShipmentPlanFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
