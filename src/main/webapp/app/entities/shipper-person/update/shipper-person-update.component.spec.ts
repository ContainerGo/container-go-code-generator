import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IShipper } from 'app/entities/shipper/shipper.model';
import { ShipperService } from 'app/entities/shipper/service/shipper.service';
import { ShipperPersonService } from '../service/shipper-person.service';
import { IShipperPerson } from '../shipper-person.model';
import { ShipperPersonFormService } from './shipper-person-form.service';

import { ShipperPersonUpdateComponent } from './shipper-person-update.component';

describe('ShipperPerson Management Update Component', () => {
  let comp: ShipperPersonUpdateComponent;
  let fixture: ComponentFixture<ShipperPersonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipperPersonFormService: ShipperPersonFormService;
  let shipperPersonService: ShipperPersonService;
  let shipperService: ShipperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ShipperPersonUpdateComponent],
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
      .overrideTemplate(ShipperPersonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipperPersonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipperPersonFormService = TestBed.inject(ShipperPersonFormService);
    shipperPersonService = TestBed.inject(ShipperPersonService);
    shipperService = TestBed.inject(ShipperService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Shipper query and add missing value', () => {
      const shipperPerson: IShipperPerson = { id: 456 };
      const shipper: IShipper = { id: 16090 };
      shipperPerson.shipper = shipper;

      const shipperCollection: IShipper[] = [{ id: 10554 }];
      jest.spyOn(shipperService, 'query').mockReturnValue(of(new HttpResponse({ body: shipperCollection })));
      const additionalShippers = [shipper];
      const expectedCollection: IShipper[] = [...additionalShippers, ...shipperCollection];
      jest.spyOn(shipperService, 'addShipperToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipperPerson });
      comp.ngOnInit();

      expect(shipperService.query).toHaveBeenCalled();
      expect(shipperService.addShipperToCollectionIfMissing).toHaveBeenCalledWith(
        shipperCollection,
        ...additionalShippers.map(expect.objectContaining),
      );
      expect(comp.shippersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shipperPerson: IShipperPerson = { id: 456 };
      const shipper: IShipper = { id: 23995 };
      shipperPerson.shipper = shipper;

      activatedRoute.data = of({ shipperPerson });
      comp.ngOnInit();

      expect(comp.shippersSharedCollection).toContain(shipper);
      expect(comp.shipperPerson).toEqual(shipperPerson);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperPerson>>();
      const shipperPerson = { id: 123 };
      jest.spyOn(shipperPersonFormService, 'getShipperPerson').mockReturnValue(shipperPerson);
      jest.spyOn(shipperPersonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperPerson });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperPerson }));
      saveSubject.complete();

      // THEN
      expect(shipperPersonFormService.getShipperPerson).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipperPersonService.update).toHaveBeenCalledWith(expect.objectContaining(shipperPerson));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperPerson>>();
      const shipperPerson = { id: 123 };
      jest.spyOn(shipperPersonFormService, 'getShipperPerson').mockReturnValue({ id: null });
      jest.spyOn(shipperPersonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperPerson: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperPerson }));
      saveSubject.complete();

      // THEN
      expect(shipperPersonFormService.getShipperPerson).toHaveBeenCalled();
      expect(shipperPersonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperPerson>>();
      const shipperPerson = { id: 123 };
      jest.spyOn(shipperPersonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperPerson });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipperPersonService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareShipper', () => {
      it('Should forward to shipperService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(shipperService, 'compareShipper');
        comp.compareShipper(entity, entity2);
        expect(shipperService.compareShipper).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
