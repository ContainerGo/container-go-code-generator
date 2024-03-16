import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipper-account.test-samples';

import { ShipperAccountFormService } from './shipper-account-form.service';

describe('ShipperAccount Form Service', () => {
  let service: ShipperAccountFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipperAccountFormService);
  });

  describe('Service methods', () => {
    describe('createShipperAccountFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipperAccountFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            shipper: expect.any(Object),
          }),
        );
      });

      it('passing IShipperAccount should create a new form with FormGroup', () => {
        const formGroup = service.createShipperAccountFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            shipper: expect.any(Object),
          }),
        );
      });
    });

    describe('getShipperAccount', () => {
      it('should return NewShipperAccount for default ShipperAccount initial value', () => {
        const formGroup = service.createShipperAccountFormGroup(sampleWithNewData);

        const shipperAccount = service.getShipperAccount(formGroup) as any;

        expect(shipperAccount).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipperAccount for empty ShipperAccount initial value', () => {
        const formGroup = service.createShipperAccountFormGroup();

        const shipperAccount = service.getShipperAccount(formGroup) as any;

        expect(shipperAccount).toMatchObject({});
      });

      it('should return IShipperAccount', () => {
        const formGroup = service.createShipperAccountFormGroup(sampleWithRequiredData);

        const shipperAccount = service.getShipperAccount(formGroup) as any;

        expect(shipperAccount).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipperAccount should not enable id FormControl', () => {
        const formGroup = service.createShipperAccountFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipperAccount should disable id FormControl', () => {
        const formGroup = service.createShipperAccountFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
