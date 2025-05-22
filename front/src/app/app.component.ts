import { AsyncPipe } from '@angular/common';
import { Component, computed, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BehaviorSubject, map } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AsyncPipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  title = 'front';
  celsius = signal(25);
  fahrenheit = computed(() => this.celsius() * 1.8 + 32);

  doubleCelsius() {
    this.celsius.update((val) => val * 2);
  }
}
