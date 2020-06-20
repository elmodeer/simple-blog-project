# Simple Blog Project
This project is my sandbox to learn multiple things and various new technologies. Following functionality is currently implemented:
  1. User login and Sign up with JWT authentication
  2. Roles for different users -> (ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN) 
  3. User profile tab
  4. Changing the image of an article through the presigned url pattern on AWS(S3).
![alt text](https://github.com/elmodeer/simple-blog-project/blob/master/blog-backend/blog-arch.png)

Following Functionality are to be implemeted: 
  1. Follow article author feature
  2. Live text editor for inserting new articles. 
  3. bookmarking articles
  4. Dockerinzing the app.
  
# Installing
  1. Clone the repo
  2. Run a local instance of postgres and create a new db instance -> "testdb"
  3. Create an S3 bucket with the name "simple-blog-s3-dev" and region "eu-central-1" (Optional)
  4. Start the backend with `mvn spring-boot:run`
  5. Start the frontend with `npm i ; ng server`
