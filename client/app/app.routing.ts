import { Routes, RouterModule } from '@angular/router';
import { SecondPotComponent } from "./second-pot/second-pot.component";
import { HomeComponent } from "./home/home.component";
import { GameAdminComponent } from "./game-admin/game-admin.component";

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
  }
];

export const routing = RouterModule.forRoot(appRoutes);
