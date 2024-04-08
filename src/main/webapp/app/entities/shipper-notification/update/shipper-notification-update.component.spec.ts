import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IShipperPerson } from 'app/entities/shipper-person/shipper-person.model';
import { ShipperPersonService } from 'app/entities/shipper-person/service/shipper-person.service';
import { ShipperNotificationService } from '../service/shipper-notification.service';
import { IShipperNotification } from '../shipper-notification.model';
import { ShipperNotificationFormService } from './shipper-notification-form.service';

import { ShipperNotificationUpdateComponent } from './shipper-notification-update.component';

describe('ShipperNotification Management Update Component', () => {
  let comp: ShipperNotificationUpdateComponent;
  let fixture: ComponentFixture<ShipperNotificationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipperNotificationFormService: ShipperNotificationFormService;
  let shipperNotificationService: ShipperNotificationService;
  let shipperPersonService: ShipperPersonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ShipperNotificationUpdateComponent],
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
      .overrideTemplate(ShipperNotificationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipperNotificationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipperNotificationFormService = TestBed.inject(ShipperNotificationFormService);
    shipperNotificationService = TestBed.inject(ShipperNotificationService);
    shipperPersonService = TestBed.inject(ShipperPersonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ShipperPerson query and add missing value', () => {
      const shipperNotification: IShipperNotification = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const person: IShipperPerson = { id: '8a86070c-84ef-4290-bcb3-9b310c89e233' };
      shipperNotification.person = person;

      const shipperPersonCollection: IShipperPerson[] = [{ id: 'a8e4a286-8748-4589-8779-a284991187c5' }];
      jest.spyOn(shipperPersonService, 'query').mockReturnValue(of(new HttpResponse({ body: shipperPersonCollection })));
      const additionalShipperPeople = [person];
      const expectedCollection: IShipperPerson[] = [...additionalShipperPeople, ...shipperPersonCollection];
      jest.spyOn(shipperPersonService, 'addShipperPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipperNotification });
      comp.ngOnInit();

      expect(shipperPersonService.query).toHaveBeenCalled();
      expect(shipperPersonService.addShipperPersonToCollectionIfMissing).toHaveBeenCalledWith(
        shipperPersonCollection,
        ...additionalShipperPeople.map(expect.objectContaining),
      );
      expect(comp.shipperPeopleSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shipperNotification: IShipperNotification = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const person: IShipperPerson = { id: '83d5fa4f-65a4-42eb-b605-ce9aa6107d85' };
      shipperNotification.person = person;

      activatedRoute.data = of({ shipperNotification });
      comp.ngOnInit();

      expect(comp.shipperPeopleSharedCollection).toContain(person);
      expect(comp.shipperNotification).toEqual(shipperNotification);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperNotification>>();
      const shipperNotification = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipperNotificationFormService, 'getShipperNotification').mockReturnValue(shipperNotification);
      jest.spyOn(shipperNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperNotification }));
      saveSubject.complete();

      // THEN
      expect(shipperNotificationFormService.getShipperNotification).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipperNotificationService.update).toHaveBeenCalledWith(expect.objectContaining(shipperNotification));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperNotification>>();
      const shipperNotification = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipperNotificationFormService, 'getShipperNotification').mockReturnValue({ id: null });
      jest.spyOn(shipperNotificationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperNotification: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperNotification }));
      saveSubject.complete();

      // THEN
      expect(shipperNotificationFormService.getShipperNotification).toHaveBeenCalled();
      expect(shipperNotificationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperNotification>>();
      const shipperNotification = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipperNotificationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperNotification });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipperNotificationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareShipperPerson', () => {
      it('Should forward to shipperPersonService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(shipperPersonService, 'compareShipperPerson');
        comp.compareShipperPerson(entity, entity2);
        expect(shipperPersonService.compareShipperPerson).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
