import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AuthGuard } from './guards/auth.guards';
import { UnauthGuard } from './guards/unauth.guards';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { MeComponent } from './pages/me/me.component';
import { PostsComponent } from './pages/posts/posts.component';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [UnauthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [UnauthGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [UnauthGuard] },
  { path: 'topics', component: TopicsComponent,  canActivate: [AuthGuard] },
  { path: 'posts', component: PostsComponent,  canActivate: [AuthGuard] },
  { path: 'me', component: MeComponent,  canActivate: [AuthGuard] },
  { path: '404', component: NotFoundComponent},
  { path: '**', redirectTo: '404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
