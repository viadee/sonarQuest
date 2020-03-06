import { Condition } from '../../../../Interfaces/Condition';
import { TaskModel } from './../../../../game-model/TaskModel';
import { Component, OnInit, Input } from '@angular/core';
import { Task } from 'app/Interfaces/Task';

@Component({
  selector: 'app-conditiontasklog',
  templateUrl: './conditiontasklog.component.html',
  styleUrls: ['./conditiontasklog.component.css']
})
export class ConditiontasklogComponent implements OnInit {
  @Input()
  public taskList: Condition[];

  constructor() { }

  ngOnInit() {
  }

}
