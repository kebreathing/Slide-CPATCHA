import os
from flask import Flask, request, render_template
app = Flask(__name__, static_folder="static")

# Path Configuration
Path = {
    'root_path': './templates/img',
    'origin_path': '/pics/',
    'pieces_path': '/pieces/',
    'shadows_path': '/shadows/',
    'origin_type': '.jpg',
    'piece_type': '.png',
    'shadow_type': '.png',
}

def list_images(origin_path=None):
    if origin_path is None:
        origin_path = Path['root_path'] + Path['origin_path']

    imgs = []
    for filename in os.listdir(origin_path):
        imgs.append(filename[:-4])

    print(imgs)
    return imgs

@app.route('/index')
def index_page():
    # Render the index.html page
    return render_template('index.html')

@app.route('/puzzle')
def puzzle_page():
    return render_template('puzzle.html')
