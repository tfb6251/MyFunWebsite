import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComputerBasketComponent } from './computer-basket.component';

describe('ComputerBasketComponent', () => {
  let component: ComputerBasketComponent;
  let fixture: ComponentFixture<ComputerBasketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComputerBasketComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComputerBasketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
