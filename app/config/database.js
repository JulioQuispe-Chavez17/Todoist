module.exports = {
  "development": {
    "username": "devopsnode",
    "password": "password",
    "database": "todolist",
    "host": "localhost",
    "dialect": "mysql"
  },
  "test": {
    "username": "root",
    "password": null,
    "database": "database_test",
    "host": "127.0.0.1",
    "dialect": "mysql"
  },
  "production": {
    "username": "root",
    "password": "35b71bcef27ae66eda7b2b04848e56cdef3611ada1cd74c7719920251b640c7b",
    "database": "mjvnirssyqefug",
    "port": 5432,
    "host": "ec2-44-198-214-172.compute-1.amazonaws.com",
    "dialect": "postgres",
    "dialectOptions": {
      "ssl": { "rejectUnauthorized": false }
    },
  }
}
