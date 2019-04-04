INSERT INTO Permission (id, type, permission) VALUES
 (12, 'URL', 'events');
 
 INSERT INTO Role_To_Permission (role_id, permission_id) VALUES 
 (1,12),
 (2,12),
 (3,12);