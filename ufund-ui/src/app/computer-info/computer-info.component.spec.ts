import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComputerInfoComponent } from './computer-info.component';

describe('ComputerInfoComponent', () => {
  let component: ComputerInfoComponent;
  let fixture: ComponentFixture<ComputerInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComputerInfoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComputerInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
