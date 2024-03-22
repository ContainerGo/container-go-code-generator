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
  {
    path: 'shipper-person',
    data: { pageTitle: 'containerGoServerApp.shipperPerson.home.title' },
    loadChildren: () => import('./shipper-person/shipper-person.routes'),
  },
  {
    path: 'carrier-person',
    data: { pageTitle: 'containerGoServerApp.carrierPerson.home.title' },
    loadChildren: () => import('./carrier-person/carrier-person.routes'),
  },
  {
    path: 'center-person',
    data: { pageTitle: 'containerGoServerApp.centerPerson.home.title' },
    loadChildren: () => import('./center-person/center-person.routes'),
  },
  {
    path: 'center-person-group',
    data: { pageTitle: 'containerGoServerApp.centerPersonGroup.home.title' },
    loadChildren: () => import('./center-person-group/center-person-group.routes'),
  },
  {
    path: 'provice',
    data: { pageTitle: 'containerGoServerApp.provice.home.title' },
    loadChildren: () => import('./provice/provice.routes'),
  },
  {
    path: 'district',
    data: { pageTitle: 'containerGoServerApp.district.home.title' },
    loadChildren: () => import('./district/district.routes'),
  },
  {
    path: 'ward',
    data: { pageTitle: 'containerGoServerApp.ward.home.title' },
    loadChildren: () => import('./ward/ward.routes'),
  },
  {
    path: 'truck-type',
    data: { pageTitle: 'containerGoServerApp.truckType.home.title' },
    loadChildren: () => import('./truck-type/truck-type.routes'),
  },
  {
    path: 'truck',
    data: { pageTitle: 'containerGoServerApp.truck.home.title' },
    loadChildren: () => import('./truck/truck.routes'),
  },
  {
    path: 'container-status',
    data: { pageTitle: 'containerGoServerApp.containerStatus.home.title' },
    loadChildren: () => import('./container-status/container-status.routes'),
  },
  {
    path: 'container-status-group',
    data: { pageTitle: 'containerGoServerApp.containerStatusGroup.home.title' },
    loadChildren: () => import('./container-status-group/container-status-group.routes'),
  },
  {
    path: 'container-type',
    data: { pageTitle: 'containerGoServerApp.containerType.home.title' },
    loadChildren: () => import('./container-type/container-type.routes'),
  },
  {
    path: 'container',
    data: { pageTitle: 'containerGoServerApp.container.home.title' },
    loadChildren: () => import('./container/container.routes'),
  },
  {
    path: 'offer',
    data: { pageTitle: 'containerGoServerApp.offer.home.title' },
    loadChildren: () => import('./offer/offer.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
