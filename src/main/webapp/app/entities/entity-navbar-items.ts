import NavbarItem from 'app/layouts/navbar/navbar-item.model';

export const EntityNavbarItems: NavbarItem[] = [
  {
    name: 'Shipper',
    route: '/shipper',
    translationKey: 'global.menu.entities.shipper',
  },
  {
    name: 'ShipperAccount',
    route: '/shipper-account',
    translationKey: 'global.menu.entities.shipperAccount',
  },
  {
    name: 'Carrier',
    route: '/carrier',
    translationKey: 'global.menu.entities.carrier',
  },
  {
    name: 'CarrierAccount',
    route: '/carrier-account',
    translationKey: 'global.menu.entities.carrierAccount',
  },
  {
    name: 'ShipperPerson',
    route: '/shipper-person',
    translationKey: 'global.menu.entities.shipperPerson',
  },
  {
    name: 'CarrierPerson',
    route: '/carrier-person',
    translationKey: 'global.menu.entities.carrierPerson',
  },
];
