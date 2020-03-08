INSERT INTO Permission (id, type, permission) VALUES
 (12, 'URL', 'events'),
 (15, 'URL', 'raids' ),
 (16, 'URL', 'qualitygate' );
 
 INSERT INTO Role_To_Permission (role_id, permission_id) VALUES 
 (1,12),
 (2,12),
 (3,12),
 (1, 15),
 (1, 16),
 (2, 15),
 (2, 16),
 (3, 15),
 (3, 16);