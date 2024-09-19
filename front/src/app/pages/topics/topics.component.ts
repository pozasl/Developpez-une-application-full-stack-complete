import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
