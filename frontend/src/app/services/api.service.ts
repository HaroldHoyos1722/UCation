import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private url = 'http://localhost:8080/rest/v1.0/ucation/api';

  constructor(private http: HttpClient) { }

  getHello() {
    return this.http.get(this.url + '/hello', { responseType: 'text' as const });
  }
}
