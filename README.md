Technologies used in the project
--------------------------------
  1. Java + Springboot 3.2.5 + JPA
  2. Angular 18
  3. Mysql ruuning on docker
  4. Session managment with Coockies created by server and holding JWT for vailading user logged in the entire session (spring-boot-starter-security)
     
Running the application (make sure you have Docker engine install and running)
------------------------------------------------------------------------------
1. Clone Repository
2. start Springboot application + mysql with docker compose up command or with mvn springboot:run command
3. go to client directory and run (akamai_homework\client) and run "ng serve"
4. go to localhost:4200
5. use signup to register to the system
6. login
7. create posts

RestAPI calls
------------------
   AuthController:
------------------
  1. /api/auth/signup (signup first time to the system) - parameters:String username, String email, String password return value: ResponseEntity
  2. /api/auth/signin (login to the system with user and password) - parameters:String username, String password return value: ResponseEntity
  3. /api/auth/logout (signout from the system)- parameters:none return value: ResponseEntity

 -----------------
   PostController: 
 -----------------
  1. /api/addPost (add new post)  - parameters:String userId, String title, String text return value: ResponseEntity
  2. /api/editPost (add existing post's text)  - parameters:String postId, String newText return value: ResponseEntity
  3. /api/upvote (upvoting a post) - parameters:String id, String upvoted_by return value: ResponseEntity
  4. /api/downvote (downvoting a post) - parameters:String id, String downvoted_by return value: ResponseEntity
  5. /api/getPostsSortedAndByPaging (getAllpostsSorted) - parameters:int page, int size, String sortDirection return value: ResponseEntity


