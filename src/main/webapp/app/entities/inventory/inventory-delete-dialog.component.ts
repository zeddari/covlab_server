import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';

@Component({
  templateUrl: './inventory-delete-dialog.component.html',
})
export class InventoryDeleteDialogComponent {
  inventory?: IInventory;

  constructor(protected inventoryService: InventoryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryListModification');
      this.activeModal.close();
    });
  }
}
