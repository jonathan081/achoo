import json
import database


def config_db():
    json_file = open("db_cred.JSON")
    credentials = json.load(json_file)
    json_file.close()
    return database.Database(credentials)
