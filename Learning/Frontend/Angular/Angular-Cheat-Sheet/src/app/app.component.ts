import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component'; // Import the HeaderComponent

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent], //imported Components too
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent { }
