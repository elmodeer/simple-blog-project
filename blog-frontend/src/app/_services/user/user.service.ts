import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../models/User';
import { API_BASE_URL } from 'src/app/_helpers/auth.interceptor';

// const API_URL = 'http://localhost:8080/api/users/';
const API_URL = API_BASE_URL + 'api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(API_URL + id);
  }

  // PUT = Create and POST = Update
  editUserDetails(user: User): Observable<any> {
    let result: Observable<Object>;
    result = this.http.post(API_URL + 'edit', user);
    return result;
  }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  updateImageUrl(fileName: string, userId: number){
    const formData: FormData = new FormData();

    formData.append('fileName', fileName);
    formData.append('userId', userId.toString());
    return this.http.post(API_URL + 'updateImage', formData);
  }

  generatePresignedPutUrl(file: File): Observable<any> {
    return this.http.get(API_URL + 'putImage/' + file.name, { responseType: 'text' });
  }

  generatePresignedGetUrl(userId: number): Observable<any>{
    return this.http.get(API_URL + 'getImage/' + userId, { responseType: 'text' });
  }
}
