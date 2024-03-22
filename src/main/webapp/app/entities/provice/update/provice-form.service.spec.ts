import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../provice.test-samples';

import { ProviceFormService } from './provice-form.service';

describe('Provice Form Service', () => {
  let service: ProviceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProviceFormService);
  });

  describe('Service methods', () => {
    describe('createProviceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProviceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });

      it('passing IProvice should create a new form with FormGroup', () => {
        const formGroup = service.createProviceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
          }),
        );
      });
    });

    describe('getProvice', () => {
      it('should return NewProvice for default Provice initial value', () => {
        const formGroup = service.createProviceFormGroup(sampleWithNewData);

        const provice = service.getProvice(formGroup) as any;

        expect(provice).toMatchObject(sampleWithNewData);
      });

      it('should return NewProvice for empty Provice initial value', () => {
        const formGroup = service.createProviceFormGroup();

        const provice = service.getProvice(formGroup) as any;

        expect(provice).toMatchObject({});
      });

      it('should return IProvice', () => {
        const formGroup = service.createProviceFormGroup(sampleWithRequiredData);

        const provice = service.getProvice(formGroup) as any;

        expect(provice).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProvice should not enable id FormControl', () => {
        const formGroup = service.createProviceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProvice should disable id FormControl', () => {
        const formGroup = service.createProviceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
