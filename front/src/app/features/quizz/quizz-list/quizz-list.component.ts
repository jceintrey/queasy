import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subject, takeUntil } from 'rxjs';
import { Quiz } from '../../../core/models/quiz.interface';
import { QuizzService } from '../../../core/services/quiz.service';
import { AsyncPipe } from '@angular/common';
import { QuizzComponent } from "../quizz/quizz.component";

@Component({
  selector: 'app-quizz-list',
  imports: [AsyncPipe, QuizzComponent],
  templateUrl: './quizz-list.component.html',
  styleUrl: './quizz-list.component.scss'
})
export class QuizzListComponent implements OnInit, OnDestroy {

  private destroy$ = new Subject<void>;
  quizzes$!: Observable<Quiz[]>;

  constructor(private quizService: QuizzService) { }

  ngOnInit(): void {

    this.quizzes$ = this.quizService.getAllQuizzes().pipe(takeUntil(this.destroy$));

  }

  /**
 * Cleans up any active subscriptions to prevent memory leaks.
 */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

} {






}
