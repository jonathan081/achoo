import json
import sys
import psycopg2


class Database:
    def __init__(self, credentials):
        self.endpoint = credentials["ENDPOINT"]
        self.username = credentials["USERNAME"]
        self.password = credentials["PASSWORD"]
        self.db_name = credentials["DB_NAME"]
        self.port = credentials["PORT"]
        self.url = f"{self.endpoint}"
        self.host = f"jdbc:postgresql://{self.endpoint}:{self.port}/{self.db_name}"
        self.dsn = {"dbname": self.db_name, "user": self.username,
                    "password": self.password, "host": self.url, "port": self.port}
        self.connection = None

    def establish_connection(self):
        if self.connection is None:
            try:
                self.connection = psycopg2.connect(**self.dsn)
            except psycopg2.DatabaseError as ex:
                raise ex

    def execute_query(self, query):
        if self.connection is not None:
            cur = self.connection.cursor()
            cur.execute(query)
            cur.close()
        else:
            raise Exception("Attempted to execute query, but there is no connection to the database.")

    def close_connection(self):
        if self.connection is not None:
            self.connection.commit()
            self.connection.close()
            self.connection = None
        else:
            raise Exception("Attempted to close the connection, but there is no connection to the database.")

    def print_tables(self):
        if self.connection is not None:
            cur = self.connection.cursor()
            print("Curr_keys:")
            cur.execute("SELECT * FROM curr_keys")
            for pair in cur.fetchall():
                print(pair)
            print("Infected:")
            cur.execute("SELECT * FROM infected")
            for val in cur.fetchall():
                print(val)
            cur.close()
        else:
            raise Exception("Attempted to print tables, but there is no connection to the database.")

    def empty_all_tables(self):
        if self.connection is not None:
            cur = self.connection.cursor()
            cur.execute("TRUNCATE TABLE infected")
            cur.execute("TRUNCATE TABLE pairs")
            cur.execute("TRUNCATE TABLE tests")
            cur.execute("TRUNCATE TABLE curr_keys")
            cur.close()
        else:
            raise Exception("Attempted to print tables, but there is no connection to the database.")