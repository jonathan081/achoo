from flask import Flask, request, jsonify, render_template
import database
from config import config_db
from datetime import datetime, timezone

app = Flask(__name__)


@app.route('/new_user', methods=['POST'])
def add_new_user():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    query = "INSERT INTO curr_keys VALUES (%s, %s)"
    params = user_key, None
    db.execute_query(query, params)
    db.close_connection()
    return "Success"


@app.route('/update_user', methods=['POST'])
def update_keys():
    new_key = request.json['new_key']
    old_key = request.json['old_key']
    db = config_db()
    db.establish_connection()
    query = "UPDATE curr_keys SET curr_key = %s WHERE curr_key = %s"
    params = new_key, old_key
    db.execute_query(query, params)
    db.close_connection()
    return "Success"


@app.route('/insert_infected', methods=['POST'])
def insert_infected():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    query = "INSERT INTO infected VALUES (%s)"
    params = user_key,
    db.execute_query(query, params)
    db.close_connection()
    return "Success"


@app.route('/remove_user', methods=['POST'])
def remove_user():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    params = user_key,
    db.execute_query("DELETE FROM pairs WHERE dev1 = %s", params)
    db.execute_query("DELETE FROM pairs WHERE dev2 = %s", params)
    db.execute_query("DELETE FROM infected WHERE key = %s", params)
    db.execute_query("DELETE FROM curr_keys WHERE curr_key = %s", params)
    db.close_connection()
    return "Success"


@app.route('/new_pair', methods=['POST'])
def add_new_pair():
    user_one = request.json['key1']
    user_two = request.json['key2']
    db = config_db()
    db.establish_connection()
    time = datetime.now(timezone.utc)
    query = "INSERT INTO pairs VALUES (%s, %s, %s)"
    params = user_one, user_two, time
    db.execute_query(query, params)
    db.close_connection()
    return "Success"


@app.route('/user_status', methods=['GET'])
def user_status():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    cur = db.connection.cursor()
    params = user_key,
    cur.execute("SELECT EXISTS(SELECT 1 FROM infected WHERE user_key = %s)", params)
    status = cur.fetchone()[0]
    cur.close()
    db.close_connection()
    return status


@app.route('/')
def main():
    return "Index page"


if __name__ == '__main__':
    app.run(debug=True)
