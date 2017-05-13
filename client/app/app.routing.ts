import { Routes, RouterModule } from '@angular/router';
import { SecondPotComponent } from "./second-pot/second-pot.component";
import { HomeComponent } from "./home/home.component";
import { GameAdminComponent } from "./game-admin/game-admin.component";
import { SettingsComponent } from "./settings/settings.component";

const appRoutes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'secondPot',
    component: SecondPotComponent
  },
  {
    path: 'gameAdmin',
    component: GameAdminComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  }
];

export const routing = RouterModule.forRoot(appRoutes);
