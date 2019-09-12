import { Component, OnInit } from '@angular/core';
import { IAmortizationSchedule } from 'app/preps/model/amortization-schedule';

@Component({
  selector: 'gha-amortization-schedule',
  templateUrl: './amortization-schedule.component.html',
  styleUrls: ['./amortization-schedule.component.scss']
})
export class AmortizationScheduleComponent implements OnInit {
  amortizationScheduleArray: IAmortizationSchedule[];
  constructor() {}

  ngOnInit() {}
}
