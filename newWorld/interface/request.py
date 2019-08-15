import json
import requests
from requests import PreparedRequest, Response

session = requests.Session()

# 构造一个请求
request = PreparedRequest()
request.prepare(method="GET", url="http://www.weather.com.cn/data/sk/101190408.html")

# 发送构造好的请求

response = session.send(request)

# 响应内容
# response.content
# 响应码
# response.status_code
# 转码后以文本形式输出
# response.text
# 把响应内容转为json

# res = json.loads(response.content)
# print(res)


