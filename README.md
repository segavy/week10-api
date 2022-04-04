# API testing

You need to perform 4 types of requests for http://www.httpbin.org/, and also to validate the responses from it.



1) GET request /get

Check that the passed parameters are correct
2) POST request. /post

verify that the transmitted body is correct
3) Authorization /basic-auth/

verify that if the authorization is correct, we will get the 200 status.
verify that in case of incorrect authorization, we will get a 401 status.
4) /stream

verify that the number of rows matches the number of streams sent.


Task requirement:

1) All requests are made to the portal http://www.httpbin.org/


2) Requirements for the use of libraries.



httpclient - for executing requests
junit/testng - for performing checks
3) Compliance with the project structure
4) If any bug was found, then it must be placed in README.md file
5) The result of the execution should be placed on the internal GitLab. The name of the commits should clearly reflect the essence of the changes.