import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DispTelemComponent } from './disp-telem.component';

describe('DispTelemComponent', () => {
  let component: DispTelemComponent;
  let fixture: ComponentFixture<DispTelemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DispTelemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DispTelemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
