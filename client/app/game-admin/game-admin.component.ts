import { Component, ViewChild, OnInit } from '@angular/core';
import { AddGameModalComponent } from '../add-game-modal/add-game-modal.component';

@Component({
  selector: 'app-game-admin',
  templateUrl: './game-admin.component.html',
  styleUrls: ['./game-admin.component.css']
})
export class GameAdminComponent implements OnInit {

  @ViewChild(AddGameModalComponent) addGameModal: AddGameModalComponent;

  constructor() { }

  ngOnInit() {
  }

  openModal() {
    return this.addGameModal.open();
  }

}
