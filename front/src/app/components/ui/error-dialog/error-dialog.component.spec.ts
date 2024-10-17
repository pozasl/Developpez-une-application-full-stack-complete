import { TestBed } from '@angular/core/testing';

import { ErrorDialogComponent } from './error-dialog.component';
import { provideHttpClient } from '@angular/common/http';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AppError } from 'src/app/model/AppError';

describe('ErrorDialogComponent', () => {
  let component: ErrorDialogComponent;
  let dialog:MatDialog;

  const appError: AppError = {
    name: 'Error Name',
    message: 'Error message'
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ErrorDialogComponent, MatDialogModule],
      providers: [
        provideHttpClient()
      ]
    })
    .compileComponents();
    dialog = TestBed.inject(MatDialog);
    component = dialog.open(ErrorDialogComponent, { data: appError }).componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have error name and error message', () => {
    expect(component.data.name).toEqual(appError.name);
    expect(component.data.message).toEqual(appError.message);
  });
});
