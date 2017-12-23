import os
import random
from flask import Flask, request, render_template
app = Flask(__name__, static_folder="static")

# Path Configuration
Path = {
    'root_path': './static/img',
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
    return imgs

@app.route('/index')
def index_page():
    # Render the index.html page
    imgs = list_images(Path['root_path'] + Path['origin_path'])
    random.shuffle(imgs)
    return render_template('index.html', imgs=imgs)

@app.route('/puzzle/<imgName>')
def puzzle_page(imgName):
    # shadow = url_for('static',filename='img/shadows/'+imgName)
    shadow = imgName
    puzzle00 = imgName[:-4] + "-00"
    puzzle01 = imgName[:-4] + "-01"
    imgs = list_images(Path['root_path'] + Path['pieces_path'])
    puzzles = [puzzle00, puzzle01]

    for p in puzzles:
        imgs.remove(p)

    random.shuffle(imgs)
    puzzles.append(imgs[int(random.random() * len(imgs))])
    puzzles.append(imgs[int(random.random() * len(imgs))])
    random.shuffle(puzzles)
    return render_template('puzzle.html', shadow=shadow, puzzles=puzzles)
