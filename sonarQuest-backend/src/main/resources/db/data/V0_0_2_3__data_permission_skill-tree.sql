INSERT INTO Permission (id, type, permission) VALUES
 (15,'URL', 'skilltree'),
 (16,'URL','innerskilltree');
 
 INSERT INTO Role_To_Permission (role_id, permission_id) VALUES 
 (1,15),
 (1,16),
 (2,15),
 (2,16),
 (3,15),
 (3,16);