export interface PostDto {
  id: number;
  points: number;
  title: string;
  text: string;
  created_by: string;
  created_on: string
}

export interface Page<T> {
  content: T[];
}
