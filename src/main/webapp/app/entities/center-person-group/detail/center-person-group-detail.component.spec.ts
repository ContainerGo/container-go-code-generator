import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CenterPersonGroupDetailComponent } from './center-person-group-detail.component';

describe('CenterPersonGroup Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CenterPersonGroupDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CenterPersonGroupDetailComponent,
              resolve: { centerPersonGroup: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CenterPersonGroupDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load centerPersonGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CenterPersonGroupDetailComponent);

      // THEN
      expect(instance.centerPersonGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
