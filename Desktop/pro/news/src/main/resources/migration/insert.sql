insert  into permission (name , description, code, status, is_hidden) values
  ('Submit News','','submit_news','ACTIVE','ACTIVE'),
  ('Edit News','','edit_news','ACTIVE','ACTIVE'),
  ('Delete News','','delete_news','ACTIVE','ACTIVE')


insert  into role_permission (role_id, permission_id, status, created_at, updated_at)
values (1,1,'ACTIVE','2018-02-20 20:01:06','2018-02-20 20:01:06'),
  (1,2,'ACTIVE','2018-02-20 20:01:06','2018-02-20 20:01:06'),
  (1,3,'ACTIVE','2018-02-20 20:01:06','2018-02-20 20:01:06')