import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { User } from '../../../../shared/models/user.model';
import { ToastService } from '../../../../shared/services/toast.service';
import { Observable } from 'rxjs';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  users: User[] = [];
  userToDelete: User | null = null; // For the confirmation modal

  constructor(
    private adminService: AdminService,
    private toastService: ToastService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.adminService.getAllUsers().subscribe((users) => {
      this.users = users;
    });
  }

  // Set the user to be deleted and show the modal
  confirmDelete(user: User): void {
    this.userToDelete = user;
  }

  // Close the modal without taking action
  cancelDelete(): void {
    this.userToDelete = null;
  }

  // Proceed with the deletion
  deleteUser(): void {
    if (!this.userToDelete) return;

    this.adminService.deleteUser(this.userToDelete.userId).subscribe({
      next: () => {
        // Remove the user from the local array for an instant UI update
        this.users = this.users.filter(
          (u) => u.userId !== this.userToDelete?.userId
        );
        this.toastService.show('User deleted successfully', 'success');
        this.userToDelete = null; // Close the modal
      },
      error: (err) => {
        this.toastService.show('Failed to delete user', 'error');
        this.userToDelete = null; // Close the modal
      },
    });
  }

  logout(): void {
    this.authService.logout();
  }
}
