import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";
import { passwordStrengthReg } from "./passwordStrengthReg";

/**
 * Custom validator to allow empty field or password strength test
 */
export function emptyOrMinSizeValidators(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const val: string = control.value ? control.value : '';
    const valid = val == '' || passwordStrengthReg.test(val);
    return !valid ? { emptyOrMinSize: { value: control.value } } : null;
  };
}