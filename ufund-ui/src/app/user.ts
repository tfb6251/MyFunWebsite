import { Computer } from "./computer";

export interface User {   
    id : number; 
    name: string;
    password: string;
    basket: Computer[];
}