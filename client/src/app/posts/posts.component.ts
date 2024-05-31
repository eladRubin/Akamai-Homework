import { Component, OnInit, Inject, AfterViewInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PostDto } from './post-dto.interface';
import { Page } from './post-dto.interface';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostComponent implements OnInit, AfterViewInit {
  posts: PostDto[] = [];
  selectedPost: any;
  newPostTitle: any;
  newPostText: any;
  constructor(private http: HttpClient, @Inject('environment') private env: any) {}

  ngAfterViewInit() {

  }

  ngOnInit() {
    this.http.get<Page<PostDto>>(`${this.env.baseUrl}/api/getPostsSortedAndByPaging`, {
     params: { page: 0, size: 10 } //TODO: get it dynamically from page
    }).subscribe(page => {
        this.posts = page.content;
    });
  }

  upvote () {
    const headers = { 'content-type': 'application/json' };
    let params = new HttpParams();
    params = params.set('id', 1);
    params = params.set('upvoted_by', 1);

    this.http.post<any>(`${this.env.baseUrl}/api/upvote`, {},{
      headers: headers,
      params: params
    }).subscribe(response => {
        console.log('Upvoted successfully:', response);
      }, error => {
        console.error('Error upvoting:', error);
      });
  }

  downvote () {
    const headers = { 'content-type': 'application/json' };
    let params = new HttpParams();
    params = params.set('id', 1);
    params = params.set('downvote_by', 1);

    this.http.post<any>(`${this.env.baseUrl}/api/downvote`, {},{
      headers: headers,
      params: params
    }).subscribe(response => {
        console.log('Downvoted successfully:', response);
      }, error => {
        console.error('Error downvoted:', error);
      });
  }

  openPostDetails(post: PostDto) {
    this.selectedPost = post;
    const modal = document.getElementById('postDetailsModal');
    if (modal) {
      modal.style.display = 'block';
    }
  }

  closePostDetails() {
    const modal = document.getElementById('postDetailsModal');
    if (modal) {
      modal.style.display = 'none';
    }
  }

  openNewPostModal() {
    const modal = document.getElementById('newPostModal');
    if (modal) {
      modal.style.display = 'block';
    }
  }

    closeNewPostModal() {
      const modal = document.getElementById('newPostModal');
      if (modal) {
        modal.style.display = 'none';
      }
    }

    submitNewPost() {
     const headers = { 'content-type': 'application/json' };
        let params = new HttpParams();
        params = params.set('userId', 1);
        params = params.set('postTitle', this.newPostTitle);
        params = params.set('postText', this.newPostText);

        this.http.post<any>(`${this.env.baseUrl}/api/addPost`, {},{
          headers: headers,
          params: params
        }).subscribe(response => {
            console.log('Post added successfully:', response);
            this.closeNewPostModal();
          }, error => {
            console.error('Error adding post:', error);
            this.closeNewPostModal();
          });
    }
}
