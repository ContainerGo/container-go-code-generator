import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipper-person-group.test-samples';

import { ShipperPersonGroupFormService } from './shipper-person-group-form.service';

describe('ShipperPersonGroup Form Service', () => {
  let service: ShipperPersonGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipperPersonGroupFormService);
  });

  describe('Service methods', () => {
    describe('createShipperPersonGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipperPersonGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IShipperPersonGroup should create a new form with FormGroup', () => {
        const formGroup = service.createShipperPersonGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getShipperPersonGroup', () => {
      it('should return NewShipperPersonGroup for default ShipperPersonGroup initial value', () => {
        const formGroup = service.createShipperPersonGroupFormGroup(sampleWithNewData);

        const shipperPersonGroup = service.getShipperPersonGroup(formGroup) as any;

        expect(shipperPersonGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipperPersonGroup for empty ShipperPersonGroup initial value', () => {
        const formGroup = service.createShipperPersonGroupFormGroup();

        const shipperPersonGroup = service.getShipperPersonGroup(formGroup) as any;

        expect(shipperPersonGroup).toMatchObject({});
      });

      it('should return IShipperPersonGroup', () => {
        const formGroup = service.createShipperPersonGroupFormGroup(sampleWithRequiredData);

        const shipperPersonGroup = service.getShipperPersonGroup(formGroup) as any;

        expect(shipperPersonGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipperPersonGroup should not enable id FormControl', () => {
        const formGroup = service.createShipperPersonGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipperPersonGroup should disable id FormControl', () => {
        const formGroup = service.createShipperPersonGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
