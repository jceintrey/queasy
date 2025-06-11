import { Question } from './question.interface';
export interface Quiz {
  id: number;
  title: String;
  valid: boolean;
  questions: Question[];
  validationMessage: String;

}
