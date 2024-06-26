import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
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
      imports: [HttpClientTestingModule, ShipperAccountUpdateComponent],
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
      const shipperAccount: IShipperAccount = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const shipper: IShipper = { id: '7e3d4a7e-c583-432d-bf44-ed5f03d49989' };
      shipperAccount.shipper = shipper;

      const shipperCollection: IShipper[] = [{ id: 'd803220c-378c-4c43-99a5-19acc6a5b9b1' }];
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
      const shipperAccount: IShipperAccount = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
      const shipper: IShipper = { id: '0bb58ee7-453f-46aa-9367-0b03b1070ec0' };
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
      const shipperAccount = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const shipperAccount = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
      const shipperAccount = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
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
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        const entity2 = { id: '1361f429-3817-4123-8ee3-fdf8943310b2' };
        jest.spyOn(shipperService, 'compareShipper');
        comp.compareShipper(entity, entity2);
        expect(shipperService.compareShipper).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
