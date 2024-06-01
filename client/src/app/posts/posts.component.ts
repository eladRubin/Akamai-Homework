import { Component, OnInit, Inject, AfterViewInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PostDto } from './post-dto.interface';
import { Page } from './post-dto.interface';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostComponent implements OnInit, AfterViewInit {
  posts: PostDto[] = [];
  selectedPost: any;
  // new post ngModels
  newPostTitle: any;
  newPostText: any;
  // edit post ngModels
  editPostText: any;
  constructor(private http: HttpClient, @Inject('environment') private env: any, private storageService: StorageService) {

  }

  ngAfterViewInit () {

  }

  ngOnInit (): void {
    this.refreshScopeData();
  }

  refreshScopeData () {
    const headers = { 'content-type': 'application/json' };
    this.http.get<Page<PostDto>>(`${this.env.baseUrl}/api/getPostsSortedAndByPaging`, {
         params: { page: 0, size: 10 } //TODO: get it dynamically from page
        }).subscribe(page => {
            this.posts = page.content;
        });
  }

  upvote (post: PostDto) {
    this.selectedPost = post;
    const headers = { 'content-type': 'application/json' };
    let params = new HttpParams();
    params = params.set('id', this.selectedPost.id);
    params = params.set('upvoted_by', this.storageService.getUser().id);

    this.http.post<any>(`${this.env.baseUrl}/api/upvote`, {},{
      headers: headers,
      params: params
    }).subscribe(response => {
        console.log('Upvoted successfully:', response);
        this.refreshScopeData();
      }, error => {
        console.error('Error upvoting:', error);
      });
  }

  downvote (post: PostDto) {
    this.selectedPost = post;
    const headers = { 'content-type': 'application/json' };
    let params = new HttpParams();
    params = params.set('id', this.selectedPost.id);
    params = params.set('downvote_by', this.storageService.getUser().id);

    this.http.post<any>(`${this.env.baseUrl}/api/downvote`, {},{
      headers: headers,
      params: params
    }).subscribe(response => {
        console.log('Downvoted successfully:', response);
        this.refreshScopeData();
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
      params = params.set('userId', this.storageService.getUser().id);
      params = params.set('postTitle', this.newPostTitle);
      params = params.set('postText', this.newPostText);

      this.http.post<any>(`${this.env.baseUrl}/api/addPost`, {},{
        headers: headers,
        params: params
      }).subscribe(response => {
          console.log('Post added successfully:', response);
          this.refreshScopeData();
          this.closeNewPostModal();
        }, error => {
          console.error('Error adding post:', error);
          this.closeNewPostModal();
        });
  }

  openEditPostModal(post: PostDto) {
    this.selectedPost = post;
    this.editPostText = this.selectedPost.text;
    const modal = document.getElementById('editPostModal');
    if (modal) {
      modal.style.display = 'block';
    }
  }

  closeEditPostModal() {
      const modal = document.getElementById('editPostModal');
      if (modal) {
        modal.style.display = 'none';
      }
  }

  submitUpdatedPost() {
    const headers = { 'content-type': 'application/json' }; //TODO: replace with const httpOptions variable
      let params = new HttpParams();
      params = params.set('postId', this.selectedPost.id);
      params = params.set('newText', this.editPostText);

      this.http.post<any>(`${this.env.baseUrl}/api/editPost`, {},{
        headers: headers,
        params: params
      }).subscribe(response => {
          console.log('Post edited successfully:', response);
          this.refreshScopeData();
          this.closeEditPostModal();
        }, error => {
          console.error('Error edit post:', error);
          this.closeEditPostModal();
        });
    }

    onTextAreaValueChange(event: Event) {
      if(event) {
       const value = (event.target as any).value;
            this.editPostText = value;
      }
    }
}
