from flask import Flask, request, jsonify
import database
from config import config_db

app = Flask(__name__)


@app.route('/new_user', methods=['POST'])
def add_new_user():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    query = "INSERT INTO curr_keys VALUES (%s, %s)", (user_key, None)
    db.execute_query(query)
    db.close_connection()


@app.route('/update_user', methods=['POST'])
def update_keys():
    new_key = request.json['new_key']
    old_key = request.json['old_key']
    db = config_db()
    db.establish_connection()
    query = "UPDATE curr_keys SET curr_key = %s WHERE curr_key = %s", (new_key, old_key)
    db.execute_query(query)
    db.close_connection()


@app.route('/insert_infected', methods=['POST'])
def insert_infected():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    query = "INSERT INTO infected VALUES (%s)", (user_key,)
    db.execute_query(query)
    db.close_connection()


@app.route('/remove_user', methods=['POST'])
def remove_user():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    db.execute_query("DELETE FROM pairs WHERE dev1 = %s", (user_key,))
    db.execute_query("DELETE FROM pairs WHERE dev2 = %s", (user_key,))
    db.execute_query("DELETE FROM tests WHERE key = %s", (user_key,))
    db.execute_query("DELETE FROM infected WHERE key = %s", (user_key,))
    db.execute_query("DELETE FROM curr_keys WHERE curr_key = %s", (user_key,))
    db.close_connection()


@app.route('/user_status', methods=['GET'])
def user_status():
    user_key = request.json['key']
    db = config_db()
    db.establish_connection()
    cur = db.connection.cursor()
    cur.execute("SELECT EXISTS(SELECT 1 FROM infected WHERE user_key = %s)", (user_key,))
    status = cur.fetchone()[0]
    cur.close()
    db.close_connection()
    return status


if __name__ == '__main__':
    app.run(debug=True)
