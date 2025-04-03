import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguagesCreateComponent } from './languages-create.component';

describe('LanguagesCreateComponent', () => {
  let component: LanguagesCreateComponent;
  let fixture: ComponentFixture<LanguagesCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LanguagesCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguagesCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
