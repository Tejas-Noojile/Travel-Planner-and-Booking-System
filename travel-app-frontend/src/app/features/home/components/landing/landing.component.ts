import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../user/services/user.service';
import { User } from '../../../../shared/models/user.model';
import { NavbarComponent } from '../../../../shared/components/navbar/navbar.component';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
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
