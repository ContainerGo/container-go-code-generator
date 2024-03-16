import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IShipper } from 'app/entities/shipper/shipper.model';
import { ShipperService } from 'app/entities/shipper/service/shipper.service';
import { ShipperAccountService } from '../service/shipper-account.service';
import { IShipperAccount } from '../shipper-account.model';
import { ShipperAccountFormService } from './shipper-account-form.service';

import { ShipperAccountUpdateComponent } from './shipper-account-update.component';

describe('ShipperAccount Management Update Component', () => {
  let comp: ShipperAccountUpdateComponent;
  let fixture: ComponentFixture<ShipperAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipperAccountFormService: ShipperAccountFormService;
  let shipperAccountService: ShipperAccountService;
  let shipperService: ShipperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ShipperAccountUpdateComponent],
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
      .overrideTemplate(ShipperAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipperAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipperAccountFormService = TestBed.inject(ShipperAccountFormService);
    shipperAccountService = TestBed.inject(ShipperAccountService);
    shipperService = TestBed.inject(ShipperService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Shipper query and add missing value', () => {
      const shipperAccount: IShipperAccount = { id: 456 };
      const shipper: IShipper = { id: 32201 };
      shipperAccount.shipper = shipper;

      const shipperCollection: IShipper[] = [{ id: 21775 }];
      jest.spyOn(shipperService, 'query').mockReturnValue(of(new HttpResponse({ body: shipperCollection })));
      const additionalShippers = [shipper];
      const expectedCollection: IShipper[] = [...additionalShippers, ...shipperCollection];
      jest.spyOn(shipperService, 'addShipperToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipperAccount });
      comp.ngOnInit();

      expect(shipperService.query).toHaveBeenCalled();
      expect(shipperService.addShipperToCollectionIfMissing).toHaveBeenCalledWith(
        shipperCollection,
        ...additionalShippers.map(expect.objectContaining),
      );
      expect(comp.shippersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shipperAccount: IShipperAccount = { id: 456 };
      const shipper: IShipper = { id: 23285 };
      shipperAccount.shipper = shipper;

      activatedRoute.data = of({ shipperAccount });
      comp.ngOnInit();

      expect(comp.shippersSharedCollection).toContain(shipper);
      expect(comp.shipperAccount).toEqual(shipperAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperAccount>>();
      const shipperAccount = { id: 123 };
      jest.spyOn(shipperAccountFormService, 'getShipperAccount').mockReturnValue(shipperAccount);
      jest.spyOn(shipperAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperAccount }));
      saveSubject.complete();

      // THEN
      expect(shipperAccountFormService.getShipperAccount).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipperAccountService.update).toHaveBeenCalledWith(expect.objectContaining(shipperAccount));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperAccount>>();
      const shipperAccount = { id: 123 };
      jest.spyOn(shipperAccountFormService, 'getShipperAccount').mockReturnValue({ id: null });
      jest.spyOn(shipperAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperAccount: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipperAccount }));
      saveSubject.complete();

      // THEN
      expect(shipperAccountFormService.getShipperAccount).toHaveBeenCalled();
      expect(shipperAccountService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipperAccount>>();
      const shipperAccount = { id: 123 };
      jest.spyOn(shipperAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipperAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipperAccountService.update).toHaveBeenCalled();
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
