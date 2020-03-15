import { ProgressDTO } from './../Interfaces/ProgressDTO';
import { Monster } from './../interfaces/Monster';

export class BaseMonster implements Monster {
  image: string;
  name: string;
  healthpoints: number;
  damageTaken: number;
  progress: number;
  gold: number;
  xp: number;

  constructor(name: string, image: string, healthpoints: number, damageTaken: number, progress: number, gold: number, xp: number) {
    this.name = name;
    this.image = image;
    this.progress = progress;
    this.healthpoints = healthpoints;
    this.damageTaken = damageTaken;
    this.progress = progress;
    this.gold = gold;
    this.xp = xp;
  }

  get Gold(): number {
    return this.gold;
  }
  set Gold(value: number) {
    this.gold = value;
  }
  get Xp(): number {
    return this.xp;
  }
  set Xp(value: number) {
    this.xp = value;
  }

  get Image(): string {
    return this.image;
  }
  set Image(value: string) {
    this.image = value;
  }
  get Name(): string {
    return this.name;
  }
  set Name(value: string) {
    this.name = value;
  }
  get Progress(): number {
    return this.progress;
  }
  set Progress(value: number) {
    this.progress = value;
  }
  get HealthPoints(): number {
    return this.healthpoints;
  }
  set HealthPoints(value: number) {
    this.healthpoints = value;
  }
  get DamageTaken(): number {
    return this.damageTaken;
  }
  set DamageTaken(value: number) {
    this.damageTaken = value;
  }

   public getDamageProgress() {
    const health = this.HealthPoints;
    const damage = this.DamageTaken;
    let progress = (health - damage) / health * 100;
    progress = Math.round(progress * 100);
    progress = progress / 100;
    return progress;
  }

  public addDamage(pDamage: number) {
    const damage = (this.damageTaken + pDamage > this.healthpoints) ? this.healthpoints : this.damageTaken + pDamage;
    this.damageTaken = damage;
  }

}
