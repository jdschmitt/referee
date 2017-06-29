import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';

import { routing }        from './app.routing';

import { AppComponent }  from './app.component';
import { BaseComponent } from './base.component';
import { LoginModalComponent } from "./login-modal/login-modal.component";
import { HomeComponent } from "./home/home.component";
import { MainNavComponent } from "./main-nav/main-nav.component";
import { SecondPotComponent } from "./second-pot/second-pot.component";

import { AuthService } from "./services/auth.service";
import { GamesService } from "./services/games.service";
import { Logger } from "./services/logger.service";
import { PlayersService } from "./services/players.service";
import { SeasonsService } from "./services/seasons.service";
import { SettingsService } from "./services/settings.service";
import { TeamsService } from "./services/teams.service";

import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';
import { CommonModule } from "@angular/common";
import { FooterComponent } from './footer/footer.component';
import { GameAdminComponent } from './game-admin/game-admin.component';
import { AddGameModalComponent } from './add-game-modal/add-game-modal.component';
import { Ng2DatetimePickerModule } from 'ng2-datetime-picker';
import { SettingsComponent } from './settings/settings.component';
import { SignUpModalComponent } from './signup-modal/signup-modal.component';

import { PasswordFieldComponent } from './password-field/password-field.component';
import { PlayerAdminComponent } from './player-admin/player-admin.component';

@NgModule({
  imports:      [
    CommonModule,
    BrowserModule,
    FormsModule,
    routing,
    Ng2Bs3ModalModule,
    HttpModule,
    JsonpModule,
    Ng2DatetimePickerModule
  ],
  declarations: [
    AppComponent,
    BaseComponent,
    LoginModalComponent,
    HomeComponent,
    MainNavComponent,
    SecondPotComponent,
    FooterComponent,
    GameAdminComponent,
    AddGameModalComponent,
    SettingsComponent,
    SignUpModalComponent,
    PasswordFieldComponent,
    PlayerAdminComponent
  ],
  providers: [
    AuthService,
    GamesService,
    Logger,
    PlayersService,
    SeasonsService,
    SettingsService,
    TeamsService
  ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
