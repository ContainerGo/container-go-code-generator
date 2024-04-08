import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICarrierPersonGroup } from 'app/entities/carrier-person-group/carrier-person-group.model';
import { CarrierPersonGroupService } from 'app/entities/carrier-person-group/service/carrier-person-group.service';
import { ICarrier } from 'app/entities/carrier/carrier.model';
import { CarrierService } from 'app/entities/carrier/service/carrier.service';
import { ICarrierPerson } from '../carrier-person.model';
import { CarrierPersonService } from '../service/carrier-person.service';
import { CarrierPersonFormService } from './carrier-person-form.service';

import { CarrierPersonUpdateComponent } from './carrier-person-update.component';

describe('CarrierPerson Management Update Component', () => {
  let comp: CarrierPersonUpdateComponent;
  let fixture: ComponentFixture<CarrierPersonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let carrierPersonFormService: CarrierPersonFormService;
  let carrierPersonService: CarrierPersonService;
  let carrierPersonGroupService: CarrierPersonGroupService;
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
    carrierPersonGroupService = TestBed.inject(CarrierPersonGroupService);
    carrierService = TestBed.inject(CarrierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CarrierPersonGroup query and add missing value', () => {
      const carrierPerson: ICarrierPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: ICarrierPersonGroup = { id: 'ccff1006-b9c1-438a-90f1-3f313b57af11' };
      carrierPerson.group = group;

      const carrierPersonGroupCollection: ICarrierPersonGroup[] = [{ id: '92467459-5179-4e9c-a002-8ce5f052271f' }];
      jest.spyOn(carrierPersonGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: carrierPersonGroupCollection })));
      const additionalCarrierPersonGroups = [group];
      const expectedCollection: ICarrierPersonGroup[] = [...additionalCarrierPersonGroups, ...carrierPersonGroupCollection];
      jest.spyOn(carrierPersonGroupService, 'addCarrierPersonGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ carrierPerson });
      comp.ngOnInit();

      expect(carrierPersonGroupService.query).toHaveBeenCalled();
      expect(carrierPersonGroupService.addCarrierPersonGroupToCollectionIfMissing).toHaveBeenCalledWith(
        carrierPersonGroupCollection,
        ...additionalCarrierPersonGroups.map(expect.objectContaining),
      );
      expect(comp.carrierPersonGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Carrier query and add missing value', () => {
      const carrierPerson: ICarrierPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const carrier: ICarrier = { id: '11a8342e-c255-4135-81bf-e9a4b7c30527' };
      carrierPerson.carrier = carrier;

      const carrierCollection: ICarrier[] = [{ id: '00e95841-98cf-4abc-b387-8102b7f40772' }];
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
      const carrierPerson: ICarrierPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: ICarrierPersonGroup = { id: '28525861-8b67-4328-ba9a-8b4f8f4f6149' };
      carrierPerson.group = group;
      const carrier: ICarrier = { id: '19b02f92-b673-4451-ba15-6866e2a39f87' };
      carrierPerson.carrier = carrier;

      activatedRoute.data = of({ carrierPerson });
      comp.ngOnInit();

      expect(comp.carrierPersonGroupsSharedCollection).toContain(group);
      expect(comp.carriersSharedCollection).toContain(carrier);
      expect(comp.carrierPerson).toEqual(carrierPerson);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICarrierPerson>>();
      const carrierPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const carrierPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const carrierPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
    describe('compareCarrierPersonGroup', () => {
      it('Should forward to carrierPersonGroupService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(carrierPersonGroupService, 'compareCarrierPersonGroup');
        comp.compareCarrierPersonGroup(entity, entity2);
        expect(carrierPersonGroupService.compareCarrierPersonGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCarrier', () => {
      it('Should forward to carrierService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(carrierService, 'compareCarrier');
        comp.compareCarrier(entity, entity2);
        expect(carrierService.compareCarrier).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
