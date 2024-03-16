import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CarrierDetailComponent } from './carrier-detail.component';

describe('Carrier Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarrierDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CarrierDetailComponent,
              resolve: { carrier: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CarrierDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load carrier on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CarrierDetailComponent);

      // THEN
      expect(instance.carrier).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});