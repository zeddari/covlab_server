import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'inventory',
        loadChildren: () => import('./inventory/inventory.module').then(m => m.CovlabServerInventoryModule),
      },
      {
        path: 'outlet',
        loadChildren: () => import('./outlet/outlet.module').then(m => m.CovlabServerOutletModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.CovlabServerProductModule),
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.CovlabServerCategoryModule),
      },
      {
        path: 'purchase-order',
        loadChildren: () => import('./purchase-order/purchase-order.module').then(m => m.CovlabServerPurchaseOrderModule),
      },
      {
        path: 'status-po',
        loadChildren: () => import('./status-po/status-po.module').then(m => m.CovlabServerStatusPOModule),
      },
      {
        path: 'tickets',
        loadChildren: () => import('./tickets/tickets.module').then(m => m.CovlabServerTicketsModule),
      },
      {
        path: 'po-status',
        loadChildren: () => import('./po-status/po-status.module').then(m => m.CovlabServerPoStatusModule),
      },
      {
        path: 'device-overview-stats',
        loadChildren: () =>
          import('./device-overview-stats/device-overview-stats.module').then(m => m.CovlabServerDeviceOverviewStatsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CovlabServerEntityModule {}
