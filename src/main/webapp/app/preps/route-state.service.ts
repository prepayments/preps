import { Injectable } from '@angular/core';

/**
 * Very mundane yet useful for sending route state data to the component
 * we are navigating to even though the component does not have a parent
 * child relationship with the originator of data
 * The dservice is generic and transmits data of type T
 */
@Injectable({
  providedIn: 'root'
})
export class RouteStateService<T> {
  data: T;

  constructor() {}
}
