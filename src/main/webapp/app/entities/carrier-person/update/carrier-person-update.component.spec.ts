import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICarrier } from 'app/entities/carrier/carrier.model';
import { CarrierService } from 'app/entities/carrier/service/carrier.service';
import { CarrierPersonService } from '../service/carrier-person.service';
import { ICarrierPerson } from '../carrier-person.model';
import { CarrierPersonFormService } from './carrier-person-form.service';

import { CarrierPersonUpdateComponent } from './carrier-person-update.component';

describe('CarrierPerson Management Update Component', () => {
  let comp: CarrierPersonUpdateComponent;
  let fixture: ComponentFixture<CarrierPersonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carrierPersonFormService: CarrierPersonFormService;
  let carrierPersonService: CarrierPersonService;
  let carrierService: CarrierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CarrierPersonUpdateComponent],
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
      .overrideTemplate(CarrierPersonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CarrierPersonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    carrierPersonFormService = TestBed.inject(CarrierPersonFormService);
    carrierPersonService = TestBed.inject(CarrierPersonService);
    carrierService = TestBed.inject(CarrierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Carrier query and add missing value', () => {
      const carrierPerson: ICarrierPerson = { id: 456 };
      const carrier: ICarrier = { id: 32672 };
      carrierPerson.carrier = carrier;

      const carrierCollection: ICarrier[] = [{ id: 4272 }];
      jest.spyOn(carrierService, 'query').mockReturnValue(of(new HttpResponse({ body: carrierCollection })));
      const additionalCarriers = [carrier];
      const expectedCollection: ICarrier[] = [...additionalCarriers, ...carrierCollection];
      jest.spyOn(carrierService, 'addCarrierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carrierPerson });
      comp.ngOnInit();

      expect(carrierService.query).toHaveBeenCalled();
      expect(carrierService.addCarrierToCollectionIfMissing).toHaveBeenCalledWith(
        carrierCollection,
        ...additionalCarriers.map(expect.objectContaining),
      );
      expect(comp.carriersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const carrierPerson: ICarrierPerson = { id: 456 };
      const carrier: ICarrier = { id: 21588 };
      carrierPerson.carrier = carrier;

      activatedRoute.data = of({ carrierPerson });
      comp.ngOnInit();

      expect(comp.carriersSharedCollection).toContain(carrier);
      expect(comp.carrierPerson).toEqual(carrierPerson);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierPerson>>();
      const carrierPerson = { id: 123 };
      jest.spyOn(carrierPersonFormService, 'getCarrierPerson').mockReturnValue(carrierPerson);
      jest.spyOn(carrierPersonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierPerson });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrierPerson }));
      saveSubject.complete();

      // THEN
      expect(carrierPersonFormService.getCarrierPerson).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(carrierPersonService.update).toHaveBeenCalledWith(expect.objectContaining(carrierPerson));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierPerson>>();
      const carrierPerson = { id: 123 };
      jest.spyOn(carrierPersonFormService, 'getCarrierPerson').mockReturnValue({ id: null });
      jest.spyOn(carrierPersonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierPerson: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: carrierPerson }));
      saveSubject.complete();

      // THEN
      expect(carrierPersonFormService.getCarrierPerson).toHaveBeenCalled();
      expect(carrierPersonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierPerson>>();
      const carrierPerson = { id: 123 };
      jest.spyOn(carrierPersonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ carrierPerson });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(carrierPersonService.update).toHaveBeenCalled();
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
