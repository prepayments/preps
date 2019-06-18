/**
 * This small class is used to implement type ahead for a field with options of
 * type T
 */
export interface ITypeAheadDetails<T> {
  fieldLabel?: string;
  fieldOptions?: T[];
}

export class TypeAheadDetails<T> {
  constructor(public fieldLabel?: string, public fieldOptions?: T[]) {}
}
