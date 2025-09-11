import { PercentPipe } from '@angular/common';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'inspectia-donut-chart',
  templateUrl: './chart-doughnut.component.html',
  styleUrls: ['./chart-doughnut.component.css'],
  standalone: true,
  imports: [PercentPipe]
})
export class DonutChartComponent implements OnInit {
  @Input() value: number = 0;
  public r: number = 100 / (2 * Math.PI);
  public total = 100;
  public offset = 25;
  public dashArray = '0 0';
  public color = '';


  constructor() { }

  ngOnInit() {
    console.log('Radius: ', this.r);
    this.dashArray = this.SetDashArray(this.value);
  }

  ngOnChanges(changes: SimpleChanges) {
    this.SetDashArray(this.value);
    this.color = this.ChangeColor(this.value);
  }

  private SetDashArray(value: number) {
    this.dashArray =
      value.toString() + ' ' + this.GetEndDashArray(value).toString();
    console.log('Dasharray: ', this.dashArray);
    return this.dashArray;
  }

  private GetEndDashArray(value: number): number {
    const end = (this.total - value);
    return end;
  }

  private ChangeColor(value: number): string {
    let range = 0;
    const color = [
      '#d0f8d0',
      '#c5efcd',
      '#bbe6c9',
      '#b0dcc6',
      '#a6d3c2',
      '#9bcabf',
      '#90c1bb',
      '#86b7b8',
      '#7baeb4',
      '#70a5b1',
      '#669cad',
      '#5b92aa',
      '#5189a6',
      '#4680a3',];
    if (value < 10) {
      return color[range];
    } else {
      range = Math.round(value / 10);
    }
    return color[range - 1];
  }

}
