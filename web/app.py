import flask
from flask import Flask, request, render_template

app = Flask(__name__)


@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'GET':
        return render_template("login.html")
    elif request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        if username == 'admin' and password == '123456':
            return "登陆成功"
        else:
            return "登陆失败"


if __name__ == '__main__':
    app.run()
