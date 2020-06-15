import { User } from './User';

export interface Article {
    id: number;
    title: string;
    content: string;
    author: User;
    createdAt: string;
    updatedAt: string;
    imageUrl: string;
  }