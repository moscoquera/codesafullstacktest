import { Component, OnDestroy, OnInit } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {ViewChild} from "@angular/core";
import {DataTableDirective} from "angular-datatables";
import {FormBuilder, FormControl, Validators} from "@angular/forms";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy{
  title = 'Users Dashboard';
  usersUrl = '/users'
  userFormStatus=null;

  dtUsersOptions: any = {};
  users = [];
  selectedUser=null;

  searchFilter=''
  userSearchForm;

  @ViewChild('userForm') viewUserForm;
  @ViewChild(DataTableDirective) private dtUsers;

  newUserMode(): void{
    this.userFormStatus='new';
  }

  editUserMode(user): void{
    this.userFormStatus='edit';

    if(this.selectedUser!=null && user.id ==this.selectedUser.id){ //it's the same, so it's deselecting
      this.viewUserForm.setUser(null);
    }else{
      this.viewUserForm.setUser(user);
    }
    this.selectedUser=user;


  }

  submitUserForm(): void{
    this.viewUserForm.submit();
  }


  constructor(private http: HttpClient, private formBuilder: FormBuilder) {

    this.userSearchForm = this.formBuilder.group({
      name: new FormControl('', [Validators.required])
    })
  }

  ngOnInit(): void {
    const that = this;
    console.log("find me")
    this.dtUsersOptions = {
      pagingType: 'full_numbers',
      serverSide: true,
      processing: true,
      searching: false,
      rowCallback: (row: Node, data: any[] | Object, index: number) => {
        const self = this;
        // Unbind first in order to avoid any duplicate handler
        // (see https://github.com/l-lin/angular-datatables/issues/87)
        $('td', row).unbind('click');
        $('td', row).bind('click', () => {
          self.usersRowClickHandler(data);
        });
        return row;
      },
      ajax: (dataTablesParameters: any, callback) => {
        let params = new HttpParams().set('name',this.searchFilter!=null?this.searchFilter:'');
        that.http
          .get(
            this.usersUrl,
            {params},
          ).subscribe(resp => {
          that.users = (resp as any);
          callback({
            recordsTotal: that.users.length,
            recordsFiltered: that.users.length,
            data: that.users
          });
        });
      },
      columns: [{title:'ID', data: 'id' }, { data: 'name' }, { data: 'role.name' },{ data: 'active' }],
      select: true
    };
   // this.getUsers();
  }


  getUsers(): void{
    this.http.get(this.usersUrl).subscribe((data => {
      this.users = data as any[];
    }))
  }

  ngOnDestroy(): void {
    // Do not forget to unsubscribe the event

  }

  usersRowClickHandler(data):void{
    this.editUserMode(data);
  }

  onFormSubmitted(userData):void{
    this.reloadUsersTable()
  }

  private async reloadUsersTable(){
    let dtInstance = await this.dtUsers.dtInstance;
    dtInstance.ajax.reload()
  }

  onSearchSubmit(data):void{
    this.searchFilter=data.name
    this.reloadUsersTable()
  }

  onSearchClear(data):void{
    this.searchFilter=''
    this.reloadUsersTable()
  }

  deleteSelectedUser():void{
    if(this.selectedUser && this.selectedUser.id){
      this.deleteUser(this.selectedUser.id)
    }
  }

  deleteUser(userId): void {
    this.http.delete(this.usersUrl+'/'+userId)
      .subscribe(data => {
        this.viewUserForm.currentUserForm.reset();
        this.reloadUsersTable();
        alert('User deleted');
      });
  }

}
