import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Question } from './models/question.interface';
import { Answer } from './models/answer.interface';
import { AnswerRequest } from './models/answerRequest.interface';
import { Score } from './models/score.interface';

/**
 *
 *
 * @export
 * @class QuestionService
 */
@Injectable({
  providedIn: 'root'
})

export class QuestionService {

  constructor(httpClient: HttpClient) { }


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
