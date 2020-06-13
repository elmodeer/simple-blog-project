import { Component, OnInit, Input } from '@angular/core';
import { Article } from '../models/Article';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {


  // @Input articleTitle: string;
  @Input() article: Article;

  constructor() { }

  ngOnInit(): void {
    // this.articleTitle = "Article Title"
  }

  bookmark() {
    alert('bookmarked.');
  }
}
