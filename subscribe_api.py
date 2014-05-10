"""Subscribe API implemented using Google Cloud Endpoints."""

import base64
import json
import os

from google.appengine.api import mail
from google.appengine.ext import deferred
from google.appengine.ext import endpoints
from google.appengine.ext.webapp import template

from models import PrivateKeys
from models import Unsubscribed

from protorpc import remote

from subscribe_api_messages import RequestMessage
from subscribe_api_messages import ResponseMessage


PRIVATE_KEYS = ['your_application_specific_private_key']


def send(message):
    """Send email for given message object."""
    message.send()


def send_emails(request):
    """Send emails to given emails addresses with provided body."""
    message = mail.EmailMessage(sender=request.sender,
                                subject=request.subject)
    private_obj = PrivateKeys.get_or_add(
        private_key=request.private_key)
    for email_obj in request.email_addresses:
        email_address = email_obj.email_address
        if Unsubscribed.is_exist(email_address):
            continue
        body_path = os.path.join(
            os.path.dirname(__file__), 'templates/body.html')
        encrypt_email = base64.b64encode(
            json.dumps({'email_address': email_address,
                        'pid': private_obj.key().id_or_name()}))
        unsubscribe_url = 'https://%s/unsubscribe?id=%s' % (
            os.environ['HTTP_HOST'], encrypt_email)
        message.html = template.render(
            body_path, {'unsubscribe_url': unsubscribe_url,
                        'body': request.body,
                        'email_address': email_address})
        if request.reply_to:
            message.reply_to = request.reply_to
        message.to = email_address
        if request.async:
            deferred.defer(send, message, _queue='email')
        else:
            send(message)
    return True


@endpoints.api(name='subscribe', version='v1',
               description='Subscribe API',
               allowed_client_ids=[endpoints.API_EXPLORER_CLIENT_ID])
class SubscribeApi(remote.Service):
    """Class which defines subscibe API v1."""

    @endpoints.method(RequestMessage, ResponseMessage,
                      path='send', http_method='POST',
                      name='send.emails')
    def subscribe_send(self, request):
        """Exposes an API endpoint to send emails for the given email addresses.

        Args:
            request: An instance of RequestMessage parsed from the API
                request.

        Returns:
            An instance of ResponseMessage containing the status of request.
        """
        if request.private_key not in PRIVATE_KEYS:
            return ResponseMessage(success=False)
        send_emails(request)
        return ResponseMessage(success=True)


APPLICATION = endpoints.api_server([SubscribeApi],
                                   restricted=False)