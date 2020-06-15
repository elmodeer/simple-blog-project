import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from 'src/app/models/Article';

const API_URL = 'http://localhost:8080/api/articles/';


@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) { }

  findArticleByUserName(userName: string): Observable<Article> {
    return this.http.get<Article>(API_URL + 'username/' + userName);
  }

  findArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(API_URL + 'id/' + id);
  }

  findAll(): Observable<Article[]> {
    return this.http.get<Article[]>(API_URL + 'all');
  }

  editPostImage(file: File, articleId: number): Observable<any> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('articleId', articleId.toString());

    return this.http.post(API_URL + 'editImage', formData);
  }
}
