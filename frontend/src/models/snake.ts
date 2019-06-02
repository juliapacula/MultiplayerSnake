import { Direction } from './directions.enum';
import { Point } from './point';

export class Snake {
  public head: Point;
  public middles: Point[];
  private _direction: Direction = Direction.NONE;
  private _color: string = '#000';

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
    this._direction = direction;
  }

  get direction() {
    // TODO: Add validation if head is moving backwards!
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
    // TODO: Add possibility to move left or right
    // const previousHead = new Point(this.head.getX(), this.head.getY());
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
