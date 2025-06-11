import { Routes } from '@angular/router';
import { QuestionComponent } from './features/quizz/question/question.component';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login/login.component';
import { QuizzListComponent } from './features/quizz/quizz-list/quizz-list.component';

export const routes: Routes = [
  { path: '', component: QuizzListComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent, },

];
