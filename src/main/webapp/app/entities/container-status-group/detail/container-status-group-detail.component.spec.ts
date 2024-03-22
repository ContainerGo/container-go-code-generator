import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContainerStatusGroupDetailComponent } from './container-status-group-detail.component';

describe('ContainerStatusGroup Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContainerStatusGroupDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContainerStatusGroupDetailComponent,
              resolve: { containerStatusGroup: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContainerStatusGroupDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load containerStatusGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContainerStatusGroupDetailComponent);

      // THEN
      expect(instance.containerStatusGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
