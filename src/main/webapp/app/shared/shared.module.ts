import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PrepsSharedCommonModule, GhaLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PrepsSharedCommonModule],
  declarations: [GhaLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [GhaLoginModalComponent],
  exports: [PrepsSharedCommonModule, GhaLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrepsSharedModule {
  static forRoot() {
    return {
      ngModule: PrepsSharedModule
    };
  }
}
