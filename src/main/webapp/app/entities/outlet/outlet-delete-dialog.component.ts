import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOutlet } from 'app/shared/model/outlet.model';
import { OutletService } from './outlet.service';

@Component({
  templateUrl: './outlet-delete-dialog.component.html',
})
export class OutletDeleteDialogComponent {
  outlet?: IOutlet;

  constructor(protected outletService: OutletService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.outletService.delete(id).subscribe(() => {
      this.eventManager.broadcast('outletListModification');
      this.activeModal.close();
    });
  }
}
