import re


def security(data):
    # <input type="hidden" name="login_security" value="158911e7321cdb70d91e4c91dcebba9a-609d143b876affd0e1e949f98257682d">
    return re.findall('<input.+hidden.+login_security.+value="(.+)"', data)[0]
