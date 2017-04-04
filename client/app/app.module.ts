import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';

import { routing }        from './app.routing';

import { AppComponent }  from './app.component';
import { LoginModalComponent } from "./login-modal/login-modal.component";
import { HomeComponent } from "./home/home.component";
import { MainNavComponent } from "./main-nav/main-nav.component";
import { SecondPotComponent } from "./second-pot/second-pot.component";
import { AuthService } from "./services/auth.service";
import { SettingsService } from "./services/settings.service";
import { PlayersService } from "./services/players.service";

import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';
import {CommonModule} from "@angular/common";
import { FooterComponent } from './footer/footer.component';
import { MyPicksComponent } from './my-picks/my-picks.component';

@NgModule({
  imports:      [
    CommonModule,
    BrowserModule,
    FormsModule,
    routing,
    Ng2Bs3ModalModule,
    HttpModule,
    JsonpModule
  ],
  declarations: [
    AppComponent,
    LoginModalComponent,
    HomeComponent,
    MainNavComponent,
    SecondPotComponent,
    FooterComponent,
    MyPicksComponent
  ],
  providers: [
    AuthService,
    SettingsService,
    PlayersService
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
