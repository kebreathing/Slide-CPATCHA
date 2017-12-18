# Before we start the server
# try to type the commands below:
# windows: set FLASK_APP=app.py
# linux: export FLASK_APP=app.py
# flask run

from flask import Flask, url_for, request, render_template
app = Flask(__name__)

@app.route('/')
def hello():
    return 'Hello, world!'

@app.route('/index')
def index():
    return 'Index page'

# Router with parameters
@app.route('/user/<username>')
def profile(username):
    return 'Username is %s' % username

@app.route('/post/<int:post_id>')
def show_post(post_id):
    return 'Post is %d' % post_id

# Router with HTTP Method
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'GET':
        return 'It\'s GET request.'
    else:
        return 'I\'s POST request.'

@app.route('/hello')
@app.route('/hello/<name>')
def helo_name(name=None):
    # python will look for templates folder
    return render_template('hello.html', name=name)

# user urf_for to generate URL
# with app.test_request_context():
#     print(url_for('index'))
#     print(url_for('login'))
#     print(url_for('login', next='/'))
#     print(url_for('profile', username='chris', age='18'))
