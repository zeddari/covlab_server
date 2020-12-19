import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoStatus } from 'app/shared/model/po-status.model';

@Component({
  selector: 'jhi-po-status-detail',
  templateUrl: './po-status-detail.component.html',
})
export class PoStatusDetailComponent implements OnInit {
  poStatus: IPoStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poStatus }) => (this.poStatus = poStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
