import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DistrictDetailComponent } from './district-detail.component';

describe('District Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DistrictDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DistrictDetailComponent,
              resolve: { district: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DistrictDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load district on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DistrictDetailComponent);

      // THEN
      expect(instance.district).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
