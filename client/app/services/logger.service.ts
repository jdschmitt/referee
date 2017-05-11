/**
 * Created by jason on 5/9/17.
 */
import { Injectable } from '@angular/core';

@Injectable()

export class Logger {
    logs: string[] = []; // capture logs for testing

    log(message: string) {
        this.logs.push(message);
        console.log(message);
    }
}