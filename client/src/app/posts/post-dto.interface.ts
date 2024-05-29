export interface PostDto {
  id: number;
  points: number;
  title: string;
  text: string;
  created_by: string;
}

export interface Page<T> {
  content: T[];
}
