# Twinimal 
## _A super-minimal, clutter-free, focused way to browse twitter_

Developed by [Animesh]

![Twinimal Landing Page](https://repository-images.githubusercontent.com/524112639/46bd3855-1fd7-4bb6-809e-9302c8e3c1d3)

I have shared my development journey under the hashtag [#buildinpublic] on Twitter


For any issues, suggestions or feedback feel free to reach out to me [@contactanimeshs]
# What is Twinimal?
Twinimal is a super-minimal approach towards Twitter which gives you the power to choose your experience.

# What inspired me to build Twinimal?
I have been using Twitter since quite some time now and have seen it turn into something where a lot of features are actually obstructing us from doing what we actually intend to use Twitter for or trap us into the endless scrolling.

I wanted something where I can simply tweet and log out without any distractions, or I can choose the features which I would want to use. So, I thought why can't I build it myself?

# What makes Twinimal different from other Twitter clients?

- ### Super-minimal UI
    Only features which you need
- ### No sign up required
    Just login when you want to and logout 
- ### Secure & Privacy-focused
  No data from Twinimal is stored in a database. Access token, username etc are stored in a session linked to your browser to enable the use of Twinimal's features. 

    The session gets expired  after 30 minutes by default or when you logout or close the browser.
- ### Constantly evolving
    Being a community focused application Twinimal will be constantly evolving based on what the users want

## Features:
- Post a tweet
- Delete a tweet
- View your previous tweets
- Manage Bookmarks (Special feature! Not available in actual Twitter app/website)


## Technologies:
- Java
- HTML
- CSS 
- Vanilla Javascript

## How to set up this project in your machine?
`Pre-requisites: JDK 1.8 or above`

- Import the project
- Add the Twitter API credentials in the application.properties file  
- Build the project 
```sh
./gradlew build
```
- Run
```shell
./gradlew bootRun
```
- Now you can start using the app, open your browser and visit `127.0.0.1:8080`

## Helpful resources:
- [Twitter API Documentation] 
- [Twitter Developer Portal]
- [Michimani's Project in Golang] - Helped me in understanding the basic flow of using the Twitter API
- [Pika] - Beautiful screenshot editor by [Rishi Mohan] 

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

[Animesh]: <https://twitter.com/contactanimeshs>
[@contactanimeshs]: <https://twitter.com/contactanimeshs>
[git-repo-url]: <https://github.com/contactanimeshs/twinimal.git>
[Spring Initializer]: <https://start.spring.io/>
[Twitter API Documentation]: <https://developer.twitter.com/en/docs>
[Twitter Developer Portal]: <https://developer.twitter.com/>
[Pika]: <https://pika.style/>
[Rishi Mohan]: <https://twitter.com/thelifeofrishi>
[Michimani's Project in Golang]: <https://github.com/michimani/gotwi/>
[#buildinpublic]: <https://twitter.com/search?q=%23buildinpublic%20from%3A%40contactanimeshs&src=typed_query>