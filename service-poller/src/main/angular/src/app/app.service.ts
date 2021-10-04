import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) { }

  rootURL = '/api';

  getServices() {
    return this.http.get(this.rootURL + '/services');
  }

  addService(service: any) {
	return this.http.post(this.rootURL + '/add', service);
  }

	deleteService(name: string) {
		return this.http.delete(this.rootURL + '/delete?'+'name='+name );
	}

}
