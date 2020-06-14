import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from 'src/app/models/Article';
import { ArticleContentComponent } from 'src/app/article-content/article-content.component';




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
    return this.http.get<Article>(API_URL + 'id/' +id);
  }

  findAll(): Observable<Article[]> {
    return this.http.get<Article[]>(API_URL + 'all');
  }

}
