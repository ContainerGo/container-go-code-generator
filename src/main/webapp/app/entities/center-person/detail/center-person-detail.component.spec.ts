import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CenterPersonDetailComponent } from './center-person-detail.component';

describe('CenterPerson Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CenterPersonDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CenterPersonDetailComponent,
              resolve: { centerPerson: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CenterPersonDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load centerPerson on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CenterPersonDetailComponent);

      // THEN
      expect(instance.centerPerson).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
