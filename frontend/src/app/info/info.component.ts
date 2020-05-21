import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-info',
  template: `
    <div class="info">running
      <span class="version">v{{ currentVersion }}</span> on <span class="env">{{ env }}</span>
    </div>
  `,
  styles: [
    ':host { margin: auto }',
    '.info { color: #e6ebf1; text-align: center }'
  ]
})
export class InfoComponent implements OnInit {
  currentVersion: string;
  env: string;

  ngOnInit(): void {
    this.currentVersion = environment.version;
    this.env = environment.config.env;
  }
}
