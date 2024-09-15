CREATE TABLE task (
                      id SERIAL PRIMARY KEY,
                      task_name VARCHAR(100) NOT NULL,
                      priority VARCHAR(10) NOT NULL,
                      status VARCHAR(20) NOT NULL,
                      creation_date TIMESTAMP NOT NULL,
                      task_folder_id INTEGER,
                      FOREIGN KEY (task_folder_id) REFERENCES task_folder(id) ON DELETE CASCADE
);