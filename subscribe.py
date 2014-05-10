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
