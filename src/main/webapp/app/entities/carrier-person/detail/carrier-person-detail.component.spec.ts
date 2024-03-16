import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CarrierPersonDetailComponent } from './carrier-person-detail.component';

describe('CarrierPerson Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarrierPersonDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CarrierPersonDetailComponent,
              resolve: { carrierPerson: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CarrierPersonDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load carrierPerson on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CarrierPersonDetailComponent);

      // THEN
      expect(instance.carrierPerson).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
