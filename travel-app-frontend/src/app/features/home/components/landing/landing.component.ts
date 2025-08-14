import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../user/services/user.service';
import { User } from '../../../../shared/models/user.model';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.scss'
})
export class LandingComponent implements OnInit{
  user: User | null = null;
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getProfile().subscribe(
      (user) => {
        this.user = user;
      }
    );

  }
}
