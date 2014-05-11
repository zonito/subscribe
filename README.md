Subscribe
=========

Subscribe service is to extract your application's email service and host it separately. An API using [Google Cloud Endpoints][1] is to interact with your subscribe service. Along with sending emails it will also handle requests for unsubscription.

## Setup Instructions

1. Make sure to have the [App Engine SDK for Python][2] installed, version
   1.7.5 or higher.
2. Update the value of `application` in [`app.yaml`][3] from `your-app-id` 
   to the app ID you have registered in the App Engine admin console and would 
   like to use to host your instance of this sample.
3. Run the application, and ensure it's running by visiting your local server's
   admin console (by default [localhost:8080/_ah/admin][4].)
4. Test your Endpoints by visiting the Google APIs Explorer: 
  [localhost:8080/_ah/api/explorer][5]

## Command line

```sh
curl -H 'content-type:application/json' \
     -d '{"email_addresses":[{"email_address": "test1@example.com"},{"email_address": "test2@example.com"}],"body": "<h1>Hello World</h1>","private_key": "your_application_specific_private_key","sender": "authorize@sender.com", "subject": "SUBJECT", "async": true}' \
     http://localhost:8080/_ah/api/subscribe/v1/send
```

## Licensing

Subscribe is licensed under the terms of the Apache License, version 2.0.

Copyright 2014 [Love Sharma][9].

Project [source code][10] is available at Github. Please report bugs and file enhancement requests at the [issue tracker][11].

## Why Subscribe?

* Avoid handling requests from different sources. Such as Android, IOS, Javascript, etc. ![8]
* Handles multiple applications. Just add your application's specific [private key][6]. It will handle all your unsubscriptions application wise.
* Handles multiple sender depending on your type of mails. Add all [sender's email address][7] in your appengine application.
* No need to handle your unsubscriptions while sending emails to your customers / users. Service already taking care of it. Append's unsubscribe link along with given body.


[1]: https://developers.google.com/appengine/docs/python/endpoints/
[2]: https://developers.google.com/appengine/downloads
[3]: https://github.com/zonito/subscribe/blob/master/app.yaml
[4]: http://localhost:8080/_ah/admin
[5]: http://localhost:8080/_ah/api/explorer
[6]: https://github.com/zonito/subscribe/blob/master/subscribe_api.py#L38
[7]: https://developers.google.com/appengine/docs/python/mail/emailmessagefields
[8]: https://developers.google.com/appengine/docs/images/endpoints.png "Endpoint Overview"
[9]: mailto:contact@lovesharma.com
[10]: https://github.com/zonito/subscribe
[11]: https://github.com/zonito/subscribe/issues

