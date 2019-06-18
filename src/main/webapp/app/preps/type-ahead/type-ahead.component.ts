import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ITypeAheadDetails } from 'app/preps/model/type-ahead-details.model';
import { FormControl, NgControl, NgModel } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'gha-type-ahead',
  templateUrl: './type-ahead.component.html',
  styles: []
})
export class TypeAheadComponent implements OnInit {
  matchingItems: any[];
  term = new FormControl();

  @Input() typeAheadDetails: ITypeAheadDetails<any>;
  @Output() onSelectItem = new EventEmitter<any>();

  constructor() {}

  ngOnInit() {
    this.term.valueChanges
      .pipe(distinctUntilChanged())
      .pipe(debounceTime(300))
      .subscribe((term: string) => {
        this.matchingItems = this.typeAheadDetails.fieldOptions.filter(
          sl =>
            sl
              .trim()
              .toLowerCase()
              .indexOf(term.toLowerCase()) > -1
        );
      });
  }

  selectItems(sl: any) {
    this.onSelectItem.emit(sl);
  }
}
