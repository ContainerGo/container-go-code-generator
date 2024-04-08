import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IShipperPersonGroup } from 'app/entities/shipper-person-group/shipper-person-group.model';
import { ShipperPersonGroupService } from 'app/entities/shipper-person-group/service/shipper-person-group.service';
import { IShipper } from 'app/entities/shipper/shipper.model';
import { ShipperService } from 'app/entities/shipper/service/shipper.service';
import { IShipperPerson } from '../shipper-person.model';
import { ShipperPersonService } from '../service/shipper-person.service';
import { ShipperPersonFormService } from './shipper-person-form.service';

import { ShipperPersonUpdateComponent } from './shipper-person-update.component';

describe('ShipperPerson Management Update Component', () => {
  let comp: ShipperPersonUpdateComponent;
  let fixture: ComponentFixture<ShipperPersonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipperPersonFormService: ShipperPersonFormService;
  let shipperPersonService: ShipperPersonService;
  let shipperPersonGroupService: ShipperPersonGroupService;
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
    shipperPersonGroupService = TestBed.inject(ShipperPersonGroupService);
    shipperService = TestBed.inject(ShipperService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ShipperPersonGroup query and add missing value', () => {
      const shipperPerson: IShipperPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: IShipperPersonGroup = { id: 'f89a2e18-95d9-4a8c-b83c-f72ecee7ec8b' };
      shipperPerson.group = group;

      const shipperPersonGroupCollection: IShipperPersonGroup[] = [{ id: '5997c9f7-40fe-470b-a2e8-363c29718487' }];
      jest.spyOn(shipperPersonGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: shipperPersonGroupCollection })));
      const additionalShipperPersonGroups = [group];
      const expectedCollection: IShipperPersonGroup[] = [...additionalShipperPersonGroups, ...shipperPersonGroupCollection];
      jest.spyOn(shipperPersonGroupService, 'addShipperPersonGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipperPerson });
      comp.ngOnInit();

      expect(shipperPersonGroupService.query).toHaveBeenCalled();
      expect(shipperPersonGroupService.addShipperPersonGroupToCollectionIfMissing).toHaveBeenCalledWith(
        shipperPersonGroupCollection,
        ...additionalShipperPersonGroups.map(expect.objectContaining),
      );
      expect(comp.shipperPersonGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Shipper query and add missing value', () => {
      const shipperPerson: IShipperPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const shipper: IShipper = { id: '889d232f-44aa-4a15-80a3-a5b0441cdb66' };
      shipperPerson.shipper = shipper;

      const shipperCollection: IShipper[] = [{ id: '3eb45654-909c-44b2-a7f4-6a1120c0f3d7' }];
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
      const shipperPerson: IShipperPerson = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const group: IShipperPersonGroup = { id: 'fdc0e97e-1cfd-4efe-92a7-5dc4f5e22d4d' };
      shipperPerson.group = group;
      const shipper: IShipper = { id: '2d93abd0-a2ac-4126-8e74-90f2c23213c9' };
      shipperPerson.shipper = shipper;

      activatedRoute.data = of({ shipperPerson });
      comp.ngOnInit();

      expect(comp.shipperPersonGroupsSharedCollection).toContain(group);
      expect(comp.shippersSharedCollection).toContain(shipper);
      expect(comp.shipperPerson).toEqual(shipperPerson);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperPerson>>();
      const shipperPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const shipperPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const shipperPerson = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
    describe('compareShipperPersonGroup', () => {
      it('Should forward to shipperPersonGroupService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(shipperPersonGroupService, 'compareShipperPersonGroup');
        comp.compareShipperPersonGroup(entity, entity2);
        expect(shipperPersonGroupService.compareShipperPersonGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareShipper', () => {
      it('Should forward to shipperService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(shipperService, 'compareShipper');
        comp.compareShipper(entity, entity2);
        expect(shipperService.compareShipper).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
