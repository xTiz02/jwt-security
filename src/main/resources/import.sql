-- CREACIÓN DE MODULOS
INSERT INTO auth_modules (label, model) VALUES ('Usuarios', 'User');
INSERT INTO auth_modules (label, model) VALUES ('Roles', 'Rol');
INSERT INTO auth_modules (label, model) VALUES ('Permisos', 'Permission');
INSERT INTO auth_modules (label, model) VALUES ('Permisos de Usuario', 'UserPermission');


-- CREACIÓN DE OPERACIONES
INSERT INTO auth_permissions (module_id,name, description) VALUES (1,'READ_ALL_USERS','');
INSERT INTO auth_permissions (module_id,name, description)VALUES (1,'READ_ONE_USER','');
INSERT INTO auth_permissions (module_id,name, description) VALUES (1,'CREATE_ONE_USER','');
INSERT INTO auth_permissions (module_id,name, description) VALUES (1,'UPDATE_ONE_USER','');
INSERT INTO auth_permissions (module_id,name, description) VALUES (1,'DISABLE_ONE_USER','');
INSERT INTO auth_permissions (module_id,name, description) VALUES (2,'CREATE_ONE_ROL','');

-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CATEGORIES','', 'GET', false, 2);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_CATEGORY','/[0-9]*', 'GET', false, 2);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_CATEGORY','', 'POST', false, 2);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_CATEGORY','/[0-9]*', 'PUT', false, 2);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_CATEGORY','/[0-9]*/disabled', 'PUT', false, 2);
--
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CUSTOMERS','', 'GET', false, 3);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('REGISTER_ONE','', 'POST', true, 3);
--
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE','/authenticate', 'POST', true, 4);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN','/validate-token', 'GET', true, 4);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', false, 4);
--
-- -- CREACIÓN DE OPERACIONES DE MÓDULO PARA RETO SECCION 11
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PERMISSIONS','','GET', false, 5);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PERMISSION','/[0-9]*','GET', false, 5);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PERMISSION','','POST', false, 5);
-- INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_PERMISSION','/[0-9]*','DELETE', false, 5);


-- CREACIÓN DE ROLES
INSERT INTO auth_roles (name) VALUES ('CUSTOMER');
INSERT INTO auth_roles (name) VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO auth_roles (name) VALUES ('ADMINISTRATOR');

-- CREACIÓN DE PERMISOS
INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (3, 1);
INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (3, 2);
INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (3, 3);
INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (3, 4);
INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (3, 5);

INSERT INTO auth_granted_permissions (role_id, permission_id) VALUES (2, 6);




-- CREACIÓN DE USUARIOS

INSERT INTO auth_users (username, email, password, max_roles,last_login,account_expired,account_locked,credentials_expired,enabled) VALUES ('piero', 'piero@gmail.com','$2a$10$yUYjPg1OY6AZX8LEGaBoJeo1ErAN0uaPwjRjs1M3o79FOaQn/NrkW',1, null,false,false,false,true);
INSERT INTO auth_users (username, email, password, max_roles,last_login,account_expired,account_locked,credentials_expired,enabled) VALUES ('sub', 'sub@gmail.com','$2a$10$yUYjPg1OY6AZX8LEGaBoJeo1ErAN0uaPwjRjs1M3o79FOaQn/NrkW',1, null,false,false,false,true);


-- CREACIÓN DE ROLES DE USUARIOS
INSERT INTO auth_user_roles (user_id, rol_id) VALUES (1, 3);
INSERT INTO auth_user_roles (user_id, rol_id) VALUES (2, 2);