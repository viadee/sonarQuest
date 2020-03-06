import { HighScore } from './../Interfaces/HighScore';
export class HighScoreModel implements HighScore {
    private _scoreDay: Date;
    private _scorePoints: number;

    constructor(scoreDay: Date, scorePoints: number){
        this._scoreDay = scoreDay;
        this._scorePoints = scorePoints;
    }

    public get scoreDay(): Date {
        return this._scoreDay;
    }

    public get scorePoints(): number {
        return this._scorePoints;
    }
}