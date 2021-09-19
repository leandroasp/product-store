import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'field-err',
  templateUrl: './control-erro-field.component.html',
  styleUrls: ['./control-erro-field.component.css']
})
export class ControlErroFieldComponent implements OnInit {

  @Input() showErr: boolean = false;
  @Input() msgErr: string = "";

  constructor() { }

  ngOnInit(): void {
  }

}
