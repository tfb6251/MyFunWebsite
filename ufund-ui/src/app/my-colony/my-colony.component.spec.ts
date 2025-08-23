import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyColonyComponent } from './my-colony.component';

describe('MyColonyComponent', () => {
  let component: MyColonyComponent;
  let fixture: ComponentFixture<MyColonyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyColonyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyColonyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
