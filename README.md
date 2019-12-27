# Kichen Table (spring blog)
Our application will eventually have the following features:

Any user can view the blog posts.
Users can create an account.
Logged in users can create posts.
Logged in users can edit and delete their own posts.

#Design and implement a “Blog” Web application in Spring MVC + MySQL. Implement the following functionality:

#Home

#Featured Posts
Show the last posts ordered by user id.
Show also the last 5 post titles at the home page (as a sidebar) with a link to the post.

#NavBar
Show [Login] and [Register] buttons (when no user is logged in).

#Login
Login in the blog with existing account (username + password).
Show a success message after login or error message in case of problem.
Register
Register a new user in the MySQL database (by username + password + full name).
Show a success message after registration or error message in case of problem.
Logout
Logout the current user.
This [Logout] button is available after successful login only.
View / Create / Edit / Delete Posts (CRUD Operations)
Logged in users should be able to view all posts, create new post (by title + content) / edit / delete their own posts.
Posts are displayed in a table (one row for each post). At each row a link [Edit] and [Delete] should be displayed.
Create post shows a form to enter the post data (title + content). After the form submission, the post is created in the database. Implement field validation (non-empty fields are required).
Edit post fills an existing post data in a form and allows it to be edited. After successful form submission, the post is edited. Implement field validation.
Delete post shows the post to be deleted and asks for confirmation.
View All Users
Logged in users should be able to view all users (username + full name) in a table.
