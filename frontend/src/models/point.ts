import { Direction } from './directions.enum';

export class Point {
  private x: number;
  private y: number;

  constructor(x: number = 0, y: number = x) {
    this.x = x;
    this.y = y;
  }

  public addPoint(point: Point) {
    const newPoint = new Point(this.x, this.y);
    newPoint.x += point.getX();
    newPoint.y += point.getY();

    return newPoint;
  }

  public isEqual(point: Point): boolean {
    return this.isOnAxisX(point) && this.isOnAxisY(point);
  }

  public getDistanceFrom(point: Point): number {
    return Math.abs(this.x - point.getX()) + Math.abs(this.y - point.getY());
  }

  public getX(): number {
    return this.x;
  }

  public getY(): number {
    return this.y;
  }

  public scale(scale: number) {
    return new Point(this.x * scale, this.y * scale);
  }

  public move(direction: Direction) {
    switch (direction) {
      case Direction.UP:
        this.y--;
        break;
      case Direction.RIGHT:
        this.x++;
        break;
      case Direction.DOWN:
        this.y++;
        break;
      case Direction.LEFT:
        this.x--;
        break;
      default:
        break;
    }
  }

  public isOnAxisX(point: Point) {
    return this.x === point.getX();
  }

  public isOnAxisY(point: Point) {
    return this.y === point.getY();
  }

  public moveTowards(point: Point) {
    if (this.isOnAxisX(point)) {
      this.y = this.y < point.getY() ? this.y + 1 : this.y - 1;
    } else if (this.isOnAxisY(point)) {
      this.x = this.x < point.getX() ? this.x + 1 : this.x - 1;
    }
  }

  public *getPointsBetween(point: Point) {
    if (this.isOnAxisX(point)) {
      if (this.y < point.y) {
        for (let y = this.y + 1; y <= point.getY(); y++) {
          yield new Point(this.x, y);
        }
      } else {
        for (let y = this.y - 1; y >= point.getY(); y--) {
          yield new Point(this.x, y);
        }
      }
    } else if (this.isOnAxisY(point)) {
      if (this.x < point.x) {
        for (let x = this.x + 1; x <= point.getX(); x++) {
          yield new Point(x, this.y);
        }
      } else {
        for (let x = this.x - 1; x >= point.getX(); x--) {
          yield new Point(x, this.y);
        }
      }
    }
  }
}
