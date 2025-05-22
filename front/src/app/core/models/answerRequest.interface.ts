import { Answer } from "./answer.interface";

export interface AnswerRequest {
  quizzId: number,
  questionId: number,
  selectedResponses: Answer[]
}
