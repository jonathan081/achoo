import json
import sys
import psycopg2
import database
from config import config_db

if __name__ == '__main__':
    if len(sys.argv) != 2:
        raise Exception("This script is supposed to take a file as argument.")
    with open(sys.argv[1], 'r') as file:
        data = file.read()
    db = config_db()
    db.establish_connection()
    db.execute_query(data)
    print("Successfully read in PostgreSQL from file.")
    db.close_connection()
