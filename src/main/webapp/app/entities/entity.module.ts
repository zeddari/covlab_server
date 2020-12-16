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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CovlabServerEntityModule {}
