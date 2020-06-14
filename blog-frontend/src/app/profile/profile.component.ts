import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user/user.service';
import { User } from '../models/User';
import { TokenStorageService } from '../_services/token-storage/token-storage.service';
import { Address } from '../models/Address';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User;

  constructor(private userService: UserService,
              private tokeService: TokenStorageService) { 
    // this.user = new User();
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
    // console.log(this.user);

  }

  reloadPage() {
    window.location.reload();
  }
}
