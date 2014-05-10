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
