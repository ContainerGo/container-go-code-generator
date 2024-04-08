import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IContainer } from 'app/entities/container/container.model';
import { ContainerService } from 'app/entities/container/service/container.service';
import { ShipmentPlanService } from '../service/shipment-plan.service';
import { IShipmentPlan } from '../shipment-plan.model';
import { ShipmentPlanFormService } from './shipment-plan-form.service';

import { ShipmentPlanUpdateComponent } from './shipment-plan-update.component';

describe('ShipmentPlan Management Update Component', () => {
  let comp: ShipmentPlanUpdateComponent;
  let fixture: ComponentFixture<ShipmentPlanUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipmentPlanFormService: ShipmentPlanFormService;
  let shipmentPlanService: ShipmentPlanService;
  let containerService: ContainerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ShipmentPlanUpdateComponent],
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
      .overrideTemplate(ShipmentPlanUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipmentPlanUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipmentPlanFormService = TestBed.inject(ShipmentPlanFormService);
    shipmentPlanService = TestBed.inject(ShipmentPlanService);
    containerService = TestBed.inject(ContainerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Container query and add missing value', () => {
      const shipmentPlan: IShipmentPlan = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const container: IContainer = { id: '5bc1eeee-1fd8-48bb-94b5-2e9f13d2121d' };
      shipmentPlan.container = container;

      const containerCollection: IContainer[] = [{ id: 'afe1d466-58e2-4f44-bc4a-681159393bb5' }];
      jest.spyOn(containerService, 'query').mockReturnValue(of(new HttpResponse({ body: containerCollection })));
      const additionalContainers = [container];
      const expectedCollection: IContainer[] = [...additionalContainers, ...containerCollection];
      jest.spyOn(containerService, 'addContainerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipmentPlan });
      comp.ngOnInit();

      expect(containerService.query).toHaveBeenCalled();
      expect(containerService.addContainerToCollectionIfMissing).toHaveBeenCalledWith(
        containerCollection,
        ...additionalContainers.map(expect.objectContaining),
      );
      expect(comp.containersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shipmentPlan: IShipmentPlan = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const container: IContainer = { id: '97741189-c267-469e-bd00-6711d6ba4fdb' };
      shipmentPlan.container = container;

      activatedRoute.data = of({ shipmentPlan });
      comp.ngOnInit();

      expect(comp.containersSharedCollection).toContain(container);
      expect(comp.shipmentPlan).toEqual(shipmentPlan);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentPlan>>();
      const shipmentPlan = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipmentPlanFormService, 'getShipmentPlan').mockReturnValue(shipmentPlan);
      jest.spyOn(shipmentPlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentPlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentPlan }));
      saveSubject.complete();

      // THEN
      expect(shipmentPlanFormService.getShipmentPlan).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipmentPlanService.update).toHaveBeenCalledWith(expect.objectContaining(shipmentPlan));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentPlan>>();
      const shipmentPlan = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipmentPlanFormService, 'getShipmentPlan').mockReturnValue({ id: null });
      jest.spyOn(shipmentPlanService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentPlan: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentPlan }));
      saveSubject.complete();

      // THEN
      expect(shipmentPlanFormService.getShipmentPlan).toHaveBeenCalled();
      expect(shipmentPlanService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentPlan>>();
      const shipmentPlan = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(shipmentPlanService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentPlan });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipmentPlanService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContainer', () => {
      it('Should forward to containerService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(containerService, 'compareContainer');
        comp.compareContainer(entity, entity2);
        expect(containerService.compareContainer).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
