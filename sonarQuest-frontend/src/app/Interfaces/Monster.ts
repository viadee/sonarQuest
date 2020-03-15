import { Reward } from './Reward';
import { ProgressDTO } from './ProgressDTO';

export interface Monster extends Reward {
  Image: string;
  Name: string;
  HealthPoints: number;
  DamageTaken: number;
  progress: number;

  getDamageProgress(): number;
  addDamage(pDamage: number): void;
}