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

"""ProtoRPC message class definitions for Subscribe API."""

from protorpc import messages


class EmailAddressMessage(messages.Message):
    """ProtoRPC message definition to represents a email address object."""
    email_address = messages.StringField(1)


class RequestMessage(messages.Message):
    """ProtoRPC message definition to provide inputs."""
    private_key = messages.StringField(1, required=True)
    email_addresses = messages.MessageField(EmailAddressMessage , 2,
                                            repeated=True)
    subject = messages.StringField(3, required=True)
    body = messages.StringField(4, required=True)
    sender = messages.StringField(5, required=True)
    reply_to = messages.StringField(6)
    async = messages.BooleanField(7, default=False)


class ResponseMessage(messages.Message):
    """ProtoRPC message definition to provide status of inputs."""
    success = messages.BooleanField(1)
