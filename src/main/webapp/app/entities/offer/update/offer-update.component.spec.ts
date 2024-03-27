import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IContainer } from 'app/entities/container/container.model';
import { ContainerService } from 'app/entities/container/service/container.service';
import { OfferService } from '../service/offer.service';
import { IOffer } from '../offer.model';
import { OfferFormService } from './offer-form.service';

import { OfferUpdateComponent } from './offer-update.component';

describe('Offer Management Update Component', () => {
  let comp: OfferUpdateComponent;
  let fixture: ComponentFixture<OfferUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let offerFormService: OfferFormService;
  let offerService: OfferService;
  let containerService: ContainerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), OfferUpdateComponent],
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
      .overrideTemplate(OfferUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OfferUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    offerFormService = TestBed.inject(OfferFormService);
    offerService = TestBed.inject(OfferService);
    containerService = TestBed.inject(ContainerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Container query and add missing value', () => {
      const offer: IOffer = { id: 456 };
      const container: IContainer = { id: 10522 };
      offer.container = container;

      const containerCollection: IContainer[] = [{ id: 27681 }];
      jest.spyOn(containerService, 'query').mockReturnValue(of(new HttpResponse({ body: containerCollection })));
      const additionalContainers = [container];
      const expectedCollection: IContainer[] = [...additionalContainers, ...containerCollection];
      jest.spyOn(containerService, 'addContainerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ offer });
      comp.ngOnInit();

      expect(containerService.query).toHaveBeenCalled();
      expect(containerService.addContainerToCollectionIfMissing).toHaveBeenCalledWith(
        containerCollection,
        ...additionalContainers.map(expect.objectContaining),
      );
      expect(comp.containersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const offer: IOffer = { id: 456 };
      const container: IContainer = { id: 22615 };
      offer.container = container;

      activatedRoute.data = of({ offer });
      comp.ngOnInit();

      expect(comp.containersSharedCollection).toContain(container);
      expect(comp.offer).toEqual(offer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOffer>>();
      const offer = { id: 123 };
      jest.spyOn(offerFormService, 'getOffer').mockReturnValue(offer);
      jest.spyOn(offerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ offer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: offer }));
      saveSubject.complete();

      // THEN
      expect(offerFormService.getOffer).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(offerService.update).toHaveBeenCalledWith(expect.objectContaining(offer));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOffer>>();
      const offer = { id: 123 };
      jest.spyOn(offerFormService, 'getOffer').mockReturnValue({ id: null });
      jest.spyOn(offerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ offer: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: offer }));
      saveSubject.complete();

      // THEN
      expect(offerFormService.getOffer).toHaveBeenCalled();
      expect(offerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOffer>>();
      const offer = { id: 123 };
      jest.spyOn(offerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ offer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(offerService.update).toHaveBeenCalled();
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
