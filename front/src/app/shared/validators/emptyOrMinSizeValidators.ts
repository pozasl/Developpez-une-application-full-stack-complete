import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

/**
 * Custom validator to allow empty field or minimum length
 * @param minLength Minimum string length
 */
export function emptyOrMinSizeValidators(minLength: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const val: string = control.value ? control.value : '';
      const valid = val == '' || minLength <= val.length;
      return !valid ? {emptyOrMinSize: {value: control.value}} : null;
    };
  }