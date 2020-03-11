import { ProgressDTO } from './ProgressDTO';

export interface Monster {
  Image: string;
  Name: string;
  HealthPoints: number;
  DamageTaken: number;
  progress: number;

  getDamageProgress(): number;
  addDamage(pDamage: number): void;
}