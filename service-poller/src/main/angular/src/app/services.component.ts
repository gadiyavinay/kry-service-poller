import { Component, OnInit, Input } from '@angular/core';
import { AppService } from './app.service';
import { AppComponent } from './app.component';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  constructor(private appService: AppService, private appComponent: AppComponent) { }

  @Input() services: any;

  ngOnInit(): void {
  }

deleteService(name : string) {
		this.appService.deleteService(name).subscribe(date => {
		this.appComponent.getAllServices();
	});
 }
}
