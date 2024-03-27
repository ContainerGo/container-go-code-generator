import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ITruckType } from 'app/entities/truck-type/truck-type.model';
import { TruckTypeService } from 'app/entities/truck-type/service/truck-type.service';
import { ICarrier } from 'app/entities/carrier/carrier.model';
import { CarrierService } from 'app/entities/carrier/service/carrier.service';
import { ITruck } from '../truck.model';
import { TruckService } from '../service/truck.service';
import { TruckFormService } from './truck-form.service';

import { TruckUpdateComponent } from './truck-update.component';

describe('Truck Management Update Component', () => {
  let comp: TruckUpdateComponent;
  let fixture: ComponentFixture<TruckUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let truckFormService: TruckFormService;
  let truckService: TruckService;
  let truckTypeService: TruckTypeService;
  let carrierService: CarrierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TruckUpdateComponent],
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
      .overrideTemplate(TruckUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TruckUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    truckFormService = TestBed.inject(TruckFormService);
    truckService = TestBed.inject(TruckService);
    truckTypeService = TestBed.inject(TruckTypeService);
    carrierService = TestBed.inject(CarrierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TruckType query and add missing value', () => {
      const truck: ITruck = { id: 456 };
      const type: ITruckType = { id: 29657 };
      truck.type = type;

      const truckTypeCollection: ITruckType[] = [{ id: 15517 }];
      jest.spyOn(truckTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: truckTypeCollection })));
      const additionalTruckTypes = [type];
      const expectedCollection: ITruckType[] = [...additionalTruckTypes, ...truckTypeCollection];
      jest.spyOn(truckTypeService, 'addTruckTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      expect(truckTypeService.query).toHaveBeenCalled();
      expect(truckTypeService.addTruckTypeToCollectionIfMissing).toHaveBeenCalledWith(
        truckTypeCollection,
        ...additionalTruckTypes.map(expect.objectContaining),
      );
      expect(comp.truckTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Carrier query and add missing value', () => {
      const truck: ITruck = { id: 456 };
      const carrier: ICarrier = { id: 26779 };
      truck.carrier = carrier;

      const carrierCollection: ICarrier[] = [{ id: 27780 }];
      jest.spyOn(carrierService, 'query').mockReturnValue(of(new HttpResponse({ body: carrierCollection })));
      const additionalCarriers = [carrier];
      const expectedCollection: ICarrier[] = [...additionalCarriers, ...carrierCollection];
      jest.spyOn(carrierService, 'addCarrierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      expect(carrierService.query).toHaveBeenCalled();
      expect(carrierService.addCarrierToCollectionIfMissing).toHaveBeenCalledWith(
        carrierCollection,
        ...additionalCarriers.map(expect.objectContaining),
      );
      expect(comp.carriersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const truck: ITruck = { id: 456 };
      const type: ITruckType = { id: 17526 };
      truck.type = type;
      const carrier: ICarrier = { id: 17510 };
      truck.carrier = carrier;

      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      expect(comp.truckTypesSharedCollection).toContain(type);
      expect(comp.carriersSharedCollection).toContain(carrier);
      expect(comp.truck).toEqual(truck);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruck>>();
      const truck = { id: 123 };
      jest.spyOn(truckFormService, 'getTruck').mockReturnValue(truck);
      jest.spyOn(truckService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: truck }));
      saveSubject.complete();

      // THEN
      expect(truckFormService.getTruck).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(truckService.update).toHaveBeenCalledWith(expect.objectContaining(truck));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruck>>();
      const truck = { id: 123 };
      jest.spyOn(truckFormService, 'getTruck').mockReturnValue({ id: null });
      jest.spyOn(truckService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truck: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: truck }));
      saveSubject.complete();

      // THEN
      expect(truckFormService.getTruck).toHaveBeenCalled();
      expect(truckService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITruck>>();
      const truck = { id: 123 };
      jest.spyOn(truckService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ truck });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(truckService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTruckType', () => {
      it('Should forward to truckTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(truckTypeService, 'compareTruckType');
        comp.compareTruckType(entity, entity2);
        expect(truckTypeService.compareTruckType).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
