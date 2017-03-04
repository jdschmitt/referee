import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent extends OnInit {
  title = 'app works!';

  ngOnInit() {
    // TODO Check for auth creds (cookie?)
    console.log("Init app");
  }
}
