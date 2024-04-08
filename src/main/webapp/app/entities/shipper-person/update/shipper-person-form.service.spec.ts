import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipper-person.test-samples';

import { ShipperPersonFormService } from './shipper-person-form.service';

describe('ShipperPerson Form Service', () => {
  let service: ShipperPersonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipperPersonFormService);
  });

  describe('Service methods', () => {
    describe('createShipperPersonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipperPersonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            group: expect.any(Object),
            shipper: expect.any(Object),
          }),
        );
      });

      it('passing IShipperPerson should create a new form with FormGroup', () => {
        const formGroup = service.createShipperPersonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            group: expect.any(Object),
            shipper: expect.any(Object),
          }),
        );
      });
    });

    describe('getShipperPerson', () => {
      it('should return NewShipperPerson for default ShipperPerson initial value', () => {
        const formGroup = service.createShipperPersonFormGroup(sampleWithNewData);

        const shipperPerson = service.getShipperPerson(formGroup) as any;

        expect(shipperPerson).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipperPerson for empty ShipperPerson initial value', () => {
        const formGroup = service.createShipperPersonFormGroup();

        const shipperPerson = service.getShipperPerson(formGroup) as any;

        expect(shipperPerson).toMatchObject({});
      });

      it('should return IShipperPerson', () => {
        const formGroup = service.createShipperPersonFormGroup(sampleWithRequiredData);

        const shipperPerson = service.getShipperPerson(formGroup) as any;

        expect(shipperPerson).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipperPerson should not enable id FormControl', () => {
        const formGroup = service.createShipperPersonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipperPerson should disable id FormControl', () => {
        const formGroup = service.createShipperPersonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
