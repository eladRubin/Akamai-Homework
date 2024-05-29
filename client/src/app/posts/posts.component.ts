import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostDto } from './post-dto.interface';
import { Page } from './post-dto.interface';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostComponent implements OnInit {
  posts: PostDto[] = [];

  constructor(private http: HttpClient, @Inject('environment') private env: any) {}

  ngOnInit() {
    this.http.get<Page<PostDto>>(`${this.env.baseUrl}/api/getPostsSortedAndByPaging`, {
     params: { page: 0, size: 10 }
    }).subscribe(page => {
        this.posts = page.content;
    });
  }
}
