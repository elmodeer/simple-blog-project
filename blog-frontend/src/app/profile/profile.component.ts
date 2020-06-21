import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user/user.service';
import { User } from '../models/User';
import { TokenStorageService } from '../_services/token-storage/token-storage.service';
import { Address } from '../models/Address';
import { AWSUtility } from '../_helpers/aws';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User;
  currentFile: File;
  imageUrl: string;

  constructor(private userService: UserService,
              private tokeService: TokenStorageService,
              private awsUtility: AWSUtility) { 
  }

  ngOnInit(): void {
    let userJson =  this.tokeService.getUser();
    this.userService.getUserById(+userJson.id)
      .subscribe(data => {
        this.user = data
        if (this.user.address == null) {
          this.user.address = {
            address: "",
            city: "",
            zipCode: null,
            id: null
          }
        }
        if (this.user.imageUrl){
          this.getImage();
        } else {
          this.imageUrl = "//ssl.gstatic.com/accounts/ui/avatar_2x.png";
        }
      }),
      err => {
        console.log(err);
      };
      // TODO!! somehow solve the exception this point due to the address problem
  }

  save(){
    this.userService.editUserDetails(this.user)
    .subscribe(date => {
      this.user = date
      this.reloadPage();
    }),
    err => {
      console.log(err);
    }
  }

  // bad Hack, need fix
  selectFileAndUpload(event) {
     // 1- generate a pre-signed url 
     this.currentFile = event.target.files.item(0);
     this.userService.generatePresignedPutUrl(this.currentFile)
         .subscribe(presignedPutUrl => {
           // 2- use the pre-signed url to upload the file to s3 
           this.awsUtility.uploadfileAWSS3(presignedPutUrl, this.currentFile)
               .subscribe(resp => {
                 if (resp.status === 200) {
                   // 3- update the user entity after successful upload
                   this.userService.updateImageUrl(this.currentFile.name, this.user.id)
                       .subscribe(data => {
                         this.reloadPage();
                       });
                 }
               });
       }, (err: any) => {
         console.log(err.error);
       });
   }

  getImage(): void{
    this.userService.generatePresignedGetUrl(this.user.id)
        .subscribe(imageUrl => {
          this.imageUrl = imageUrl;
        }),
        (err: any) => {
          console.log(err.error);
        };
  }

  reloadPage() {
    window.location.reload();
  }
}
