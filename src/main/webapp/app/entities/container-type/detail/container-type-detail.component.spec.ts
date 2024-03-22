import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContainerTypeDetailComponent } from './container-type-detail.component';

describe('ContainerType Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContainerTypeDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContainerTypeDetailComponent,
              resolve: { containerType: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContainerTypeDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load containerType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContainerTypeDetailComponent);

      // THEN
      expect(instance.containerType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
