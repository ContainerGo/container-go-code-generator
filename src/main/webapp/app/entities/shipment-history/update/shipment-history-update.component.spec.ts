import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IContainer } from 'app/entities/container/container.model';
import { ContainerService } from 'app/entities/container/service/container.service';
import { ShipmentHistoryService } from '../service/shipment-history.service';
import { IShipmentHistory } from '../shipment-history.model';
import { ShipmentHistoryFormService } from './shipment-history-form.service';

import { ShipmentHistoryUpdateComponent } from './shipment-history-update.component';

describe('ShipmentHistory Management Update Component', () => {
  let comp: ShipmentHistoryUpdateComponent;
  let fixture: ComponentFixture<ShipmentHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipmentHistoryFormService: ShipmentHistoryFormService;
  let shipmentHistoryService: ShipmentHistoryService;
  let containerService: ContainerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ShipmentHistoryUpdateComponent],
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
      .overrideTemplate(ShipmentHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipmentHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipmentHistoryFormService = TestBed.inject(ShipmentHistoryFormService);
    shipmentHistoryService = TestBed.inject(ShipmentHistoryService);
    containerService = TestBed.inject(ContainerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Container query and add missing value', () => {
      const shipmentHistory: IShipmentHistory = { id: 456 };
      const container: IContainer = { id: 10648 };
      shipmentHistory.container = container;

      const containerCollection: IContainer[] = [{ id: 28826 }];
      jest.spyOn(containerService, 'query').mockReturnValue(of(new HttpResponse({ body: containerCollection })));
      const additionalContainers = [container];
      const expectedCollection: IContainer[] = [...additionalContainers, ...containerCollection];
      jest.spyOn(containerService, 'addContainerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipmentHistory });
      comp.ngOnInit();

      expect(containerService.query).toHaveBeenCalled();
      expect(containerService.addContainerToCollectionIfMissing).toHaveBeenCalledWith(
        containerCollection,
        ...additionalContainers.map(expect.objectContaining),
      );
      expect(comp.containersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shipmentHistory: IShipmentHistory = { id: 456 };
      const container: IContainer = { id: 17194 };
      shipmentHistory.container = container;

      activatedRoute.data = of({ shipmentHistory });
      comp.ngOnInit();

      expect(comp.containersSharedCollection).toContain(container);
      expect(comp.shipmentHistory).toEqual(shipmentHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentHistory>>();
      const shipmentHistory = { id: 123 };
      jest.spyOn(shipmentHistoryFormService, 'getShipmentHistory').mockReturnValue(shipmentHistory);
      jest.spyOn(shipmentHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentHistory }));
      saveSubject.complete();

      // THEN
      expect(shipmentHistoryFormService.getShipmentHistory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipmentHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(shipmentHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentHistory>>();
      const shipmentHistory = { id: 123 };
      jest.spyOn(shipmentHistoryFormService, 'getShipmentHistory').mockReturnValue({ id: null });
      jest.spyOn(shipmentHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentHistory }));
      saveSubject.complete();

      // THEN
      expect(shipmentHistoryFormService.getShipmentHistory).toHaveBeenCalled();
      expect(shipmentHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentHistory>>();
      const shipmentHistory = { id: 123 };
      jest.spyOn(shipmentHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipmentHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContainer', () => {
      it('Should forward to containerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(containerService, 'compareContainer');
        comp.compareContainer(entity, entity2);
        expect(containerService.compareContainer).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
