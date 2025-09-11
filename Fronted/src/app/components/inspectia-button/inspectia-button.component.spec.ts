import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectiaButtonComponent } from './inspectia-button.component';

describe('InspectiaButtonComponent', () => {
  let component: InspectiaButtonComponent;
  let fixture: ComponentFixture<InspectiaButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InspectiaButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InspectiaButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
