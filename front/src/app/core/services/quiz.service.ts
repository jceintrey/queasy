import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Question } from '../models/question.interface';
import { Answer } from '../models/answer.interface';
import { AnswerRequest } from '../models/answerRequest.interface';
import { Score } from '../models/score.interface';
import { Quiz } from '../models/quiz.interface';
import { filter, map, Observable } from 'rxjs';

/**
 *
 *
 * @export
 * @class QuizzService
 */
@Injectable({
  providedIn: 'root'
})

export class QuizzService {

  constructor(private http: HttpClient) { }

  private apiUrl = '/api/';

  /**
   * Retrieve the list of all avalaible quizzes
   * @returns
   */
  getAllQuizzes(): Observable<Quiz[]> {

    return this.http.get<Quiz[]>(`${this.apiUrl}/quizzes`, {
      withCredentials: true
    }).pipe(
      map(quizzes => quizzes.filter(quiz => quiz.valid == true))
    );

  }

  /**
   *
   * Get a question by its Id
   * @param {number} id
   * @return {{}}
   * @memberof QuestionService
   */
  getQuestion(id: number): Question {
    throw new Error("");
  }

  /**
   *
   * Get a quizz by its Id
   * @param {number} id
   * @memberof QuestionService
   */
  getQuizz(id: number): Question[] {
    throw new Error("");
  }

  /**
   *
   *
   * @param {AnswerRequest} responses
   * @return {Score}
   * @memberof QuestionService
   */
  submitAnswsers(responses: AnswerRequest): Score {
    throw new Error("");
  }


}
