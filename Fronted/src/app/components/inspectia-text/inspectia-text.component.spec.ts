import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectiaTextComponent } from './inspectia-text.component';

describe('InspectiaTextComponent', () => {
  let component: InspectiaTextComponent;
  let fixture: ComponentFixture<InspectiaTextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InspectiaTextComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InspectiaTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
