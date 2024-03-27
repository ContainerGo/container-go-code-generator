import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContainerOwnerDetailComponent } from './container-owner-detail.component';

describe('ContainerOwner Management Detail Component', () => {
  let comp: ContainerOwnerDetailComponent;
  let fixture: ComponentFixture<ContainerOwnerDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContainerOwnerDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContainerOwnerDetailComponent,
              resolve: { containerOwner: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContainerOwnerDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContainerOwnerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load containerOwner on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContainerOwnerDetailComponent);

      // THEN
      expect(instance.containerOwner).toEqual(expect.objectContaining({ id: 123 }));
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
