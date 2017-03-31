import { Routes, RouterModule } from '@angular/router';
import { SecondPotComponent } from "./second-pot/second-pot.component";
import { HomeComponent } from "./home/home.component";

const appRoutes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'secondPot',
    component: SecondPotComponent
  }
];

export const routing = RouterModule.forRoot(appRoutes);
