import { Computer } from "../computer/computer";

export interface User {   
    id : number | undefined; 
    name: string;
    password: string;
    basket: Computer[];
}