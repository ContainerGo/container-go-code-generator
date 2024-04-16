import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CarrierAccountDetailComponent } from './carrier-account-detail.component';

describe('CarrierAccount Management Detail Component', () => {
  let comp: CarrierAccountDetailComponent;
  let fixture: ComponentFixture<CarrierAccountDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarrierAccountDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CarrierAccountDetailComponent,
              resolve: { carrierAccount: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CarrierAccountDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarrierAccountDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load carrierAccount on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CarrierAccountDetailComponent);

      // THEN
      expect(instance.carrierAccount()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
