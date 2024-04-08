import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../center-person-group.test-samples';

import { CenterPersonGroupFormService } from './center-person-group-form.service';

describe('CenterPersonGroup Form Service', () => {
  let service: CenterPersonGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CenterPersonGroupFormService);
  });

  describe('Service methods', () => {
    describe('createCenterPersonGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCenterPersonGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing ICenterPersonGroup should create a new form with FormGroup', () => {
        const formGroup = service.createCenterPersonGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getCenterPersonGroup', () => {
      it('should return NewCenterPersonGroup for default CenterPersonGroup initial value', () => {
        const formGroup = service.createCenterPersonGroupFormGroup(sampleWithNewData);

        const centerPersonGroup = service.getCenterPersonGroup(formGroup) as any;

        expect(centerPersonGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewCenterPersonGroup for empty CenterPersonGroup initial value', () => {
        const formGroup = service.createCenterPersonGroupFormGroup();

        const centerPersonGroup = service.getCenterPersonGroup(formGroup) as any;

        expect(centerPersonGroup).toMatchObject({});
      });

      it('should return ICenterPersonGroup', () => {
        const formGroup = service.createCenterPersonGroupFormGroup(sampleWithRequiredData);

        const centerPersonGroup = service.getCenterPersonGroup(formGroup) as any;

        expect(centerPersonGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICenterPersonGroup should not enable id FormControl', () => {
        const formGroup = service.createCenterPersonGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCenterPersonGroup should disable id FormControl', () => {
        const formGroup = service.createCenterPersonGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
