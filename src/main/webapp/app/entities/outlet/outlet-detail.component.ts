import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOutlet } from 'app/shared/model/outlet.model';

@Component({
  selector: 'jhi-outlet-detail',
  templateUrl: './outlet-detail.component.html',
})
export class OutletDetailComponent implements OnInit {
  outlet: IOutlet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ outlet }) => (this.outlet = outlet));
  }

  previousState(): void {
    window.history.back();
  }
}
