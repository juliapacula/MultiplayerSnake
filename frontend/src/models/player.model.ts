import { Position } from './position.model';

export interface Player {
  id: string;
  color: string;
  positions: Position[];
}
