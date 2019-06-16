import { Direction } from './direction.enum';
import { Point } from './point';

export class Snake {
  public points: Point[];
  public id: string;
  private _direction: Direction = Direction.NONE;
  private _color: string = '#000';
  private _directionChanged: Direction = Direction.NONE;
  private _shouldGrow: boolean = false;

  constructor(id: string, points: Point[], color?: string) {
    this.id = id;
    this.color = color ? color : '#000';
    if (points.length) {
      this.points = [ ...points ];
    } else {
      this.points = [];
    }
  }

  set color(color: string) {
    this._color = color ? color : '#000';
  }

  get color() {
    return this._color;
  }

  set direction(direction: Direction) {
    if (this._direction === direction) {
      return;
    }

    if (this._direction === Direction.NONE) {
      this._direction = direction;
      return;
    }

    if (this._direction === Direction.UP && direction === Direction.DOWN) {
      return;
    }

    if (this._direction === Direction.DOWN && direction === Direction.UP) {
      return;
    }

    if (this._direction === Direction.LEFT && direction === Direction.RIGHT) {
      return;
    }

    if (this._direction === Direction.RIGHT && direction === Direction.LEFT) {
      return;
    }

    this._directionChanged = direction;
  }

  get direction() {
    return this._direction;
  }

  public createFromSnake(snake: Snake) {
    this.points = [...snake.points];
  }

  get length(): number {
    if (this.points.length === 1) {
      return 1;
    } else {
      let length: number = 1;

      for (let index = 0; index  < this.points.length - 1; index++) {
        length += this.points[index].getDistanceFrom(this.points[index + 1]);
      }

      return length;
    }
  }

  public grow() {
    if (this.direction !== Direction.NONE) {
      this._shouldGrow = true;
    }
  }

  public move() {
    const previousHead = new Point(this.points[0].getX(), this.points[0].getY());
    if (this._directionChanged !== Direction.NONE && this.points.length) {
      this.points.splice(1, 0, previousHead);
      this._direction = this._directionChanged;
      this._directionChanged = Direction.NONE;
    }

    if (this.direction !== Direction.NONE) {
      this.points[0].move(this._direction);

      if (this.points.length > 1) {
        this.points[this.points.length - 1].moveTowards(this.points[this.points.length - 2]);
        if (this.points[this.points.length - 1].isEqual(this.points[this.points.length - 2])) {
          this.points.pop();
        }
      }
    }

    if (this._shouldGrow) {
      if (this.points.length > 1) {
        const previous = this.points[this.points.length - 2];
        const tail = this.points[this.points.length - 1];
        tail.moveBackwards(previous);
      } else {
        this.points.push(previousHead);
      }

      this._shouldGrow = false;
    }
  }

  public *getBlocks() {
    yield this.points[0];

    for (let index = 0; index < this.points.length - 1; index++) {
      yield* this.points[index].getPointsBetween(this.points[index + 1]);
    }
  }

}
