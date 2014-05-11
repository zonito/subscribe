#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
# Copyright 2011 Yesudeep Mangalapilly <yesudeep@gmail.com>
# Copyright 2012 Google, Inc.
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
import webapp2

from models import PrivateKeys
from models import Unsubscribed


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


APPLICATION = webapp2.WSGIApplication([
    webapp2.Route('/unsubscribe', UnsubscribeHandler, name='Unsubscribe')
], debug=True)
