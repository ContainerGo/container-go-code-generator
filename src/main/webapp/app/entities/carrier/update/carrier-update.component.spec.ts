import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CarrierService } from '../service/carrier.service';
import { ICarrier } from '../carrier.model';
import { CarrierFormService } from './carrier-form.service';

import { CarrierUpdateComponent } from './carrier-update.component';

describe('Carrier Management Update Component', () => {
  let comp: CarrierUpdateComponent;
  let fixture: ComponentFixture<CarrierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carrierFormService: CarrierFormService;
  let carrierService: CarrierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CarrierUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CarrierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarrierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    carrierFormService = TestBed.inject(CarrierFormService);
    carrierService = TestBed.inject(CarrierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const carrier: ICarrier = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };

      activatedRoute.data = of({ carrier });
      comp.ngOnInit();

      expect(comp.carrier).toEqual(carrier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrier>>();
      const carrier = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(carrierFormService, 'getCarrier').mockReturnValue(carrier);
      jest.spyOn(carrierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrier }));
      saveSubject.complete();

      // THEN
      expect(carrierFormService.getCarrier).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(carrierService.update).toHaveBeenCalledWith(expect.objectContaining(carrier));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrier>>();
      const carrier = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(carrierFormService, 'getCarrier').mockReturnValue({ id: null });
      jest.spyOn(carrierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrier: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrier }));
      saveSubject.complete();

      // THEN
      expect(carrierFormService.getCarrier).toHaveBeenCalled();
      expect(carrierService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrier>>();
      const carrier = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(carrierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(carrierService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
