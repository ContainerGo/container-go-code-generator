import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
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
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ContainerUpdateComponent],
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
      const container: IContainer = { id: 456 };
      const dropoffProvice: IProvice = { id: 31109 };
      container.dropoffProvice = dropoffProvice;

      const proviceCollection: IProvice[] = [{ id: 18832 }];
      jest.spyOn(proviceService, 'query').mockReturnValue(of(new HttpResponse({ body: proviceCollection })));
      const additionalProvices = [dropoffProvice];
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
      const container: IContainer = { id: 456 };
      const dropoffDistrict: IDistrict = { id: 17218 };
      container.dropoffDistrict = dropoffDistrict;

      const districtCollection: IDistrict[] = [{ id: 10852 }];
      jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
      const additionalDistricts = [dropoffDistrict];
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
      const container: IContainer = { id: 456 };
      const dropoffWard: IWard = { id: 18751 };
      container.dropoffWard = dropoffWard;

      const wardCollection: IWard[] = [{ id: 26361 }];
      jest.spyOn(wardService, 'query').mockReturnValue(of(new HttpResponse({ body: wardCollection })));
      const additionalWards = [dropoffWard];
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
      const container: IContainer = { id: 456 };
      const type: IContainerType = { id: 30408 };
      container.type = type;

      const containerTypeCollection: IContainerType[] = [{ id: 6362 }];
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
      const container: IContainer = { id: 456 };
      const status: IContainerStatus = { id: 2483 };
      container.status = status;

      const containerStatusCollection: IContainerStatus[] = [{ id: 3282 }];
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
      const container: IContainer = { id: 456 };
      const truckType: ITruckType = { id: 29400 };
      container.truckType = truckType;

      const truckTypeCollection: ITruckType[] = [{ id: 7141 }];
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
      const container: IContainer = { id: 456 };
      const truck: ITruck = { id: 17028 };
      container.truck = truck;

      const truckCollection: ITruck[] = [{ id: 19938 }];
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
      const container: IContainer = { id: 456 };
      const owner: IContainerOwner = { id: 23224 };
      container.owner = owner;

      const containerOwnerCollection: IContainerOwner[] = [{ id: 10978 }];
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
      const container: IContainer = { id: 456 };
      const dropoffProvice: IProvice = { id: 18345 };
      container.dropoffProvice = dropoffProvice;
      const dropoffDistrict: IDistrict = { id: 19127 };
      container.dropoffDistrict = dropoffDistrict;
      const dropoffWard: IWard = { id: 20162 };
      container.dropoffWard = dropoffWard;
      const type: IContainerType = { id: 224 };
      container.type = type;
      const status: IContainerStatus = { id: 14853 };
      container.status = status;
      const truckType: ITruckType = { id: 667 };
      container.truckType = truckType;
      const truck: ITruck = { id: 25962 };
      container.truck = truck;
      const owner: IContainerOwner = { id: 10262 };
      container.owner = owner;

      activatedRoute.data = of({ container });
      comp.ngOnInit();

      expect(comp.provicesSharedCollection).toContain(dropoffProvice);
      expect(comp.districtsSharedCollection).toContain(dropoffDistrict);
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
      const container = { id: 123 };
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
      const container = { id: 123 };
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
      const container = { id: 123 };
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
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(proviceService, 'compareProvice');
        comp.compareProvice(entity, entity2);
        expect(proviceService.compareProvice).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDistrict', () => {
      it('Should forward to districtService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(districtService, 'compareDistrict');
        comp.compareDistrict(entity, entity2);
        expect(districtService.compareDistrict).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareWard', () => {
      it('Should forward to wardService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(wardService, 'compareWard');
        comp.compareWard(entity, entity2);
        expect(wardService.compareWard).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContainerType', () => {
      it('Should forward to containerTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(containerTypeService, 'compareContainerType');
        comp.compareContainerType(entity, entity2);
        expect(containerTypeService.compareContainerType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContainerStatus', () => {
      it('Should forward to containerStatusService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(containerStatusService, 'compareContainerStatus');
        comp.compareContainerStatus(entity, entity2);
        expect(containerStatusService.compareContainerStatus).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTruckType', () => {
      it('Should forward to truckTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(truckTypeService, 'compareTruckType');
        comp.compareTruckType(entity, entity2);
        expect(truckTypeService.compareTruckType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTruck', () => {
      it('Should forward to truckService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(truckService, 'compareTruck');
        comp.compareTruck(entity, entity2);
        expect(truckService.compareTruck).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareContainerOwner', () => {
      it('Should forward to containerOwnerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(containerOwnerService, 'compareContainerOwner');
        comp.compareContainerOwner(entity, entity2);
        expect(containerOwnerService.compareContainerOwner).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
