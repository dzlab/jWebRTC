# WebRTC Video Conferencing #

## Introduction ##
**jWebRTC** is a web application based on WebRTC that allows two participants to have a video/voice conference by sharing a single link. the [WebRTC](http://elsoufy.blogspot.fr/2012/07/webrtc-introduction.html)  protocol enables two browsers to have a direct P2P connection for transferring data. But before been able to communicate, a signaling channel is used to transfer information like codecs. This project uses [WebSocket](http://elsoufy.blogspot.fr/2012/08/html5-websocket-api.html) channels for signaling. 

Please refer to this post for more details on [how WebRTC works](http://elsoufy.blogspot.fr/2012/07/webrtc-introduction.html).

## Implementation ##

**jWebRTC** is a J2EE web application that uses Tomcat as the Applicaton Server and embed a Jetty server as a WebSocket Server. 

This project is a port of a [python web application](http://elsoufy.blogspot.fr/2012/07/webrtc-introduction.html). Here is a [link](https://apprtc.appspot.com/) for the original demo.

## Installation ##

To run the project locally, you need: Tomcat Application Server to be installed and a browser supporting WebRTC. This project is only available for Chrome v21 or Chrome Canary with WebRTC support. 

	Download the project with
		$ git clone https://github.com/soufy/jWebRTC.git
	Import it to eclipse
	Export a WAR file 
	Place it into Tomcat's webapps directory
	Launch the Tomcat server
    In a browser, open http://yourserver/jWebRTC/
    Have fun

## License  
 
This project is released under a Creative Commons CC BY-NC-SA license.