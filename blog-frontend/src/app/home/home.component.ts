import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user/user.service';
import { Article } from '../models/Article';
import { ARTICLES } from '../mock-articles';
import { ArticleService } from '../_services/article/article.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  content: string;
  articles: Article[];


  constructor(private userService: UserService,
              private articleService: ArticleService) { }

  ngOnInit(): void {
    this.getArticles();

    // this.userService.getPublicContent().subscribe(
    //   data => {
    //     this.content = data;
    //   },
    //   err => {
    //     this.content = JSON.parse(err.error).message;
    //   }
    // );
  }

  getArticles(): void{
    this.articleService.findAll()
      .subscribe(articles => {
        this.articles = articles
      }),
      (err: any) => {
        console.log(err.error);
      }
  }
  
}
