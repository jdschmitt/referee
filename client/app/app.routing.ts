import { Routes, RouterModule } from '@angular/router';
import { GameAdminComponent } from "./game-admin/game-admin.component";
import { GroupPicksComponent } from "./group-picks/group-picks.component";
import { HomeComponent } from "./home/home.component";
import { MyPicksComponent } from "./my-picks/my-picks.component";
import { PlayerAdminComponent } from "./player-admin/player-admin.component";
import { SecondPotComponent } from "./second-pot/second-pot.component";
import { SettingsComponent } from "./settings/settings.component";
import { WeeklyWinnersComponent } from "./weekly-winners/weekly-winners.component";

const appRoutes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'gameAdmin',
    component: GameAdminComponent
  },
  {
    path: 'groupPicks',
    component: GroupPicksComponent
  },
  {
    path: 'picks',
    component: MyPicksComponent
  },
  {
    path: 'players',
    component: PlayerAdminComponent
  },
  {
    path: 'secondPot',
    component: SecondPotComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'weeklyWinners',
    component: WeeklyWinnersComponent
  }
];

export const routing = RouterModule.forRoot(appRoutes);
