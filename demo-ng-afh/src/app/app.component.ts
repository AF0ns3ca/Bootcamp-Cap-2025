import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoggerService } from '@my/core';
// import { NotificationComponent } from './main/notification/notification.component';
import { DemosComponent } from './demos/demos.component';
import { NotificationModalComponent } from './main';

@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, NotificationModalComponent, DemosComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'demo-ng-afh';

  constructor(out: LoggerService) {
    out.error('Es un error')
    out.warn('Es un warn')
    out.info('Es un info')
    out.log('Es un log')
  }
}
