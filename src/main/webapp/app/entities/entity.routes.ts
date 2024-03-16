import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'shipper',
    data: { pageTitle: 'containerGoServerApp.shipper.home.title' },
    loadChildren: () => import('./shipper/shipper.routes'),
  },
  {
    path: 'shipper-account',
    data: { pageTitle: 'containerGoServerApp.shipperAccount.home.title' },
    loadChildren: () => import('./shipper-account/shipper-account.routes'),
  },
  {
    path: 'carrier',
    data: { pageTitle: 'containerGoServerApp.carrier.home.title' },
    loadChildren: () => import('./carrier/carrier.routes'),
  },
  {
    path: 'carrier-account',
    data: { pageTitle: 'containerGoServerApp.carrierAccount.home.title' },
    loadChildren: () => import('./carrier-account/carrier-account.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
