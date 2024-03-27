import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICarrier } from 'app/entities/carrier/carrier.model';
import { CarrierService } from 'app/entities/carrier/service/carrier.service';
import { CarrierAccountService } from '../service/carrier-account.service';
import { ICarrierAccount } from '../carrier-account.model';
import { CarrierAccountFormService } from './carrier-account-form.service';

import { CarrierAccountUpdateComponent } from './carrier-account-update.component';

describe('CarrierAccount Management Update Component', () => {
  let comp: CarrierAccountUpdateComponent;
  let fixture: ComponentFixture<CarrierAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carrierAccountFormService: CarrierAccountFormService;
  let carrierAccountService: CarrierAccountService;
  let carrierService: CarrierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CarrierAccountUpdateComponent],
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
      .overrideTemplate(CarrierAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarrierAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    carrierAccountFormService = TestBed.inject(CarrierAccountFormService);
    carrierAccountService = TestBed.inject(CarrierAccountService);
    carrierService = TestBed.inject(CarrierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Carrier query and add missing value', () => {
      const carrierAccount: ICarrierAccount = { id: 456 };
      const carrier: ICarrier = { id: 16933 };
      carrierAccount.carrier = carrier;

      const carrierCollection: ICarrier[] = [{ id: 1486 }];
      jest.spyOn(carrierService, 'query').mockReturnValue(of(new HttpResponse({ body: carrierCollection })));
      const additionalCarriers = [carrier];
      const expectedCollection: ICarrier[] = [...additionalCarriers, ...carrierCollection];
      jest.spyOn(carrierService, 'addCarrierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carrierAccount });
      comp.ngOnInit();

      expect(carrierService.query).toHaveBeenCalled();
      expect(carrierService.addCarrierToCollectionIfMissing).toHaveBeenCalledWith(
        carrierCollection,
        ...additionalCarriers.map(expect.objectContaining),
      );
      expect(comp.carriersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const carrierAccount: ICarrierAccount = { id: 456 };
      const carrier: ICarrier = { id: 25003 };
      carrierAccount.carrier = carrier;

      activatedRoute.data = of({ carrierAccount });
      comp.ngOnInit();

      expect(comp.carriersSharedCollection).toContain(carrier);
      expect(comp.carrierAccount).toEqual(carrierAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierAccount>>();
      const carrierAccount = { id: 123 };
      jest.spyOn(carrierAccountFormService, 'getCarrierAccount').mockReturnValue(carrierAccount);
      jest.spyOn(carrierAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrierAccount }));
      saveSubject.complete();

      // THEN
      expect(carrierAccountFormService.getCarrierAccount).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(carrierAccountService.update).toHaveBeenCalledWith(expect.objectContaining(carrierAccount));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierAccount>>();
      const carrierAccount = { id: 123 };
      jest.spyOn(carrierAccountFormService, 'getCarrierAccount').mockReturnValue({ id: null });
      jest.spyOn(carrierAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierAccount: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrierAccount }));
      saveSubject.complete();

      // THEN
      expect(carrierAccountFormService.getCarrierAccount).toHaveBeenCalled();
      expect(carrierAccountService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierAccount>>();
      const carrierAccount = { id: 123 };
      jest.spyOn(carrierAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(carrierAccountService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCarrier', () => {
      it('Should forward to carrierService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(carrierService, 'compareCarrier');
        comp.compareCarrier(entity, entity2);
        expect(carrierService.compareCarrier).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
