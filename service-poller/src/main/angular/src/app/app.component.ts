import { Component, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AppService } from './app.service';
import { takeUntil } from 'rxjs/operators';
import { Subject, Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {
	
	private subscription: Subscription;

  constructor(private appService: AppService) {}

  title = 'service-poller';

  serviceForm = new FormGroup({
    name: new FormControl('', Validators.required),
    url: new FormControl('', Validators.required),
  });

  services: any[] = [];
  serviceCount = 0;

  destroy$: Subject<boolean> = new Subject<boolean>();

  onSubmit() {
    this.appService.addService(this.serviceForm.value).pipe(takeUntil(this.destroy$)).subscribe(data => {
      console.log('message::::', data);
      this.serviceForm.reset();
			this.getAllServices();
    });
  }

  getAllServices() {
    this.appService.getServices().pipe(takeUntil(this.destroy$)).subscribe((services: any ) => {
		this.serviceCount = services.length;
        this.services = services;
    });
  }

	deleteService(name : string) {
		this.appService.deleteService(name).subscribe(date => {
		this.getAllServices();
	});
 }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit() {
	this.getAllServices();
	this.subscription = interval(5000).subscribe(
        (val) => { this.getAllServices()
      }
  );
}
}
