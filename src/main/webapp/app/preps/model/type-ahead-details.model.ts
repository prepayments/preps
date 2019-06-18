/**
 * This small class is used to implement type ahead for a field with options of
 * type T
 */
export interface ITypeAheadDetails<T> {
  fieldLabel?: string;
  placeHolder?: string;
  fieldOptions?: T[];
}

export class TypeAheadDetails<T> implements ITypeAheadDetails<T> {
  constructor(public fieldLabel?: string, public placeHolder?: string, public fieldOptions?: T[]) {}
}
