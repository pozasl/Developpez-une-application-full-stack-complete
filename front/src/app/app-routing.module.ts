import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { AuthGuard } from './guards/auth.guards';
import { UnauthGuard } from './guards/unauth.guards';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { MeComponent } from './pages/me/me.component';
import { FeedComponent } from './pages/feed/feed.component';
import { PostComponent } from './pages/post/post.component';
import { PostDetailComponent } from './pages/post-detail/post-detail.component';

const routes: Routes = [
  { path: '', title: 'Accueil', component: HomeComponent, canActivate: [UnauthGuard] },
  { path: 'login', title: 'Connection', component: LoginComponent, canActivate: [UnauthGuard] },
  { path: 'register', title: 'Inscription', component: RegisterPageComponent, canActivate: [UnauthGuard] },
  { path: 'topics', title: 'Thèmes', component: TopicsComponent,  canActivate: [AuthGuard] },
  { path: 'feed', title: 'Mes articles', component: FeedComponent,  canActivate: [AuthGuard] },
  { path: 'post/:id', title: 'Article', component: PostDetailComponent,  canActivate: [AuthGuard] },
  { path: 'post', title: 'Nouvel article', component: PostComponent,  canActivate: [AuthGuard] },
  { path: 'me', title: 'Mon compte', component: MeComponent,  canActivate: [AuthGuard] },
  { path: '404', title: 'Introuvable', component: NotFoundComponent},
  { path: '**', redirectTo: '404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
