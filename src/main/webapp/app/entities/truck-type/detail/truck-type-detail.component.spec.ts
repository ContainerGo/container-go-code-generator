import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TruckTypeDetailComponent } from './truck-type-detail.component';

describe('TruckType Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TruckTypeDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TruckTypeDetailComponent,
              resolve: { truckType: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TruckTypeDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load truckType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TruckTypeDetailComponent);

      // THEN
      expect(instance.truckType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
