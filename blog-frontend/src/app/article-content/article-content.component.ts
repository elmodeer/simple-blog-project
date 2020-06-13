import { Component, OnInit, Input } from '@angular/core';
import { Article } from '../models/Article';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ARTICLES } from '../mock-articles';

@Component({
  selector: 'app-article-content',
  templateUrl: './article-content.component.html',
  styleUrls: ['./article-content.component.scss']
})
export class ArticleContentComponent implements OnInit {

  @Input() article: Article;
  
  constructor(private route: ActivatedRoute, private location: Location) { }

  ngOnInit(): void {
    this.getArticle();
  }

  getArticle(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.article = ARTICLES.find(a => a.id === id);
  }

  goBack(): void {
    this.location.back();
  }
}
