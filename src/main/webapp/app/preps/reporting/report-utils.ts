import { throwError } from 'rxjs/internal/observable/throwError';

export class ReportUtils {
  public static probablyUnregisteredReport(error) {
    window.history.back();

    return throwError(`Could not retrieve report probably due to unregistered report type error. Navigating back...`);
  }
}
