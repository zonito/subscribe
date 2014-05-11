#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Copyright 2014 Love Sharma <contact@lovesharma.com>
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""Subscribe API implemented using Google Cloud Endpoints."""

import base64
import endpoints
import json
import os

from google.appengine.api import mail
from google.appengine.ext import deferred
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
               title='Subscribe Service')
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
            raise endpoints.UnauthorizedException('Unauthorize Application.')
        send_emails(request)
        return ResponseMessage(success=True)


APPLICATION = endpoints.api_server([SubscribeApi],
                                   restricted=False)
