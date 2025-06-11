import { Component, Input } from '@angular/core';
import { Quiz } from '../../../core/models/quiz.interface';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-quizz',
  imports: [MatCardModule, RouterLink],
  templateUrl: './quizz.component.html',
  styleUrl: './quizz.component.scss'
})
export class QuizzComponent {
  @Input() quiz!: Quiz;

}
