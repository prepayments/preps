import { NgModule } from '@angular/core';

import { PrepsSharedLibsModule, GhaAlertComponent, GhaAlertErrorComponent } from './';

@NgModule({
  imports: [PrepsSharedLibsModule],
  declarations: [GhaAlertComponent, GhaAlertErrorComponent],
  exports: [PrepsSharedLibsModule, GhaAlertComponent, GhaAlertErrorComponent]
})
export class PrepsSharedCommonModule {}
