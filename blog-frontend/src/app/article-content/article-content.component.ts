import { Component, OnInit, Input } from '@angular/core';
import { Article } from '../models/Article';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ARTICLES } from '../mock-articles';
import { HttpClient } from '@angular/common/http';
import { ArticleService } from '../_services/article/article.service';

@Component({
  selector: 'app-article-content',
  templateUrl: './article-content.component.html',
  styleUrls: ['./article-content.component.scss']
})
export class ArticleContentComponent implements OnInit {

  @Input() article: Article;
  lastUpdated: string;
  currentFile: File;

  constructor(private route: ActivatedRoute, private location: Location, 
              private articleService: ArticleService) { }

  ngOnInit(): void {
    // console.log(this.article);
    this.getArticle();
    this.formatLastUpdated()
  }

  getArticle(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.articleService.findArticleById(id)
        .subscribe(article => {
          this.article = article

        }),
        (err: any) => {
          console.log(err.error);
        }
  }

  // bad Hack
  selectFileAndUpload(event) {
    this.currentFile = event.target.files.item(0);
    this.articleService.editPostImage(this.currentFile, this.article.id)
        .subscribe(data => console.log(data));
    this.reloadPage();    
  }
  
  editImage(): void{
    // this.articleService.editPostImage();
  }

  // TODO!! maybe needed for better formating
  formatLastUpdated(): string {
    // 2020-06-14 07:29:54
    if(this.article) {
      let dayAndTime = this.article.createdAt.split(" ");
    }
     
    return "";
  }

  reloadPage() {
    window.location.reload();
  }

  goBack(): void {
    this.location.back();
  }
}
