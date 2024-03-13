import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CarrierAccountDetailComponent } from './carrier-account-detail.component';

describe('CarrierAccount Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarrierAccountDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
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

  describe('OnInit', () => {
    it('Should load carrierAccount on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CarrierAccountDetailComponent);

      // THEN
      expect(instance.carrierAccount).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
