import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipper.test-samples';

import { ShipperFormService } from './shipper-form.service';

describe('Shipper Form Service', () => {
  let service: ShipperFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipperFormService);
  });

  describe('Service methods', () => {
    describe('createShipperFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipperFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            taxCode: expect.any(Object),
            bankAccount: expect.any(Object),
            bankName: expect.any(Object),
            accountName: expect.any(Object),
            branchName: expect.any(Object),
          }),
        );
      });

      it('passing IShipper should create a new form with FormGroup', () => {
        const formGroup = service.createShipperFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            address: expect.any(Object),
            taxCode: expect.any(Object),
            bankAccount: expect.any(Object),
            bankName: expect.any(Object),
            accountName: expect.any(Object),
            branchName: expect.any(Object),
          }),
        );
      });
    });

    describe('getShipper', () => {
      it('should return NewShipper for default Shipper initial value', () => {
        const formGroup = service.createShipperFormGroup(sampleWithNewData);

        const shipper = service.getShipper(formGroup) as any;

        expect(shipper).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipper for empty Shipper initial value', () => {
        const formGroup = service.createShipperFormGroup();

        const shipper = service.getShipper(formGroup) as any;

        expect(shipper).toMatchObject({});
      });

      it('should return IShipper', () => {
        const formGroup = service.createShipperFormGroup(sampleWithRequiredData);

        const shipper = service.getShipper(formGroup) as any;

        expect(shipper).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipper should not enable id FormControl', () => {
        const formGroup = service.createShipperFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipper should disable id FormControl', () => {
        const formGroup = service.createShipperFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
