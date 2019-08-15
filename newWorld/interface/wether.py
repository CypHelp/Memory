import json

import requests

response = requests.get("http://www.weather.com.cn/data/sk/101190408.html")

res = json.loads(response.content)
print(res)
