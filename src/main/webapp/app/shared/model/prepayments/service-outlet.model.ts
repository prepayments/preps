export interface IServiceOutlet {
  id?: number;
  serviceOutletName?: string;
  serviceOutletCode?: string;
  serviceOutletLocation?: string;
  serviceOutletManager?: string;
  numberOfStaff?: number;
  building?: string;
  floor?: number;
  postalAddress?: string;
  contactPersonName?: string;
  contactEmail?: string;
  street?: string;
  OriginatingFileToken?: string;
}

export class ServiceOutlet implements IServiceOutlet {
  constructor(
    public id?: number,
    public serviceOutletName?: string,
    public serviceOutletCode?: string,
    public serviceOutletLocation?: string,
    public serviceOutletManager?: string,
    public numberOfStaff?: number,
    public building?: string,
    public floor?: number,
    public postalAddress?: string,
    public contactPersonName?: string,
    public contactEmail?: string,
    public street?: string,
    public OriginatingFileToken?: string
  ) {}
}
