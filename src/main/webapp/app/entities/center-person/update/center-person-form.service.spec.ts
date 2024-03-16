import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../center-person.test-samples';

import { CenterPersonFormService } from './center-person-form.service';

describe('CenterPerson Form Service', () => {
  let service: CenterPersonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CenterPersonFormService);
  });

  describe('Service methods', () => {
    describe('createCenterPersonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCenterPersonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            groups: expect.any(Object),
          }),
        );
      });

      it('passing ICenterPerson should create a new form with FormGroup', () => {
        const formGroup = service.createCenterPersonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            address: expect.any(Object),
            groups: expect.any(Object),
          }),
        );
      });
    });

    describe('getCenterPerson', () => {
      it('should return NewCenterPerson for default CenterPerson initial value', () => {
        const formGroup = service.createCenterPersonFormGroup(sampleWithNewData);

        const centerPerson = service.getCenterPerson(formGroup) as any;

        expect(centerPerson).toMatchObject(sampleWithNewData);
      });

      it('should return NewCenterPerson for empty CenterPerson initial value', () => {
        const formGroup = service.createCenterPersonFormGroup();

        const centerPerson = service.getCenterPerson(formGroup) as any;

        expect(centerPerson).toMatchObject({});
      });

      it('should return ICenterPerson', () => {
        const formGroup = service.createCenterPersonFormGroup(sampleWithRequiredData);

        const centerPerson = service.getCenterPerson(formGroup) as any;

        expect(centerPerson).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICenterPerson should not enable id FormControl', () => {
        const formGroup = service.createCenterPersonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCenterPerson should disable id FormControl', () => {
        const formGroup = service.createCenterPersonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
