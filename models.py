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

"""Helper model class for Subscribe API.

Defines models for persisting and querying subscribe data on a per send email basis and
check if given email address / user opt-out for the service.
"""


from google.appengine.ext import db


class PrivateKeys(db.Model):
    """Model to store private key in order to reference."""
    private_key = db.StringProperty(required=True)

    @classmethod
    def get_or_add(cls, private_key):
        all_obj = cls.all()
        all_obj.filter('private_key =', private_key)
        private_obj = all_obj.get()
        if not private_obj:
            private_obj = cls(private_key=private_key)
            private_obj.put()
        return private_obj



class Unsubscribed(db.Model):
    """Model to store opt-out users.

    With every email address we will be sending link to opt-out,
    to avoid spamming.
    """
    private_key = db.ReferenceProperty(PrivateKeys ,required=True)

    @classmethod
    def is_exist(cls, email_address):
        return cls.get_by_key_name(email_address) is not None
