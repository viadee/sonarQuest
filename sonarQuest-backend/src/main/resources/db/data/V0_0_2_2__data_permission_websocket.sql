INSERT INTO Permission (id, type, permission) VALUES
 (13, 'URL', 'socket'),
 (14, 'URL', 'app');
 
 INSERT INTO Role_To_Permission (role_id, permission_id) VALUES 
 (1,13),
 (2,13),
 (3,13),
 (1,14),
 (2,14),
 (3,14);