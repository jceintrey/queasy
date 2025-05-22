import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzListComponent } from './quizz-list.component';

describe('QuizzListComponent', () => {
  let component: QuizzListComponent;
  let fixture: ComponentFixture<QuizzListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuizzListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuizzListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
