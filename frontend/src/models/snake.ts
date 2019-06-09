import { Direction } from './direction.enum';
import { Point } from './point';

export class Snake {
  public head: Point;
  public middles: Point[];
  private _direction: Direction = Direction.NONE;
  private _color: string = '#000';
  private _directionChanged: boolean = false;

  constructor(start: Point = new Point(), middles?: Point[]) {
    this.head = start;
    if (middles) {
      this.middles = [ ...middles ];
    } else {
      this.middles = [];
    }
  }

  set color(color: string) {
    this._color = color;
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

    this._direction = direction;
    this._directionChanged = true;
  }

  get direction() {
    return this._direction;
  }

  public createFromSnake(snake: Snake) {
    this.head = snake.head;
    this.middles = [...snake.middles];
  }

  get length(): number {
    if (this.middles.length === 0) {
      return 1;
    } else {
      let length: number = 0;

      length = this.middles.reduce((previous: number, currentPoint: Point, index: number) => {
          return previous + currentPoint.getDistanceFrom(this.middles[index - 1]);
        }, this.head.getDistanceFrom(this.middles[0]));

      return length;
    }
  }

  public move() {
    if (this._directionChanged) {
      const previousHead = new Point(this.head.getX(), this.head.getY());
      this.middles.splice(0, 0, previousHead);
      this._directionChanged = false;
    }

    if (this.direction !== Direction.NONE) {
      this.head.move(this._direction);

      if (this.middles.length > 1) {
        this.middles[this.middles.length - 1].moveTowards(this.middles[this.middles.length - 2]);
        if (this.middles[this.middles.length - 1].isEqual(this.middles[this.middles.length - 2])) {
          this.middles.pop();
        }
      } else if (this.middles.length === 1) {
        this.middles[0].moveTowards(this.head);
      }
    }
  }

  public *getBlocks() {
    let index = -1;

    if (index === -1) {
      index++;
      yield this.head;
    }

    for (; index < this.middles.length; index++) {
      if (index === 0) {
        yield* this.head.getPointsBetween(this.middles[index]);
      } else {
        yield* this.middles[index - 1].getPointsBetween(this.middles[index]);
      }
    }
  }

}
