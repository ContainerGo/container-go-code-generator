import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipment-history.test-samples';

import { ShipmentHistoryFormService } from './shipment-history-form.service';

describe('ShipmentHistory Form Service', () => {
  let service: ShipmentHistoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipmentHistoryFormService);
  });

  describe('Service methods', () => {
    describe('createShipmentHistoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipmentHistoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            event: expect.any(Object),
            timestamp: expect.any(Object),
            executedBy: expect.any(Object),
            location: expect.any(Object),
            lat: expect.any(Object),
            lng: expect.any(Object),
            container: expect.any(Object),
          }),
        );
      });

      it('passing IShipmentHistory should create a new form with FormGroup', () => {
        const formGroup = service.createShipmentHistoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            event: expect.any(Object),
            timestamp: expect.any(Object),
            executedBy: expect.any(Object),
            location: expect.any(Object),
            lat: expect.any(Object),
            lng: expect.any(Object),
            container: expect.any(Object),
          }),
        );
      });
    });

    describe('getShipmentHistory', () => {
      it('should return NewShipmentHistory for default ShipmentHistory initial value', () => {
        const formGroup = service.createShipmentHistoryFormGroup(sampleWithNewData);

        const shipmentHistory = service.getShipmentHistory(formGroup) as any;

        expect(shipmentHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipmentHistory for empty ShipmentHistory initial value', () => {
        const formGroup = service.createShipmentHistoryFormGroup();

        const shipmentHistory = service.getShipmentHistory(formGroup) as any;

        expect(shipmentHistory).toMatchObject({});
      });

      it('should return IShipmentHistory', () => {
        const formGroup = service.createShipmentHistoryFormGroup(sampleWithRequiredData);

        const shipmentHistory = service.getShipmentHistory(formGroup) as any;

        expect(shipmentHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipmentHistory should not enable id FormControl', () => {
        const formGroup = service.createShipmentHistoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipmentHistory should disable id FormControl', () => {
        const formGroup = service.createShipmentHistoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
