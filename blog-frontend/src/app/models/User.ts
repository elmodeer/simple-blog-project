import { Address } from './Address';
import { Article } from './Article';

export interface User {
    id: string;
    username: string;
    email: string;
    password: string;
    address?: Address;
    aboutMe?:string;
    firstName?: string;
    lastName?: string;
    roles: any[];
    articles: Article[];
    
}