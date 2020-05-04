INSERT INTO Permission (id, type, permission) VALUES
 (15, 'URL', 'raids' ),
 (16, 'URL', 'raid' ),
 (17, 'URL', 'qualitygate' );
 
 INSERT INTO Role_To_Permission (role_id, permission_id) VALUES 
 (2,15),
 (2,16),
 (2,17);
