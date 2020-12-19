import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusPO } from 'app/shared/model/status-po.model';

@Component({
  selector: 'jhi-status-po-detail',
  templateUrl: './status-po-detail.component.html',
})
export class StatusPODetailComponent implements OnInit {
  statusPO: IStatusPO | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusPO }) => (this.statusPO = statusPO));
  }

  previousState(): void {
    window.history.back();
  }
}
