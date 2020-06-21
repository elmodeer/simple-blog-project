import { Component, OnInit, Input } from '@angular/core';
import { Article } from '../models/Article';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ARTICLES } from '../mock-articles';
import { HttpClient } from '@angular/common/http';
import { ArticleService } from '../_services/article/article.service';
import { AWSUtility } from '../_helpers/aws';

@Component({
  selector: 'app-article-content',
  templateUrl: './article-content.component.html',
  styleUrls: ['./article-content.component.scss']
})
export class ArticleContentComponent implements OnInit {

  @Input() article: Article;
  lastUpdated: string;
  currentFile: File;
  imageUrl: string;

  constructor(private route: ActivatedRoute,
    private location: Location,
    private articleService: ArticleService,
    private awsUtility: AWSUtility) { }

  ngOnInit(): void {
    this.getArticle();
  }

  getArticle(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.articleService.findArticleById(id)
      .subscribe(article => {
        this.article = article
        this.getImage();
      }),
      (err: any) => {
        console.log(err.error);
      }
  }

  // bad Hack, need fix
  async selectFileAndUpload(event) {
    // 1- generate a pre-signed url 
    this.currentFile = event.target.files.item(0);  
    this.articleService.generatePresignedPutUrl(this.currentFile)
        .subscribe(presignedPutUrl => {
          // 2- use the pre-signed url to upload the file to s3 
          this.awsUtility.uploadfileAWSS3(presignedPutUrl, this.currentFile)
              .subscribe(resp => {     
                console.log('resp: ' +  resp.message);
                                           
                if (resp.status === 200) {
                  // 3- update the article entity after successful upload
                  this.articleService.updateImageUrl(this.currentFile.name, this.article.id)
                      .subscribe(data => {
                        this.reloadPage();
                      });
                }
              }, (err: any) => {
                console.log(err.error);
              });
      }, (err: any) => {
        console.log(err.error);
      });
  }

  getImage(): void {
    this.articleService.generatePresignedGetUrl(this.article.id)
      .subscribe(imageUrl => {
        this.imageUrl = imageUrl;
      }),
      (err: any) => {
        console.log(err.error);
      };

  }


  // TODO!! maybe needed for better formating
  formatLastUpdated(): string {
    // 2020-06-14 07:29:54
    if (this.article) {
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
