import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipper-notification.test-samples';

import { ShipperNotificationFormService } from './shipper-notification-form.service';

describe('ShipperNotification Form Service', () => {
  let service: ShipperNotificationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipperNotificationFormService);
  });

  describe('Service methods', () => {
    describe('createShipperNotificationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipperNotificationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            isEmailNotificationEnabled: expect.any(Object),
            isSmsNotificationEnabled: expect.any(Object),
            isAppNotificationEnabled: expect.any(Object),
            person: expect.any(Object),
          }),
        );
      });

      it('passing IShipperNotification should create a new form with FormGroup', () => {
        const formGroup = service.createShipperNotificationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            isEmailNotificationEnabled: expect.any(Object),
            isSmsNotificationEnabled: expect.any(Object),
            isAppNotificationEnabled: expect.any(Object),
            person: expect.any(Object),
          }),
        );
      });
    });

    describe('getShipperNotification', () => {
      it('should return NewShipperNotification for default ShipperNotification initial value', () => {
        const formGroup = service.createShipperNotificationFormGroup(sampleWithNewData);

        const shipperNotification = service.getShipperNotification(formGroup) as any;

        expect(shipperNotification).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipperNotification for empty ShipperNotification initial value', () => {
        const formGroup = service.createShipperNotificationFormGroup();

        const shipperNotification = service.getShipperNotification(formGroup) as any;

        expect(shipperNotification).toMatchObject({});
      });

      it('should return IShipperNotification', () => {
        const formGroup = service.createShipperNotificationFormGroup(sampleWithRequiredData);

        const shipperNotification = service.getShipperNotification(formGroup) as any;

        expect(shipperNotification).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipperNotification should not enable id FormControl', () => {
        const formGroup = service.createShipperNotificationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipperNotification should disable id FormControl', () => {
        const formGroup = service.createShipperNotificationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
