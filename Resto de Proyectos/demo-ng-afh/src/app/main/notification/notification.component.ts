import { Component } from '@angular/core';
import { I18nSelectPipe } from '@angular/common';
import { NotificationService } from 'src/app/common-services';


@Component({
  selector: 'app-notification',
  imports: [I18nSelectPipe],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  constructor(private vm: NotificationService) { }

  public get VM() { return this.vm; }

}
