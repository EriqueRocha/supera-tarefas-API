-- Inserir TaskFolder 'trabalho'
INSERT INTO task_folder (folder_name)
VALUES ('trabalho');

-- Inserir TaskFolder 'rotina diária'
INSERT INTO task_folder (folder_name)
VALUES ('rotina diária');

-- Inserir Task e associar à TaskFolder 'trabalho'
WITH inserted_task_folder AS (
    SELECT id FROM task_folder WHERE folder_name = 'trabalho'
)
INSERT INTO task (task_name, priority, status, creation_date, task_folder_id)
VALUES
    ('Finalizar relatório', 'HIGH', 'NOT_STARTED', '2024-09-20 14:30', (SELECT id FROM inserted_task_folder)),
    ('Reunião com equipe', 'MEDIUM', 'IN_PROGRESS', '2024-09-21 09:00', (SELECT id FROM inserted_task_folder));

-- Inserir Task e associar à TaskFolder 'rotina diária'
WITH inserted_task_folder AS (
    SELECT id FROM task_folder WHERE folder_name = 'rotina diária'
)
INSERT INTO task (task_name, priority, status, creation_date, task_folder_id)
VALUES
    ('Verificar e-mails', 'LOW', 'NOT_STARTED', '2024-09-20 08:00', (SELECT id FROM inserted_task_folder)),
    ('Planejar dia', 'MEDIUM', 'IN_PROGRESS', '2024-09-20 09:30', (SELECT id FROM inserted_task_folder));