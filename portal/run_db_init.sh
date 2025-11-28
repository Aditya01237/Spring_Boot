#!/bin/bash

# Database Credentials
DB_USER="aditya"
DB_PASS="Aditya@123"
DB_NAME="placement_db"

# SQL Files
CREATE_SQL="src/main/resources/sql/create.sql"
ALTER_SQL="src/main/resources/sql/alter.sql"
INSERT_SQL="src/main/resources/sql/insert.sql"

echo "Running SQL initialization..."

# Run Create
if [ -f "$CREATE_SQL" ]; then
    echo "Executing $CREATE_SQL..."
    mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" < "$CREATE_SQL"
else
    echo "Warning: $CREATE_SQL not found."
fi

# Run Alter
if [ -f "$ALTER_SQL" ]; then
    echo "Executing $ALTER_SQL..."
    mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" < "$ALTER_SQL"
else
    echo "Warning: $ALTER_SQL not found."
fi

# Run Insert
if [ -f "$INSERT_SQL" ]; then
    echo "Executing $INSERT_SQL..."
    mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" < "$INSERT_SQL"
else
    echo "Warning: $INSERT_SQL not found."
fi

echo "Database initialization complete."
