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

"""Unsuscribe handler - User can unsubscribe from the service"""

import base64
import json
import os
import urllib2
import webapp2

from google.appengine.ext.webapp import template

from models import PrivateKeys
from models import Unsubscribed


API_URL = 'http://%s/_ah/api/subscribe/v1/' % os.environ['HTTP_HOST']


class UnsubscribeHandler(webapp2.RequestHandler):
    """Unsubscribe handler."""

    def get(self):
        encrypted_message = self.request.get('id')
        if encrypted_message:
            message = base64.b64decode(encrypted_message)
            obj = json.loads(message)
            private_obj = PrivateKeys.get_by_id(obj['pid'])
            Unsubscribed.get_or_insert(
                key_name=obj['email_address'],
                private_key=private_obj)
        self.response.out.write('Unsubscribed.')


class DemoHandler(webapp2.RequestHandler):
    """Demo Handler."""

    def get(self, **kwargs):
        index_html = os.path.join(
            os.path.dirname(__file__), 'templates/index.html')
        self.response.out.write(template.render(index_html, kwargs))

    def post(self):
        params = {"sender": self.request.get('from'),
                  "reply_to": self.request.get('reply_to'),
                  "private_key": "your_application_specific_private_key",
                  "body": self.request.get("body"),
                  "subject": self.request.get("subject"),
                  "async": self.request.get("async") == 'on'}
        email_addresses = []
        for email_address in self.request.get('emails').split(','):
            email_addresses.append({'email_address': email_address})
        params['email_addresses'] = email_addresses
        request = urllib2.Request(API_URL + 'send', json.dumps(params),
                                  {'Content-Type': 'application/json'})
        response = urllib2.urlopen(request)
        output = response.read()
        response.close()
        self.get(response=output)


APPLICATION = webapp2.WSGIApplication([
    webapp2.Route('/unsubscribe', UnsubscribeHandler, name='Unsubscribe'),
    webapp2.Route('/demo', DemoHandler, name='demo')
], debug=True)
