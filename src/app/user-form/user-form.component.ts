import {Component, OnInit, ViewChild, Input, Output, EventEmitter} from '@angular/core';
import {FormControl, FormBuilder, Validators} from '@angular/forms';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {throwError} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

  currentUserForm;

  rolesUrl = '/roles'
  usersUrl = '/users'
  availableRoles = []

  @Input() mode: string = ''

  @Output() submitted: EventEmitter<any> = new EventEmitter<any>();

  currentUser = null;

  @ViewChild('userFormElement') userFormElement;

  constructor(private formBuilder: FormBuilder, private http: HttpClient) {

    this.fillRoles();

    this.currentUserForm = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      role: null,
      active: 'N'
    })

  }


  fillRoles(): void {
    this.http.get(this.rolesUrl).subscribe((data => {
      this.availableRoles = data as any[];
    }))
  }


  ngOnInit(): void {
  }


  onSubmit(userData) {
    // Process checkout data here
    this.currentUserForm.reset();
    console.warn('Your order has been submitted', userData);
  }

  submit() {
    if (this.currentUserForm.valid) {
      let targetUrl = this.usersUrl;
      let request = 'post';
      if (this.mode == 'edit') {
        targetUrl = this.usersUrl + '/' + this.currentUser.id;
        request = 'put';
      }

      let requestBody = this.buildBackendUser(this.currentUserForm.value)

      this.http.request(request, targetUrl, {
        body: requestBody
      })
        .pipe(catchError(this.handleError.bind(this)))
        .subscribe(data => {
          this.currentUserForm.reset();
          this.submitted.emit(requestBody);

          if (this.mode == 'new')
            alert('User created');
          else if (this.mode == 'edit')
            alert('User updated');

        });
    }

  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else if (error.error.status == "BAD_REQUEST") {
      for (let field in error.error.errors) {
        if (field == 'name') {
          this.currentUserForm.controls['name'].setErrors({'inuse': true});
          this.currentUserForm.controls['name'].markAsTouched();
        } else {
          alert(error.error.errors[field]);
        }
      }
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }

  private buildBackendUser(formData) {
    let newData = {...formData};
    newData.role = this.availableRoles.find(x => (x.id == formData.role || (typeof formData.role == "object" && formData.role.id == x.id)));
    newData.active = formData.active ? 'Y' : 'N';
    return newData;
  }

  public setUser(user) {
    this.currentUser = user;
    if (user == null) {
      this.currentUserForm.reset();
      return;
    }
    this.currentUserForm.patchValue(user);
  }

  public roleComparator(item, selected) {
    return item.id == selected.id;
  }
}
