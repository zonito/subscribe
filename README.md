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

[1]: https://developers.google.com/appengine/docs/python/endpoints/
[2]: https://developers.google.com/appengine/downloads
[3]: https://github.com/zonito/subscribe/blob/master/app.yaml
[4]: http://localhost:8080/_ah/admin
[5]: http://localhost:8080/_ah/api/explorer
