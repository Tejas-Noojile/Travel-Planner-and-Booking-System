import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ItineraryItem } from '../../../../shared/models/itinerary.model';
import { ToastService } from '../../../../shared/services/toast.service';
import { ItineraryService } from '../../services/itinerary.service';

@Component({
  selector: 'app-itinerary-list',
  templateUrl: './itinerary-list.component.html',
  styleUrls: ['./itinerary-list.component.scss'],
})
export class ItineraryListComponent {
  @Input() items: ItineraryItem[] = [];
  @Input() selectedDay: number = 1;
  @Output() itemDeleted = new EventEmitter<string>();

  get itemsForSelectedDay(): ItineraryItem[] {
    return this.items
      .filter((item) => item.dayNumber === this.selectedDay)
      .sort((a, b) => a.time.localeCompare(b.time));
  }
  constructor(
    private itineraryService: ItineraryService,
    private toastService: ToastService
  ) {}

  onClickDelete(item: ItineraryItem): void {
    console.log('Deleting item:', item);
    if (!item.itemId) {
      this.toastService.show('Error: Item ID is missing', 'error');
      return;
    }

    if (confirm('Are you sure you want to delete this itinerary item?')) {
      this.itineraryService.deleteItineraryItem(item.itemId).subscribe({
        next: () => {
          // Emit the deleted item ID so parent can update its array
          this.itemDeleted.emit(item.itemId);
          this.toastService.show('Itinerary item deleted', 'success');
        },
        error: (error) => {
          console.error('Error deleting item:', error);
          this.toastService.show('Error deleting item', 'error');
        }
      });
    }
  }
}
