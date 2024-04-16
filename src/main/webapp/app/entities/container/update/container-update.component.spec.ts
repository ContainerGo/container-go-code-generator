import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProvice } from 'app/entities/provice/provice.model';
import { ProviceService } from 'app/entities/provice/service/provice.service';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';
import { IWard } from 'app/entities/ward/ward.model';
import { WardService } from 'app/entities/ward/service/ward.service';
import { IContainerType } from 'app/entities/container-type/container-type.model';
import { ContainerTypeService } from 'app/entities/container-type/service/container-type.service';
import { IContainerStatus } from 'app/entities/container-status/container-status.model';
import { ContainerStatusService } from 'app/entities/container-status/service/container-status.service';
import { ITruckType } from 'app/entities/truck-type/truck-type.model';
import { TruckTypeService } from 'app/entities/truck-type/service/truck-type.service';
import { ITruck } from 'app/entities/truck/truck.model';
import { TruckService } from 'app/entities/truck/service/truck.service';
import { IContainerOwner } from 'app/entities/container-owner/container-owner.model';
import { ContainerOwnerService } from 'app/entities/container-owner/service/container-owner.service';
import { IContainer } from '../container.model';
import { ContainerService } from '../service/container.service';
import { ContainerFormService } from './container-form.service';

import { ContainerUpdateComponent } from './container-update.component';

describe('Container Management Update Component', () => {
  let comp: ContainerUpdateComponent;
  let fixture: ComponentFixture<ContainerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let containerFormService: ContainerFormService;
  let containerService: ContainerService;
  let proviceService: ProviceService;
  let districtService: DistrictService;
  let wardService: WardService;
  let containerTypeService: ContainerTypeService;
  let containerStatusService: ContainerStatusService;
  let truckTypeService: TruckTypeService;
  let truckService: TruckService;
  let containerOwnerService: ContainerOwnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ContainerUpdateComponent],
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
      .overrideTemplate(ContainerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContainerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    containerFormService = TestBed.inject(ContainerFormService);
    containerService = TestBed.inject(ContainerService);
    proviceService = TestBed.inject(ProviceService);
    districtService = TestBed.inject(DistrictService);
    wardService = TestBed.inject(WardService);
    containerTypeService = TestBed.inject(ContainerTypeService);
    containerStatusService = TestBed.inject(ContainerStatusService);
    truckTypeService = TestBed.inject(TruckTypeService);
    truckService = TestBed.inject(TruckService);
    containerOwnerService = TestBed.inject(ContainerOwnerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Provice query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const pickupProvice: IProvice = { id: '698c0dad-958b-49b5-a03b-97bb066333bc' };
      container.pickupProvice = pickupProvice;
      const dropoffProvice: IProvice = { id: '6b54fb5e-0788-4762-a02e-6c169788bc77' };
      container.dropoffProvice = dropoffProvice;

      const proviceCollection: IProvice[] = [{ id: 'b7ca9773-38a6-460a-a1c4-00569de0e107' }];
      jest.spyOn(proviceService, 'query').mockReturnValue(of(new HttpResponse({ body: proviceCollection })));
      const additionalProvices = [pickupProvice, dropoffProvice];
      const expectedCollection: IProvice[] = [...additionalProvices, ...proviceCollection];
      jest.spyOn(proviceService, 'addProviceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(proviceService.query).toHaveBeenCalled();
      expect(proviceService.addProviceToCollectionIfMissing).toHaveBeenCalledWith(
        proviceCollection,
        ...additionalProvices.map(expect.objectContaining),
      );
      expect(comp.provicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call District query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const pickupDistrict: IDistrict = { id: '45fc1f99-0cfc-420a-a0f5-9a8db4ab26b6' };
      container.pickupDistrict = pickupDistrict;
      const dropoffDistrict: IDistrict = { id: '6b5bb2c6-b4e4-4e71-bc41-a49ac3594bea' };
      container.dropoffDistrict = dropoffDistrict;

      const districtCollection: IDistrict[] = [{ id: '3208deea-bdb2-4301-8b33-c938c0c85e71' }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [pickupDistrict, dropoffDistrict];
      const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
      jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(districtService.query).toHaveBeenCalled();
      expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(
        districtCollection,
        ...additionalDistricts.map(expect.objectContaining),
      );
      expect(comp.districtsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ward query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const pickupWard: IWard = { id: '31ef774b-8c82-4d48-b2e9-ac9296f3fe93' };
      container.pickupWard = pickupWard;
      const dropoffWard: IWard = { id: 'db375339-371c-41a3-b35e-d95a1225afaf' };
      container.dropoffWard = dropoffWard;

      const wardCollection: IWard[] = [{ id: '5179ac69-8102-41de-b771-a665488de900' }];
      jest.spyOn(wardService, 'query').mockReturnValue(of(new HttpResponse({ body: wardCollection })));
      const additionalWards = [pickupWard, dropoffWard];
      const expectedCollection: IWard[] = [...additionalWards, ...wardCollection];
      jest.spyOn(wardService, 'addWardToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(wardService.query).toHaveBeenCalled();
      expect(wardService.addWardToCollectionIfMissing).toHaveBeenCalledWith(
        wardCollection,
        ...additionalWards.map(expect.objectContaining),
      );
      expect(comp.wardsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ContainerType query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const type: IContainerType = { id: '456c18da-e58d-4f80-a005-d85f5ed7a6f1' };
      container.type = type;

      const containerTypeCollection: IContainerType[] = [{ id: '6fb29ec6-9d0d-4a34-8c1a-2ec5979b422b' }];
      jest.spyOn(containerTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: containerTypeCollection })));
      const additionalContainerTypes = [type];
      const expectedCollection: IContainerType[] = [...additionalContainerTypes, ...containerTypeCollection];
      jest.spyOn(containerTypeService, 'addContainerTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(containerTypeService.query).toHaveBeenCalled();
      expect(containerTypeService.addContainerTypeToCollectionIfMissing).toHaveBeenCalledWith(
        containerTypeCollection,
        ...additionalContainerTypes.map(expect.objectContaining),
      );
      expect(comp.containerTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ContainerStatus query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const status: IContainerStatus = { id: 'f6179e9c-33bd-472b-96ee-68771094e23d' };
      container.status = status;

      const containerStatusCollection: IContainerStatus[] = [{ id: '06977044-59ee-45ad-a4de-79c60fe3f035' }];
      jest.spyOn(containerStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: containerStatusCollection })));
      const additionalContainerStatuses = [status];
      const expectedCollection: IContainerStatus[] = [...additionalContainerStatuses, ...containerStatusCollection];
      jest.spyOn(containerStatusService, 'addContainerStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(containerStatusService.query).toHaveBeenCalled();
      expect(containerStatusService.addContainerStatusToCollectionIfMissing).toHaveBeenCalledWith(
        containerStatusCollection,
        ...additionalContainerStatuses.map(expect.objectContaining),
      );
      expect(comp.containerStatusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TruckType query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const truckType: ITruckType = { id: '5293516b-1550-46fb-8718-dbfc13319bec' };
      container.truckType = truckType;

      const truckTypeCollection: ITruckType[] = [{ id: 'b4227768-587d-49d8-8c0c-0d17e9e13735' }];
      jest.spyOn(truckTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: truckTypeCollection })));
      const additionalTruckTypes = [truckType];
      const expectedCollection: ITruckType[] = [...additionalTruckTypes, ...truckTypeCollection];
      jest.spyOn(truckTypeService, 'addTruckTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(truckTypeService.query).toHaveBeenCalled();
      expect(truckTypeService.addTruckTypeToCollectionIfMissing).toHaveBeenCalledWith(
        truckTypeCollection,
        ...additionalTruckTypes.map(expect.objectContaining),
      );
      expect(comp.truckTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Truck query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const truck: ITruck = { id: '8289d9a7-eafb-47ba-9204-68139efb98d9' };
      container.truck = truck;

      const truckCollection: ITruck[] = [{ id: 'aa8375e4-9e6e-4f6e-bc38-3ffa70bdc9e6' }];
      jest.spyOn(truckService, 'query').mockReturnValue(of(new HttpResponse({ body: truckCollection })));
      const additionalTrucks = [truck];
      const expectedCollection: ITruck[] = [...additionalTrucks, ...truckCollection];
      jest.spyOn(truckService, 'addTruckToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(truckService.query).toHaveBeenCalled();
      expect(truckService.addTruckToCollectionIfMissing).toHaveBeenCalledWith(
        truckCollection,
        ...additionalTrucks.map(expect.objectContaining),
      );
      expect(comp.trucksSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ContainerOwner query and add missing value', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const owner: IContainerOwner = { id: 'b5558a82-d425-46b3-ae90-adca2af0890b' };
      container.owner = owner;

      const containerOwnerCollection: IContainerOwner[] = [{ id: '186188eb-decc-4646-bbb5-a9ad0b4b6bcd' }];
      jest.spyOn(containerOwnerService, 'query').mockReturnValue(of(new HttpResponse({ body: containerOwnerCollection })));
      const additionalContainerOwners = [owner];
      const expectedCollection: IContainerOwner[] = [...additionalContainerOwners, ...containerOwnerCollection];
      jest.spyOn(containerOwnerService, 'addContainerOwnerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(containerOwnerService.query).toHaveBeenCalled();
      expect(containerOwnerService.addContainerOwnerToCollectionIfMissing).toHaveBeenCalledWith(
        containerOwnerCollection,
        ...additionalContainerOwners.map(expect.objectContaining),
      );
      expect(comp.containerOwnersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const container: IContainer = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const pickupProvice: IProvice = { id: 'ae3f0e2e-dac8-408f-b4a1-dbbe64b1895c' };
      container.pickupProvice = pickupProvice;
      const dropoffProvice: IProvice = { id: '557e2012-fb5e-471f-8a86-272ab6f655da' };
      container.dropoffProvice = dropoffProvice;
      const pickupDistrict: IDistrict = { id: '26855a38-e708-4b85-bbfc-c975bc0cc144' };
      container.pickupDistrict = pickupDistrict;
      const dropoffDistrict: IDistrict = { id: 'd90741a7-a6ad-4819-8d52-ee1a80e08e6b' };
      container.dropoffDistrict = dropoffDistrict;
      const pickupWard: IWard = { id: '8ef87ba6-2f28-45ad-94b0-03059c0bc493' };
      container.pickupWard = pickupWard;
      const dropoffWard: IWard = { id: '423c8e27-9748-4721-b47a-7db08b9a68d2' };
      container.dropoffWard = dropoffWard;
      const type: IContainerType = { id: 'bb9f6dec-7511-4813-90e1-18dc8d35faac' };
      container.type = type;
      const status: IContainerStatus = { id: '671e4f35-290d-4d66-b091-29cb59702117' };
      container.status = status;
      const truckType: ITruckType = { id: '5d95e6b6-4798-47a9-84ab-811efea40b38' };
      container.truckType = truckType;
      const truck: ITruck = { id: 'f2682cea-040c-4b37-a435-b67412f71175' };
      container.truck = truck;
      const owner: IContainerOwner = { id: '308a2d43-326d-49f9-9cef-9837fb1bea91' };
      container.owner = owner;

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(comp.provicesSharedCollection).toContain(pickupProvice);
      expect(comp.provicesSharedCollection).toContain(dropoffProvice);
      expect(comp.districtsSharedCollection).toContain(pickupDistrict);
      expect(comp.districtsSharedCollection).toContain(dropoffDistrict);
      expect(comp.wardsSharedCollection).toContain(pickupWard);
      expect(comp.wardsSharedCollection).toContain(dropoffWard);
      expect(comp.containerTypesSharedCollection).toContain(type);
      expect(comp.containerStatusesSharedCollection).toContain(status);
      expect(comp.truckTypesSharedCollection).toContain(truckType);
      expect(comp.trucksSharedCollection).toContain(truck);
      expect(comp.containerOwnersSharedCollection).toContain(owner);
      expect(comp.container).toEqual(container);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainer>>();
      const container = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerFormService, 'getContainer').mockReturnValue(container);
      jest.spyOn(containerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ container });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: container }));
      saveSubject.complete();

      // THEN
      expect(containerFormService.getContainer).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(containerService.update).toHaveBeenCalledWith(expect.objectContaining(container));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainer>>();
      const container = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerFormService, 'getContainer').mockReturnValue({ id: null });
      jest.spyOn(containerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ container: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: container }));
      saveSubject.complete();

      // THEN
      expect(containerFormService.getContainer).toHaveBeenCalled();
      expect(containerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContainer>>();
      const container = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
      jest.spyOn(containerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ container });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(containerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProvice', () => {
      it('Should forward to proviceService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(proviceService, 'compareProvice');
        comp.compareProvice(entity, entity2);
        expect(proviceService.compareProvice).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDistrict', () => {
      it('Should forward to districtService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(districtService, 'compareDistrict');
        comp.compareDistrict(entity, entity2);
        expect(districtService.compareDistrict).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareWard', () => {
      it('Should forward to wardService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(wardService, 'compareWard');
        comp.compareWard(entity, entity2);
        expect(wardService.compareWard).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContainerType', () => {
      it('Should forward to containerTypeService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(containerTypeService, 'compareContainerType');
        comp.compareContainerType(entity, entity2);
        expect(containerTypeService.compareContainerType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContainerStatus', () => {
      it('Should forward to containerStatusService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(containerStatusService, 'compareContainerStatus');
        comp.compareContainerStatus(entity, entity2);
        expect(containerStatusService.compareContainerStatus).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTruckType', () => {
      it('Should forward to truckTypeService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(truckTypeService, 'compareTruckType');
        comp.compareTruckType(entity, entity2);
        expect(truckTypeService.compareTruckType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTruck', () => {
      it('Should forward to truckService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(truckService, 'compareTruck');
        comp.compareTruck(entity, entity2);
        expect(truckService.compareTruck).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContainerOwner', () => {
      it('Should forward to containerOwnerService', () => {
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(containerOwnerService, 'compareContainerOwner');
        comp.compareContainerOwner(entity, entity2);
        expect(containerOwnerService.compareContainerOwner).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
